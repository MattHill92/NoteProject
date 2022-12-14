import java.util.Scanner;

public class LogInMenu {

    LogInManager lm;
    Session session = null;
    Scanner sc;
    boolean requestExit = false;


    public LogInMenu(Scanner sc, LogInManager lm){
        this.sc = sc;
        this.lm = lm;
    }

    public void runMenu(){
        do {
            System.out.println("Login // Register // Quit");
            String input = sc.nextLine().toLowerCase();
            switch (input) {
                case "login":
                    processLogin();
                    break;
                case "register":
                    processRegister();
                    break;
                case "quit":
                    requestExit = true;
                    break;
            }
        } while (!requestExit && session == null);
    }

    private void processRegister() {
        String username = requestUsername();
        String password = requestPassword();
        String confirm = requestConfirm();
        lm.registerAccount(username, password, confirm);
        session = lm.getSession();
        Run.logger.info("Register attempted - username: " + username);
    }

    private void processLogin() {
        String username = requestUsername();
        String password = requestPassword();
        lm.login(username, password);
        session = lm.getSession();
        Run.logger.info("Login attempted - username: " + username);
    }

    private String requestUsername(){
        System.out.print("Username: ");
        return sc.nextLine();
    }

    private String requestPassword(){
        System.out.print("Password: ");
        return sc.nextLine();
    }

    private String requestConfirm(){
        System.out.print("Confirm Password: ");
        return sc.nextLine();
    }

    public Session getSession() {
        return session;
    }

    public void clearCatche(){
        session = null;
    }

    public boolean isRequestingExit(){return requestExit;}
}
