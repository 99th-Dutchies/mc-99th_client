package nl._99th_client;

import net.minecraft.client.Minecraft;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OnlinePlayers {
    private final Minecraft mc;
    private ArrayList<UUID> players = new ArrayList<>();

    public OnlinePlayers(Minecraft mcIn){
        this.mc = mcIn;
        this.start();
    }

    public boolean isOnline(UUID uuid) {
        return players.contains(uuid);
    }

    public ArrayList<UUID> getPlayers() {
        return this.players;
    }

    public void setPlayers(ArrayList<UUID> players) {
        this.players = players;
    }

    private void load() {
        try{
            ArrayList<UUID> players = this.mc.apiClient.getOnlinePlayers();
            this.setPlayers(players);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void start() {
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        exec.scheduleAtFixedRate(() -> {
            this.load();
        }, 0, 1, TimeUnit.MINUTES);
    }
}
