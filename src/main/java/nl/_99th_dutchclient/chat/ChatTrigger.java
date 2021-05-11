package nl._99th_dutchclient.chat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatTrigger
{
    public String response;
    public Pattern pattern;
    public boolean active;

    public ChatTrigger(String regex, String response, boolean active) {
        this(Pattern.compile(regex), response, active);
    }

    public ChatTrigger(Pattern pattern, String response, boolean active) {
        this.pattern = pattern;
        this.active = active;
        this.response = response;
    }

    public Matcher match(String string) {
        return this.pattern.matcher(string);
    }

    public boolean matches(String string) {
        return this.match(string).matches();
    }
}
