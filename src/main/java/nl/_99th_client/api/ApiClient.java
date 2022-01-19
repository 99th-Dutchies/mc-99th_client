package nl._99th_client.api;

import nl._99th_client.Config;
import nl._99th_client.util.DeviceID;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

public class ApiClient {
    private static final String device_id = DeviceID.getDeviceID();

    public static List<String> post(String route, HashMap<String,String> body) throws IOException {
        body.put("version", Config.clientVersion);
        body.put("device_id", device_id);

        MultipartUtil multipart = new MultipartUtil(Config.apiBase + route, "UTF-8");

        for(HashMap.Entry<String, String> param : body.entrySet()) {
            multipart.addFormField(URLEncoder.encode(param.getKey(), "UTF-8"), URLEncoder.encode(param.getValue(), "UTF-8"));
        }

        return multipart.finish();
    }
}
