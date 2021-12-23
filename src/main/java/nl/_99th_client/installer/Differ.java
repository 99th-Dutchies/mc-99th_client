package nl._99th_client.installer;

import nl._99th_client.installer.xdelta.Delta;
import nl._99th_client.installer.xdelta.DeltaException;
import nl._99th_client.installer.xdelta.DiffWriter;
import nl._99th_client.installer.xdelta.GDiffWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Differ {
    public static void main(String[] args) {
        if (args.length < 3) {
            Utils.dbg("Usage: Differ <base.jar> <mod.jar> <diff.jar>");
            return;
        }
        File baseFile = new File(args[0]);
        File modFile = new File(args[1]);
        File diffFile = new File(args[2]);
        try {
            /*if (baseFile.getName().equals("AUTO"))
                baseFile = detectBaseFile(modFile);*/
            if (!baseFile.exists() || !baseFile.isFile())
                throw new IOException("Base file not found: " + baseFile);
            if (!modFile.exists() || !modFile.isFile())
                throw new IOException("Mod file not found: " + modFile);
            process(baseFile, modFile, diffFile);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void process(File baseFile, File modFile, File diffFile) throws IOException, DeltaException, NoSuchAlgorithmException {
        ZipFile modZip = new ZipFile(modFile);
        Map<String, String> cfgMap = Patcher.getConfigurationMap(modZip);
        Pattern[] patterns = Patcher.getConfigurationPatterns(cfgMap);
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(diffFile));
        ZipFile baseZip = new ZipFile(baseFile);
        Enumeration<? extends ZipEntry> modZipEntries = modZip.entries();
        Map<String, Map<String, Integer>> mapStats = new HashMap<>();
        while (modZipEntries.hasMoreElements()) {
            ZipEntry modZipEntry = modZipEntries.nextElement();
            if (modZipEntry.isDirectory())
                continue;
            InputStream in = modZip.getInputStream(modZipEntry);
            byte[] bytes = Utils.readAll(in);
            String name = modZipEntry.getName();
            byte[] bytesDiff = makeDiff(name, bytes, patterns, cfgMap, baseZip);
            if (bytesDiff != bytes) {
                ZipEntry zipEntryDiff = new ZipEntry("patch/" + name + ".xdelta");
                zipOut.putNextEntry(zipEntryDiff);
                zipOut.write(bytesDiff);
                zipOut.closeEntry();
                addStat(mapStats, name, "delta");
                byte[] md5 = HashUtils.getHashMd5(bytes);
                String md5Str = HashUtils.toHexString(md5);
                byte[] bytesMd5Str = md5Str.getBytes("ASCII");
                ZipEntry zipEntryMd5 = new ZipEntry("patch/" + name + ".md5");
                zipOut.putNextEntry(zipEntryMd5);
                zipOut.write(bytesMd5Str);
                zipOut.closeEntry();
                continue;
            }
            ZipEntry zipEntrySame = new ZipEntry(name);
            zipOut.putNextEntry(zipEntrySame);
            zipOut.write(bytes);
            zipOut.closeEntry();
            addStat(mapStats, name, "same");
        }
        zipOut.close();
        printStats(mapStats);
    }

    private static void printStats(Map<String, Map<String, Integer>> mapStats) {
        List<String> folders = new ArrayList<>(mapStats.keySet());
        Collections.sort(folders);
        for (Iterator<String> it = folders.iterator(); it.hasNext(); ) {
            String folder = it.next();
            Map<String, Integer> map = mapStats.get(folder);
            List<String> types = new ArrayList<>(map.keySet());
            Collections.sort(types);
            String dbg = "";
            for (Iterator<String> it2 = types.iterator(); it2.hasNext(); ) {
                String type = it2.next();
                Integer val = map.get(type);
                if (dbg.length() > 0)
                    dbg = String.valueOf(dbg) + ", ";
                dbg = String.valueOf(dbg) + type + " " + val;
            }
            Utils.dbg(folder + " = " + dbg);
        }
    }

    private static void addStat(Map<String, Map<String, Integer>> mapStats, String name, String type) {
        int pos = name.lastIndexOf('/');
        String folder = "";
        if (pos >= 0)
            folder = name.substring(0, pos);
        Map<String, Integer> map = mapStats.get(folder);
        if (map == null) {
            map = new HashMap<>();
            mapStats.put(folder, map);
        }
        Integer val = map.get(type);
        if (val == null)
            val = new Integer(0);
        val = new Integer(val.intValue() + 1);
        map.put(type, val);
    }

    public static byte[] makeDiff(String name, byte[] bytesMod, Pattern[] patterns, Map<String, String> cfgMap, ZipFile zipBase) throws IOException, DeltaException {
        String baseName = Patcher.getPatchBase(name, patterns, cfgMap);
        if (baseName == null)
            return bytesMod;
        ZipEntry baseEntry = zipBase.getEntry(baseName);
        if (baseEntry == null)
            throw new IOException("Base entry not found: " + baseName + " in: " + zipBase.getName());
        InputStream baseIn = zipBase.getInputStream(baseEntry);
        byte[] baseBytes = Utils.readAll(baseIn);
        ByteArrayInputStream baisTarget = new ByteArrayInputStream(bytesMod);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        GDiffWriter gDiffWriter = new GDiffWriter(new DataOutputStream(outputStream));
        Delta.computeDelta(baseBytes, baisTarget, bytesMod.length, (DiffWriter)gDiffWriter);
        gDiffWriter.close();
        return outputStream.toByteArray();
    }

    /*public static File detectBaseFile(File modFile) throws IOException {
        ZipFile modZip = new ZipFile(modFile);
        String clientVer = Installer.getClientVersion(modZip);
        if (clientVer == null)
            throw new IOException("Version not found");
        modZip.close();
        String mcVer = Installer.getMinecraftVersionFromClientVersion(clientVer);
        if (mcVer == null)
            throw new IOException("Version not found");
        File dirMc = Utils.getWorkingDirectory();
        File baseFile = new File(dirMc, "versions/" + mcVer + "/" + mcVer + ".jar");
        Utils.dbg("BaseFile: " + baseFile);
        return baseFile;
    }*/
}

