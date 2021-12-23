package nl._99th_client.chat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatFilter
{
    public Pattern pattern;
    public boolean activePlayer;
    public boolean activeChat;

    public ChatFilter(String regex, boolean activePlayer, boolean activeChat) {
        this(Pattern.compile(""), activePlayer, activeChat);

        try{
            Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            this.pattern = p;
        } catch (Exception ex) {}
    }

    public ChatFilter(Pattern pattern, boolean activePlayer, boolean activeChat) {
        this.pattern = pattern;
        this.activePlayer = activePlayer;
        this.activeChat = activeChat;
    }

    public Matcher match(String string) {
        return this.pattern.matcher(string);
    }

    public boolean matches(String string) {
        return this.match(string).matches();
    }
}
