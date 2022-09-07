


import java.util.Scanner;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Run {

    public static Logger logger = LogManager.getLogger(Run.class);


    Scanner sc = new Scanner(System.in);
    Session session;
    DatabaseManager dm = new DatabaseManager();
    LogInManager lm = new LogInManager(dm);
    LogInMenu logInMenu = new LogInMenu(sc, lm);
    SessionMenu sessionMenu = new SessionMenu(sc);

    public void run(){
        logger.info("Application Started");
        do {

            logInMenu.runMenu();
            session = logInMenu.getSession();

            if (session != null) {
                sessionMenu.setSession(session);
                sessionMenu.runMenu();
                logger.info("User "+session.username+" logged out.");
            }


            lm.logout();
            logInMenu.clearCatche();
            session = null;

        } while (!logInMenu.isRequestingExit());
    }
}
