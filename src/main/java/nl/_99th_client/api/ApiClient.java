package nl._99th_client.api;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import nl._99th_client.Config;
import nl._99th_client.ccg.Achievement;
import nl._99th_client.ccg.LobbyGameItem;
import nl._99th_client.ccg.Parkour;
import nl._99th_client.util.DeviceID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

public class ApiClient {
    private String session_id;
    private static final String device_id = DeviceID.getDeviceID();
    private Minecraft mc;
    private Logger LOGGER;

    public ApiClient(Minecraft mc) {
        this.mc = mc;
        this.LOGGER = LogManager.getLogger();
    }

    public ApiClient() {}

    public void startSession(Session session) {
        if(this.mc.gameSettings._99thClientSettings.getDataCollection()) {
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
        if(this.mc.gameSettings._99thClientSettings.getDataCollection()) {
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

    public ArrayList<UUID> getOnlinePlayers() throws IOException, ParseException {
        ArrayList<UUID> players = new ArrayList<>();
        String playerString = this.get("online");

        if(playerString.isEmpty()) return players;

        JSONParser jp = new JSONParser();

        for (Object o : (JSONArray)jp.parse(playerString)) {
            String s = (String) o;
            players.add(UUID.fromString(s));
        }

        return players;
    }

    public JSONObject resolveDiscordUser(String tag) throws IOException, ParseException {
        String userString = this.getDiscord("user/" + tag);
        JSONParser jp = new JSONParser();
        return (JSONObject) jp.parse(userString);
    }

    public ApiResult<String> linkDiscord(String discord_id, String discord_tag) {
        try {
            HashMap<String, String> data = new HashMap<>();

            data.put("session_id", this.session_id);
            data.put("playerid", this.mc.getSession().getProfile().getId().toString());
            data.put("discord_id", discord_id);
            data.put("discord_tag", discord_tag);

            this.post("link/discord", data);

            return new ApiResult(true, "Discord account linked!");
        } catch (IOException e) {
            LOGGER.error("Failed linking Discord account: " + e);

            return new ApiResult(false, "Unknown error occurred linking Discord account: " + e);
        }
    }

    public void submitAchievements(List<Achievement> achievements) {
        try {
            JSONArray jsonArray = new JSONArray();
            for (Achievement ach : achievements) {
                jsonArray.add(ach.toJSON());
            }

            HashMap<String, String> data = new HashMap<>();
            data.put("session_id", this.session_id);
            data.put("achievements", jsonArray.toJSONString());
            LOGGER.info(this.post("ccg/achievements", data));
        } catch (IOException e) {
            LOGGER.error("Failed submitting achievements to API: " + e);
        }
    }

    public void submitLobbyGames(List<LobbyGameItem> lobbyGameItems) {
        try {
            JSONArray jsonArray = new JSONArray();
            for (LobbyGameItem lgi : lobbyGameItems) {
                jsonArray.add(lgi.toJSON());
            }

            HashMap<String, String> data = new HashMap<>();
            data.put("session_id", this.session_id);
            data.put("lobbygame_items", jsonArray.toJSONString());
            LOGGER.info(this.post("ccg/lobbygames", data));
        } catch (IOException e) {
            LOGGER.error("Failed submitting lobbygame_items to API: " + e);
        }
    }

    public void submitParkours(List<Parkour> parkours) {
        try {
            JSONArray jsonArray = new JSONArray();
            for (Parkour parkour : parkours) {
                jsonArray.add(parkour.toJSON());
            }

            HashMap<String, String> data = new HashMap<>();
            data.put("session_id", this.session_id);
            data.put("parkours", jsonArray.toJSONString());
            LOGGER.info(this.post("ccg/parkours", data));
        } catch (IOException e) {
            LOGGER.error("Failed submitting parkours to API: " + e);
        }
    }

    private String get(String route) throws IOException {
        URL url = new URL(Config.apiBase + route);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", Config.userAgent);
        con.setRequestProperty("Content-Type", "application/json");

        con.setRequestProperty("X-Client-version", Config.clientVersion);
        con.setRequestProperty("X-Client-device", this.device_id);
        if(this.session_id != null) {
            con.setRequestProperty("X-Client-session", this.session_id);
        }
        int conRes = con.getResponseCode();
        if(conRes > 299) {
            con.disconnect();
            return "";
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        con.disconnect();
        return content.toString();
    }

    private String post(String route, HashMap<String,String> body) throws IOException {
        body.put("version", Config.clientVersion);
        body.put("device_id", this.device_id);

        MultipartUtil multipart = new MultipartUtil(Config.apiBase + route, "UTF-8");

        for(HashMap.Entry<String, String> param : body.entrySet()) {
            multipart.addFormField(URLEncoder.encode(param.getKey(), "UTF-8"), URLEncoder.encode(param.getValue(), "UTF-8"));
        }

        return multipart.finish();
    }

    private String getDiscord(String route) throws IOException {
        URL url = new URL(Config.discordBotApiBase + route);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", Config.userAgent);
        con.setRequestProperty("Content-Type", "application/json");

        int conRes = con.getResponseCode();
        if(conRes > 299) {
            con.disconnect();
            return "";
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        con.disconnect();
        return content.toString();
    }
}
