


import java.util.Scanner;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Run {

    public static Logger logger = LogManager.getLogger(Run.class);


    Scanner sc = new Scanner(System.in);
    NoteSession ns;
    DatabaseManager dm = new DatabaseManager();
    LogInManager lm = new LogInManager(dm);
    LogInMenu logInMenu = new LogInMenu(sc, lm);
    NoteSessionMenu noteMenu = new NoteSessionMenu(sc);

    public void run(){
        logger.info("Application Started");
        do {

            logInMenu.runMenu();
            ns = logInMenu.getSession();

            if (ns != null) {
                noteMenu.setSession(ns);
                noteMenu.runMenu();
                logger.info("User "+ns.username+" logged out.");
            }


            lm.logout();
            logInMenu.clearCatche();
            ns = null;

        } while (!logInMenu.isRequestingExit());
    }
}
