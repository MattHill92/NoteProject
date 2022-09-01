import java.util.ArrayList;
import java.util.Arrays;

public class Session {

    String username;
    ArrayList<String> noteTitles = new ArrayList<>();
    ArrayList<String> noteBodies = new ArrayList<>();
    DatabaseManager database;

    public Session(String username, String[] noteTitles, String[] noteBodies, DatabaseManager database) {
        this.username = username;
        this.noteTitles.addAll(Arrays.asList(noteTitles));
        this.noteBodies.addAll(Arrays.asList(noteBodies));
        this.database = database;
    }

    public void addNote(String title, String body) {
        if (checkNameAlreadyExists(title)) return;

        database.addNote(username, title, body);
        refreshNotes();

        Run.logger.info("User " + username + " added note " + title);
    }

    public void removeNote(String title) {
        if (!checkCanFindName(title)) return;

        database.removeNote(username, title);
        refreshNotes();

        Run.logger.info("User " + username + " removed note \"" + title + "\"");
    }

    public void renameNote(String oldTitle, String newTitle){
        if (!checkCanFindName(oldTitle)) return;
        if (checkNameAlreadyExists(newTitle)) return;

        database.renameNote(username, oldTitle, newTitle);
        refreshNotes();

        Run.logger.info("User " + username + " renamed note \"" + oldTitle +"\" to \"" + newTitle + "\"" );
    }

    private boolean checkNameAlreadyExists(String newTitle) {
        if (!noteTitles.contains(newTitle)) return false;
        String message = "Instruction failed: File named \""+ newTitle +"\" already exists.";
        System.out.println(message);
        Run.logger.info(message);
        return true;
    }

    private boolean checkCanFindName(String title) {
        if (noteTitles.contains(title)) return true;
        String message = "Rename failed: Cannot find \""+ title +"\".";
        System.out.println(message);
        Run.logger.info(message);
        return false;
    }

    public void printNotes(){
        System.out.println("\n" + username + " has " + noteTitles.size()+" notes:\n");
        for (int i = 0; i < noteTitles.size(); i++){
            if (noteTitles.get(i) != null)
                System.out.println(noteTitles.get(i) + ": " + noteBodies.get(i));
        }
        System.out.println();
    }

    private void refreshNotes() {

    }

    public ArrayList<String> getNoteTitles(){
        return noteTitles;
    }

    public ArrayList<String> getNoteBodies(){
        return noteBodies;
    }

}
