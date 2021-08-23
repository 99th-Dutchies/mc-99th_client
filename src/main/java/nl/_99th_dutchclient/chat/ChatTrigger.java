package nl._99th_dutchclient.chat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatTrigger
{
    public String response;
    public Pattern pattern;
    public boolean active;
    public int delay;

    public ChatTrigger(String regex, String response, boolean active, int delay) {
        this(Pattern.compile(regex, Pattern.CASE_INSENSITIVE), response, active, delay);
    }

    public ChatTrigger(Pattern pattern, String response, boolean active, int delay) {
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
