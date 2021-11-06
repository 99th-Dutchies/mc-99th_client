package nl._99th_dutchclient.installer;

import nl._99th_dutchclient.Config;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

public class Utils {
    public static final String MAC_OS_HOME_PREFIX = "Library/Application Support";

    public enum OS {
        LINUX, SOLARIS, WINDOWS, MACOS, UNKNOWN;
    }

    private static final char[] hexTable = new char[] {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f' };

    public static File getWorkingDirectory() {
        return getWorkingDirectory("minecraft");
    }

    public static File getWorkingDirectory(String applicationName) {
        String applicationData, userHome = System.getProperty("user.home", ".");
        File workingDirectory = null;
        switch (getPlatform()) {
            case SOLARIS:
                workingDirectory = new File(userHome, String.valueOf('.') + applicationName + '/');
                break;
            case WINDOWS:
                applicationData = System.getenv("APPDATA");
                if (applicationData != null) {
                    workingDirectory = new File(applicationData, "." + applicationName + '/');
                    break;
                }
                workingDirectory = new File(userHome, String.valueOf('.') + applicationName + '/');
                break;
            case MACOS:
                workingDirectory = new File(userHome, "Library/Application Support/" + applicationName);
                break;
            default:
                workingDirectory = new File(userHome, String.valueOf(applicationName) + '/');
                break;
        }
        if (!workingDirectory.exists() && !workingDirectory.mkdirs())
            throw new RuntimeException("The working directory could not be created: " + workingDirectory);
        return workingDirectory;
    }

    public static OS getPlatform() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win"))
            return OS.WINDOWS;
        if (osName.contains("mac"))
            return OS.MACOS;
        if (osName.contains("solaris"))
            return OS.SOLARIS;
        if (osName.contains("sunos"))
            return OS.SOLARIS;
        if (osName.contains("linux"))
            return OS.LINUX;
        if (osName.contains("unix"))
            return OS.LINUX;
        return OS.UNKNOWN;
    }

    public static int find(byte[] buf, byte[] pattern) {
        return find(buf, 0, pattern);
    }

    public static int find(byte[] buf, int index, byte[] pattern) {
        for (int i = index; i < buf.length - pattern.length; i++) {
            boolean found = true;
            for (int pos = 0; pos < pattern.length; pos++) {
                if (pattern[pos] != buf[i + pos]) {
                    found = false;
                    break;
                }
            }
            if (found)
                return i;
        }
        return -1;
    }

    public static byte[] readAll(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        while (true) {
            int len = is.read(buf);
            if (len < 0)
                break;
            baos.write(buf, 0, len);
        }
        is.close();
        byte[] bytes = baos.toByteArray();
        return bytes;
    }

    public static void dbg(String str) {
        System.out.println(str);
    }

    public static String[] tokenize(String str, String delim) {
        List<String> list = new ArrayList();
        StringTokenizer tok = new StringTokenizer(str, delim);
        while (tok.hasMoreTokens()) {
            String token = tok.nextToken();
            list.add(token);
        }
        String[] tokens = list.<String>toArray(new String[list.size()]);
        return tokens;
    }

    public static String getExceptionStackTrace(Throwable e) {
        StringWriter swr = new StringWriter();
        PrintWriter pwr = new PrintWriter(swr);
        e.printStackTrace(pwr);
        pwr.close();
        try {
            swr.close();
        } catch (IOException iOException) {}
        return swr.getBuffer().toString();
    }

    public static void copyFile(File fileSrc, File fileDest) throws IOException {
        if (fileSrc.getCanonicalPath().equals(fileDest.getCanonicalPath()))
            return;
        FileInputStream fin = new FileInputStream(fileSrc);
        FileOutputStream fout = new FileOutputStream(fileDest);
        copyAll(fin, fout);
        fout.flush();
        fin.close();
        fout.close();
    }

    public static void copyAll(InputStream is, OutputStream os) throws IOException {
        byte[] buf = new byte[1024];
        while (true) {
            int len = is.read(buf);
            if (len < 0)
                break;
            os.write(buf, 0, len);
        }
    }

