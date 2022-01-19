package nl._99th_client.util;

import net.minecraft.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DeviceID {
    public static String getDeviceID() {
        String OS = System.getProperty("os.name").toLowerCase();
        String machineId = "";
        if (OS.indexOf("win") >= 0) {
            StringBuffer output = new StringBuffer();
            Process process;
            String[] cmd = {"wmic", "csproduct", "get", "UUID"};
            try {
                process = Runtime.getRuntime().exec(cmd);
                process.waitFor();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    if(!StringUtils.isNullOrEmpty(line) && !line.startsWith("UUID")) {
                        output.append(line.trim());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            machineId = output.toString();
        } else {
            machineId = "Invalid-OS";
        }

        return machineId;
    }
}
