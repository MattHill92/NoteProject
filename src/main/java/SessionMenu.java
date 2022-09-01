import java.util.Scanner;

public class SessionMenu {

    Scanner sc;
    Session session;

    public SessionMenu(Scanner scanner){
        this.sc = scanner;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void runMenu() {
        do {
            displaySearch();

            System.out.println("View // Page // Search // Create // Logout");

            String input = sc.nextLine().toLowerCase();
            Command command = Command.parseCommand(input);

            switch (command.command) {
                case "view":
                    viewPost(command.target);
                    break;
                case "page":
                    changePage(command.target);
                    break;
                case "delete":
                    searchPosts(command.target);
                    break;
                case "create":
                    createPost(command.target);
                    break;
                case "logout":
                    return;
            }
        } while (true);
    }

    private void viewPost(String target) {
    }

    private void changePage(String target) {
    }

    private void searchPosts(String target) {
    }

    private void createPost(String target) {
    }

    private void displaySearch() {
    }





}