    public static void showMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg, Config.clientName, 1);
    }

    public static void showErrorMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Error", 0);
    }

    public static String readFile(File file) throws IOException {
        return readFile(file, "ASCII");
    }

    public static String readFile(File file, String encoding) throws IOException {
        FileInputStream fin = new FileInputStream(file);
        return readText(fin, encoding);
    }

    public static String readText(InputStream in, String encoding) throws IOException {
        InputStreamReader inr = new InputStreamReader(in, encoding);
        BufferedReader br = new BufferedReader(inr);
        StringBuffer sb = new StringBuffer();
        while (true) {
            String line = br.readLine();
            if (line == null)
                break;
            sb.append(line);
            sb.append("\n");
        }
        br.close();
        inr.close();
        in.close();
        return sb.toString();
    }

    public static String[] readLines(InputStream in, String encoding) throws IOException {
        String str = readText(in, encoding);
        String[] strs = tokenize(str, "\n\r");
        return strs;
    }

    public static void centerWindow(Component c, Component par) {
        Rectangle parRect;
        if (c == null)
            return;
        Rectangle rect = c.getBounds();
        if (par != null && par.isVisible()) {
            parRect = par.getBounds();
        } else {
            Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();
            parRect = new Rectangle(0, 0, scrDim.width, scrDim.height);
        }
        int newX = parRect.x + (parRect.width - rect.width) / 2;
        int newY = parRect.y + (parRect.height - rect.height) / 2;
        if (newX < 0)
            newX = 0;
        if (newY < 0)
            newY = 0;
        c.setBounds(newX, newY, rect.width, rect.height);
    }

    public static String byteArrayToHexString(byte[] bytes) {
        if (bytes == null)
            return "";
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            buf.append(hexTable[b >> 4 & 0xF]);
            buf.append(hexTable[b & 0xF]);
        }
        return buf.toString();
    }

    public static String arrayToCommaSeparatedString(Object[] arr) {
        if (arr == null)
            return "";
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            Object val = arr[i];
            if (i > 0)
                buf.append(", ");
            if (val == null) {
                buf.append("null");
            } else if (val.getClass().isArray()) {
                buf.append("[");
                if (val instanceof Object[]) {
                    Object[] valObjArr = (Object[])val;
                    buf.append(arrayToCommaSeparatedString(valObjArr));
                } else {
                    for (int ai = 0; ai < Array.getLength(val); ai++) {
                        if (ai > 0)
                            buf.append(", ");
                        buf.append(Array.get(val, ai));
                    }
                }
                buf.append("]");
            } else {
                buf.append(arr[i]);
            }
        }
        return buf.toString();
    }

    public static String removePrefix(String str, String prefix) {
        if (str == null || prefix == null)
            return str;
        if (str.startsWith(prefix))
            str = str.substring(prefix.length());
        return str;
    }

    public static String removePrefix(String str, String[] prefixes) {
        if (str == null || prefixes == null)
            return str;
        int strLen = str.length();
        for (int i = 0; i < prefixes.length; i++) {
            String prefix = prefixes[i];
            str = removePrefix(str, prefix);
            if (str.length() != strLen)
                break;
        }
        return str;
    }

    public static String removeSuffix(String str, String suffix) {
        if (str == null || suffix == null)
            return str;
        if (str.endsWith(suffix))
            str = str.substring(0, str.length() - suffix.length());
        return str;
    }

    public static String removeSuffix(String str, String[] suffixes) {
        if (str == null || suffixes == null)
            return str;
        int strLen = str.length();
        for (int i = 0; i < suffixes.length; i++) {
            String suffix = suffixes[i];
            str = removeSuffix(str, suffix);
            if (str.length() != strLen)
                break;
        }
        return str;
    }

    public static String ensurePrefix(String str, String prefix) {
        if (str == null || prefix == null)
            return str;
        if (!str.startsWith(prefix))
            str = String.valueOf(prefix) + str;
        return str;
    }

    public static boolean equals(Object o1, Object o2) {
        if (o1 == o2)
            return true;
        if (o1 == null)
            return false;
        return o1.equals(o2);
    }

    public static int parseInt(String str, int defVal) {
        try {
            if (str == null)
                return defVal;
            str = str.trim();
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return defVal;
        }
    }

    public static boolean equalsMask(String str, String mask, char wildChar) {
        if (mask == null || str == null)
            return (mask == str);
        if (mask.indexOf(wildChar) < 0)
            return mask.equals(str);
        List<String> tokens = new ArrayList();
        char c = wildChar;
        String cs = String.valueOf(c);
        if (mask.startsWith(cs))
            tokens.add("");
        StringTokenizer tok = new StringTokenizer(mask, cs);
        while (tok.hasMoreElements())
            tokens.add(tok.nextToken());
        if (mask.endsWith(cs))
            tokens.add("");
        String startTok = tokens.get(0);
        if (!str.startsWith(startTok))
            return false;
        String endTok = tokens.get(tokens.size() - 1);
        if (!str.endsWith(endTok))
            return false;
        int currPos = 0;
        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            if (token.length() > 0) {
                int foundPos = str.indexOf(token, currPos);
                if (foundPos >= 0) {
                    currPos = foundPos + token.length();
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public static Object[] addObjectToArray(Object[] arr, Object obj) {
        if (arr == null)
            throw new NullPointerException("The given array is NULL");
        int arrLen = arr.length;
        int newLen = arrLen + 1;
        Object[] newArr = (Object[])Array.newInstance(arr.getClass().getComponentType(), newLen);
        System.arraycopy(arr, 0, newArr, 0, arrLen);
        newArr[arrLen] = obj;
        return newArr;
    }

    public static Object[] addObjectToArray(Object[] arr, Object obj, int index) {
        List<Object> list = new ArrayList(Arrays.asList(arr));
        list.add(index, obj);
        Object[] newArr = (Object[])Array.newInstance(arr.getClass().getComponentType(), list.size());
        return list.toArray(newArr);
    }

    public static Object[] addObjectsToArray(Object[] arr, Object[] objs) {
        if (arr == null)
            throw new NullPointerException("The given array is NULL");
        if (objs.length == 0)
            return arr;
        int arrLen = arr.length;
        int newLen = arrLen + objs.length;
        Object[] newArr = (Object[])Array.newInstance(arr.getClass().getComponentType(), newLen);
        System.arraycopy(arr, 0, newArr, 0, arrLen);
        System.arraycopy(objs, 0, newArr, arrLen, objs.length);
        return newArr;
    }

    public static Object[] removeObjectFromArray(Object[] arr, Object obj) {
        List list = new ArrayList(Arrays.asList(arr));
        list.remove(obj);
        Object[] newArr = collectionToArray(list, arr.getClass().getComponentType());
        return newArr;
    }

    public static Object[] collectionToArray(Collection coll, Class<?> elementClass) {
        if (coll == null)
            return null;
        if (elementClass == null)
            return null;
        if (elementClass.isPrimitive())
            throw new IllegalArgumentException(
                    "Can not make arrays with primitive elements (int, double), element class: " + elementClass);
        Object[] array = (Object[])Array.newInstance(elementClass, coll.size());
        return coll.toArray(array);
    }
}
