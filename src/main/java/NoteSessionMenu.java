import java.util.Scanner;

public class NoteSessionMenu {

    Scanner sc;
    NoteSession ns;

    public NoteSessionMenu(Scanner scanner){
        this.sc = scanner;
    }

    public void setSession(NoteSession ns) {
        this.ns = ns;
    }

    public void runMenu() {
        do {
            ns.printNotes();
            System.out.println("Add // Rename // Delete // Logout");

            String input = sc.nextLine().toLowerCase();
            Command command = Command.parseCommand(input);

            switch (command.command) {
                case "add":
                    addNote();
                    break;
                case "rename":
                    renameNote(command.target);
                    break;
                case "delete":
                    removeNote(command.target);
                    break;
                case "logout":
                    return;
            }
        } while (true);
    }

    private void removeNote(String target) {
        if (target.equals("")){
            System.out.println("Incorrect Syntax, try 'delete (target)'");
            return;
        }
        ns.removeNote(target);
    }

    private void renameNote(String target) {
        if (target.equals("")){
            System.out.println("Incorrect Syntax, try 'rename (target)'");
            return;
        }
        System.out.print("New title: ");
        String rename = sc.nextLine();
        ns.renameNote(target, rename);
    }

    private void addNote() {
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Message: ");
        String body = sc.nextLine();
        ns.addNote(title, body);
    }
}
