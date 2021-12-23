package nl._99th_client.chat;

import net.minecraft.client.Minecraft;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class EventTrigger
{
    public String response;
    public EventTrigger.Event trigger;
    public boolean active;
    public int delay;

    public EventTrigger(Event trigger, String response, boolean active, int delay) {
        this.response = response;
        this.trigger = trigger;
        this.active = active;
        this.delay = delay;
    }

    public void handle() {
        Minecraft mc = Minecraft.getInstance();

        if(this.active && !this.response.isEmpty()) {
            ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);

            exec.schedule(() -> {
                String[] split = response.split("\\|");

                for(String r : split) {
                    if(r.startsWith("\\")) {
                        mc.commandManager.checkCommand(r);
                        continue;
                    }

                    mc.ingameGUI.getChatGUI().addToSentMessages(r);
                    mc.player.sendChatMessage(r);
                }
            }, this.delay, TimeUnit.MILLISECONDS);
        }
    }

    public enum Event {
        SERVER_JOIN("server_join"),
        WORLD_JOIN("world_join");

        private final String eventName;

        Event(String name)
        {
            this.eventName = name;
        }

        public String toString()
        {
            return this.getString();
        }

        public String getString()
        {
            return this.eventName;
        }
    }
}
