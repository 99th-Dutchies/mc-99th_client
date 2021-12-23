package nl._99th_client.chat;

import nl._99th_client.settings.ActiveAFK;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatTrigger
{
    public String response;
    public Pattern pattern;
    public ActiveAFK active;
    public int delay;
    public int cooldown;

    private ArrayList<Pair<String, Long>> history = new ArrayList<>();

    public ChatTrigger(String regex, String response, ActiveAFK active, int delay, int cooldown) {
        this(Pattern.compile(""), response, active, delay, cooldown);

        try{
            Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            this.pattern = p;
        } catch (Exception ex) {}
    }

    public ChatTrigger(Pattern pattern, String response, ActiveAFK active, int delay, int cooldown) {
        this.pattern = pattern;
        this.active = active;
        this.response = response;
        this.delay = delay;
        this.cooldown = cooldown;
    }

    public Matcher match(String string) {
        return this.pattern.matcher(string);
    }

    public boolean matches(String string) {
        return this.match(string).matches();
    }

    public boolean checkCooldown(String response) {
        for(Pair<String, Long> r : this.history) {
            if(r.getKey().equals(response) &&
                    r.getValue() + this.cooldown*1000 > System.currentTimeMillis()) {
                return true;
            }
        }

        this.history.add(Pair.of(response, System.currentTimeMillis()));
        return false;
    }
}
