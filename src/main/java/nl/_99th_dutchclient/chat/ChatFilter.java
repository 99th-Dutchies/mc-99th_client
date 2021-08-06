package nl._99th_dutchclient.chat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatFilter
{
    public Pattern pattern;
    public boolean active;

    public ChatFilter(String regex, boolean active) {
        this(Pattern.compile(regex, Pattern.CASE_INSENSITIVE), active);
    }

    public ChatFilter(Pattern pattern, boolean active) {
        this.pattern = pattern;
        this.active = active;
    }

    public Matcher match(String string) {
        return this.pattern.matcher(string);
    }

    public boolean matches(String string) {
        return this.match(string).matches();
    }
}
