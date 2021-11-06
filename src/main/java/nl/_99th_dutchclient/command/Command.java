package nl._99th_dutchclient.command;

public abstract class Command
{
    public String command;

    public Command(String command) {
        this.command = command;
    }

    public boolean matches(String string){
        return string.equals("\\" + command) || string.startsWith("\\" + command + " ");
    }

    public abstract void execute(String string);
}
