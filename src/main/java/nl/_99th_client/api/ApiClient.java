package nl._99th_client.api;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import nl._99th_client.Config;
import nl._99th_client.util.DeviceID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

public class ApiClient {
    private static String session_id;
    private static final String device_id = DeviceID.getDeviceID();
    private static Minecraft mc;
    private static Logger LOGGER;

    public ApiClient(Minecraft mc) {
        this.mc = mc;
        this.LOGGER = LogManager.getLogger();
    }

    public ApiClient() {}

    public void startSession(Session session) {
        if(this.mc.gameSettings._99thClientSettings.dataCollection) {
            try {
                HashMap<String, String> data = new HashMap<>();
                GameProfile profile = session.getProfile();

                data.put("playername", profile.getName());
                data.put("playerid", profile.getId().toString());

                this.session_id = this.post("play/start", data);
            } catch (IOException e) {
                LOGGER.error("Failed starting session at API: " + e);
            }
        }
    }

    public void stopSession() {
        if(this.mc.gameSettings._99thClientSettings.dataCollection) {
            try {
                HashMap<String, String> data = new HashMap<>();

                data.put("id", this.session_id);

                this.post("play/stop", data);
            } catch (IOException e) {
                LOGGER.error("Failed stopping session at API: " + e);
            }
        }
    }

    public void installed() throws IOException {
        this.post("installed", new HashMap());
    }

    private String post(String route, HashMap<String,String> body) throws IOException {
        body.put("version", Config.clientVersion);
        body.put("device_id", device_id);

        MultipartUtil multipart = new MultipartUtil(Config.apiBase + route, "UTF-8");

        for(HashMap.Entry<String, String> param : body.entrySet()) {
            multipart.addFormField(URLEncoder.encode(param.getKey(), "UTF-8"), URLEncoder.encode(param.getValue(), "UTF-8"));
        }

        return multipart.finish();
    }
}
