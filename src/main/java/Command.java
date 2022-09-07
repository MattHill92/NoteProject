public class Command {
    String command = "";
    String target = "";

    static Command parseCommand(String input){
        Command c = new Command();
        int indexOfFirstSpace = input.indexOf(" ");

        if (indexOfFirstSpace != -1) {
            c.command = input.substring(0, indexOfFirstSpace).toLowerCase();
            if (input.length() > indexOfFirstSpace+1)
                c.target = input.substring(indexOfFirstSpace+1);
        }
        else c.command = input;
        return c;
    }
}
