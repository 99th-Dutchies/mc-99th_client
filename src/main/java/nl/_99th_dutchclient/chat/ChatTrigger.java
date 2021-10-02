package nl._99th_dutchclient.chat;

import nl._99th_dutchclient.settings.ActiveAFK;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatTrigger
{
    public String response;
    public Pattern pattern;
    public ActiveAFK active;
    public int delay;

    public ChatTrigger(String regex, String response, ActiveAFK active, int delay) {
        this(Pattern.compile(""), response, active, delay);

        try{
            Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            this.pattern = p;
        } catch (Exception ex) {}
    }

    public ChatTrigger(Pattern pattern, String response, ActiveAFK active, int delay) {
        this.pattern = pattern;
        this.active = active;
        this.response = response;
        this.delay = delay;
    }

    public Matcher match(String string) {
        return this.pattern.matcher(string);
    }

    public boolean matches(String string) {
        return this.match(string).matches();
    }
}
