import java.util.ArrayList;
import java.util.Scanner;

public class SessionMenu {

    Scanner sc;
    Session session;
    private int postPage = 0;
    String previousSearch = "recent";

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
                    searchPosts(previousSearch, postPage);
                    break;
                case "page":
                    changePage(command.target);
                    break;
                case "search":
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
        if (target.equals("")) System.out.println("view post by #: \"view 3\" to view post number 3");
        if (!target.matches("-?\\d+")) {
            System.out.println("invalid view statement use post #: \"view 3\"");
        }
        int post_id = session.getSearchResults().get(Integer.valueOf(target) - 1).post_id;
        Post post = session.fetchPost(post_id);
        ViewPostMenu vm = new ViewPostMenu(post, session, sc);
        vm.runMenu();
    }

    private void changePage(String target) {
        postPage = Integer.valueOf(target)-1;
    }

    private void searchPosts(String target) {
        searchPosts(target, 0);
    }
    private void searchPosts(String target, int page) {
        if(target.equals("")){
            System.out.println("Include a topic to search for: \"search sports\"");
            System.out.println("You can also search titles with -t: \"search -t football\"");
            System.out.println("You can search recent with: \"search recent\" or you can search your own posts with: \"search myposts\"");
            return;
        }else if(target.equals("recent")){
            session.fetchRecentSearchResults();
        }else if(target.equals("myposts")){
            session.fetchMyPostResults();
        }else if (target.startsWith("-t ")){
            session.fetchTitleResults(target.substring(3));
        }else{
            session.fetchTopicResults(target);
        }
        previousSearch = target;
        postPage = page;
    }

    private void createPost(String target) {
        String title;
        if (target.equals("")){
            System.out.print("Title: ");
            title = sc.nextLine();
        }else title = target;
        System.out.print("Topic: ");
        String topic = sc.nextLine();
        System.out.println("Body: ");
        String body = sc.nextLine();

        session.requestCreatePost(title, topic, body);
        ViewPostMenu vm = new ViewPostMenu(session.getPost(), session, sc);
        vm.runMenu();
    }

    private void displaySearch() {
        ArrayList<SearchResult> results = session.getSearchResults();
        System.out.println();
        System.out.println(" # |   topic    |                  post                    |views|         upload date   ");
        System.out.println("==============================================================================================");
        for(int i = postPage*10; i < (postPage+1)*10; i++){
            if (i < results.size()) {
                System.out.print("\n" + String.format("%-3s", String.valueOf(i + 1)));
                System.out.println(results.get(i));
            }
            else {
                System.out.print("\n   ");
                System.out.println("| " + String.format("%-10.10s", "") + " | " + String.format("%-40.40s", "") + " |     |");
            }
        }
        System.out.println("\nPage " + (Integer.valueOf(postPage)+1) + " of " +  Math.max((int)(Math.ceil( (double)results.size()/10) ), 1)  );
        System.out.println("==============================================================================================");
        System.out.println();
    }


}
