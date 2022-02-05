package nl._99th_client.installer;

import io.sentry.Sentry;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import nl._99th_client.Config;
import nl._99th_client.api.ApiClient;
import org.json.simple.JSONObject;
import org.json.simple.JSONWriter;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Installer {
    public static void main(String[] args)
    {
        Sentry.init(options -> {
            options.setDsn(Config.sentryDsn);
            options.setDebug(true);
            options.setRelease("Installer " + Config.clientVersion);
        });

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            InstallerGUI frm = new InstallerGUI();
            Utils.centerWindow(frm, null);
            frm.show();
        } catch (Exception e) {
            String msg = e.getMessage();
            if (msg != null && msg.equals("QUIET"))
                return;
            e.printStackTrace();
            String str = Utils.getExceptionStackTrace(e);
            str = str.replace("\t", "  ");
            JTextArea textArea = new JTextArea(str);
            textArea.setEditable(false);
            Font f = textArea.getFont();
            Font f2 = new Font("Monospaced", f.getStyle(), f.getSize());
            textArea.setFont(f2);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(600, 400));
            JOptionPane.showMessageDialog(null, scrollPane, "Error", 0);
        }
    }

    public static void doInstall(File dirMc) throws Exception {
        Utils.dbg("Dir minecraft: " + dirMc);
        File dirMcLib = new File(dirMc, "libraries");
        Utils.dbg("Dir libraries: " + dirMcLib);
        File dirMcVers = new File(dirMc, "versions");
        Utils.dbg("Dir versions: " + dirMcVers);
        String clientVer = getClientVersion();
        String clientMCVer = getClientMCVersion();
        Utils.dbg(Config.clientName + " Version: " + clientVer);
        Utils.dbg("Minecraft Version: " + clientMCVer);
        String mcVerClient = clientMCVer + "-" + Config.clientName;
        Utils.dbg("Minecraft-" + Config.clientName + " Version: " + mcVerClient);
        copyMinecraftVersion(clientMCVer, mcVerClient, dirMcVers);
        installClientJar(clientMCVer, mcVerClient, dirMcVers);
        updateJson(dirMcVers, mcVerClient, dirMcLib, clientMCVer);
        updateLauncherJson(dirMc, mcVerClient, "launcher_profiles.json");
        updateLauncherJson(dirMc, mcVerClient, "launcher_profiles_microsoft_store.json");

        ApiClient apiClient = new ApiClient();
        apiClient.installed();
    }

    private static void updateJson(File dirMcVers, String mcVerClient, File dirMcLib, String clientMCVer) throws IOException, ParseException {
        File dirMcVersOf = new File(dirMcVers, mcVerClient);
        File fileJson = new File(dirMcVersOf, String.valueOf(mcVerClient) + ".json");
        String json = Utils.readFile(fileJson, "UTF-8");
        JSONParser jp = new JSONParser();
        JSONObject root = (JSONObject)jp.parse(json);
        root.remove("downloads");
        root.put("id", mcVerClient);
        FileOutputStream fosJson = new FileOutputStream(fileJson);
        OutputStreamWriter oswJson = new OutputStreamWriter(fosJson, "UTF-8");
        JSONWriter jw = new JSONWriter(oswJson);
        jw.writeObject(root);
        oswJson.flush();
        oswJson.close();
    }

    private static void updateLauncherJson(File dirMc, String mcVerClient, String fileName) throws IOException, ParseException {
        File fileJson = new File(dirMc, fileName);
        if (!fileJson.exists() || !fileJson.isFile()) {
            return;
        }
        String json = Utils.readFile(fileJson, "UTF-8");
        JSONParser jp = new JSONParser();
        JSONObject root = (JSONObject)jp.parse(json);
        JSONObject profiles = (JSONObject)root.get("profiles");
        JSONObject prof = (JSONObject)profiles.get(Config.clientName);
        if (prof == null) {
            prof = new JSONObject();
            prof.put("name", Config.clientName);
            prof.put("created", formatDateMs(new Date()));
            profiles.put(Config.clientName, prof);
        }
        prof.put("type", "custom");
        prof.put("lastVersionId", mcVerClient);
        prof.put("lastUsed", formatDateMs(new Date()));
        prof.put("icon", ProfileIcon.DATA);
        root.put("selectedProfile", Config.clientName);
        FileOutputStream fosJson = new FileOutputStream(fileJson);
        OutputStreamWriter oswJson = new OutputStreamWriter(fosJson, "UTF-8");
        JSONWriter jw = new JSONWriter(oswJson);
        jw.writeObject(root);
        oswJson.flush();
        oswJson.close();
    }

    private static void copyMinecraftVersion(String mcVer, String mcVerClient, File dirMcVer) throws IOException {
        File dirVerMc = new File(dirMcVer, mcVer);
        if (!dirVerMc.exists()) {
            showMessageVersionNotFound(mcVer);
            throw new RuntimeException("QUIET");
        }
        File dirVerMcClient = new File(dirMcVer, mcVerClient);
        dirVerMcClient.mkdirs();
        Utils.dbg("Dir version MC: " + dirVerMc);
        Utils.dbg("Dir version MC-" + Config.clientName + ": " + dirVerMcClient);
        File fileJarMc = new File(dirVerMc, mcVer + ".jar");
        File fileJarMcClient = new File(dirVerMcClient, mcVerClient + ".jar");
        if (!fileJarMc.exists()) {
            showMessageVersionNotFound(mcVer);
            throw new RuntimeException("QUIET");
        }
        Utils.copyFile(fileJarMc, fileJarMcClient);
        File fileJsonMc = new File(dirVerMc, mcVer + ".json");
        File fileJsonMcOf = new File(dirVerMcClient, mcVerClient + ".json");
        Utils.copyFile(fileJsonMc, fileJsonMcOf);
    }

    private static boolean installClientJar(String clientMCVer, String mcVerClient, File dirMcVers) throws Exception {
        File fileSrc = getClientZipFile();
        File dirDest = new File(dirMcVers, clientMCVer + "-" + Config.clientName);
        File fileDest = new File(dirDest, clientMCVer + "-" + Config.clientName + ".jar");
        if (false) {
            fileDest = new File(fileSrc.getParentFile(), clientMCVer + "-" + Config.clientName + "_MOD.jar");
            JFileChooser jfc = new JFileChooser(fileDest.getParentFile());
            jfc.setSelectedFile(fileDest);
            int ret = jfc.showSaveDialog(null);
            if (ret != 0)
                return false;
            fileDest = jfc.getSelectedFile();
            if (fileDest.exists()) {
                JOptionPane.setDefaultLocale(Locale.ENGLISH);
                int ret2 = JOptionPane.showConfirmDialog((Component)null, "The file \"" + fileDest.getName() + "\" already exists.\nDo you want to overwrite it?", "Save", 1);
                if (ret2 != 0)
                    return false;
            }
        }
        if (fileDest.equals(fileSrc)) {
            JOptionPane.showMessageDialog((Component)null, "Source and target file are the same.", "Save", 0);
            return false;
        }
        Utils.dbg("Source: " + fileSrc);
        Utils.dbg("Dest: " + fileDest);
        File dirMc = dirMcVers.getParentFile();
        File fileBase = new File(dirMc, "versions/" + clientMCVer + "/" + clientMCVer + ".jar");
        if (!fileBase.exists()) {
            showMessageVersionNotFound(clientMCVer);
            throw new RuntimeException("QUIET");
        }
        if (fileDest.getParentFile() != null)
            fileDest.getParentFile().mkdirs();
        Patcher.process(fileBase, fileSrc, fileDest);
        return true;
    }

    public static File getClientZipFile() throws Exception {
        URL url = Installer.class.getProtectionDomain().getCodeSource().getLocation();
        Utils.dbg("URL: " + url);
        URI uri = url.toURI();
        File fileZip = new File(uri);
        return fileZip;
    }

    public static String getClientVersion() {
        return Config.clientVersion;
    }

    public static String getClientMCVersion() {
        return Config.mcVersion;
    }

    private static Object formatDate(Date date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
            String str = dateFormat.format(date);
            return str;
        } catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String str = dateFormat.format(date);
            return str;
        }
    }

    private static Object formatDateMs(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss.SSS'Z'");
        String str = dateFormat.format(date);
        return str;
    }

    private static void showMessageVersionNotFound(String mcVer) {
        Utils.showErrorMessage("Cannot find Minecraft " + mcVer + ".\nYou must download and start Minecraft " + mcVer + " once in the official launcher.");
    }
}
