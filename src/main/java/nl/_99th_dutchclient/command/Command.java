package nl._99th_dutchclient.command;

public abstract class Command
{
    public String[] commandNames;

    public Command(String[] commandNames) {
        this.commandNames = commandNames;
    }

    public boolean matches(String string) {
        for(String commandName : commandNames) {
            if(string.equals("\\" + commandName) || string.startsWith("\\" + commandName + " ")) {
                return true;
            }
        }

        return false;
    }

    public abstract void execute(String string);
    public abstract void invalid();
}
