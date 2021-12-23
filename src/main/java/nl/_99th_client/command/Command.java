package nl._99th_client.command;

public abstract class Command
{
    public String[] commandNames;
    public String description;

    public Command(String[] commandNames, String description) {
        this.commandNames = commandNames;
        this.description = description;
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
