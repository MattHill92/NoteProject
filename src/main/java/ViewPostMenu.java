import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class ViewPostMenu {

    Post post;
    Session session;
    Scanner sc;

    int commentsPage = 0;
    public ViewPostMenu(Post post, Session session, Scanner sc){
        this.post = post;
        this.session = session;
        this.sc = sc;
    }
    public void runMenu() {
        do {
            displayPost();
            displayComments();
            if (post.getUsername().equals(session.username))
                System.out.println("Comment // Page // Edit // DeletePost // DeleteComment // Back");
            else
                System.out.println("Comment // Page // DeleteComment // Back");

            String input = sc.nextLine();
            Command command = Command.parseCommand(input);

            switch (command.command) {
                case "comment":
                    leaveComment(command.target);
                    break;
                case "page":
                    commentsPage = Integer.valueOf(command.target)-1;
                    break;
                case "edit":
                    editPost();
                    break;
                case "deletepost":
                    if(deletePost())
                        return;
                    break;
                case "deletecomment":
                    deleteComment(Integer.valueOf(command.target));
                    break;
                case "back":
                    return;
            }
        } while (true);
    }

    private boolean deletePost() {
        if(!post.getUsername().equals(session.username)) return false;
        System.out.print("Are you sure you want to delete this post? (y/n) ");
        if (sc.nextLine().equalsIgnoreCase("y")){
            session.requestDeletePost();
            return true;
        }
        return false;
    }

    private void editPost() {
        if(!post.getUsername().equals(session.username)) return;
        System.out.println("Editing post: leave section blank for 'no changes'");
        System.out.print("New Title: ");
        String title = sc.nextLine();
        if (title.equals("")) title = post.getTitle();
        System.out.print("New  Body: ");
        String body = sc.nextLine();
        if (body.equals("")) body = post.getBody();
        session.requestUpdatePost(title, body);

    }

    private void leaveComment(String comment) {
        if (comment.equals("")){
            System.out.print("Comment: ");
            comment = sc.nextLine();
        }
        session.requestLeaveComment(comment);
        post = session.getPost();
    }

    private void deleteComment(int targetIndex) {
        if (post.getUsername().equals(session.username) || post.getComments().get(targetIndex-1).username.equals(session.username)) {
            session.requestDeletePostComment(targetIndex - 1);
            post = session.getPost();
        }
    }


    private void displayPost() {
        System.out.println("\n\n\n\nTitle:");
        System.out.println(String.format("%-80s", post.getTitle()) + "    " +  String.format("%-3s", post.getView_count()) + " views");
        System.out.println("==============================================================================================");
        System.out.println("topic: " + String.format("%-20s", post.getTopic()) + String.format("%67s", "posted on " + post.getDate() + " by " + post.getUsername()));
        System.out.println("\n\n\t" + post.getBody() + "\n\n\n");


    }

    private void displayComments() {
        ArrayList<Comment> comments = post.getComments();

        System.out.println("Comments - page " + (Integer.valueOf(commentsPage)+1) + " of " +  Math.max((int)(Math.ceil( (double)comments.size()/5) ), 1)  );
        System.out.println("==============================================================================================");

        for(int i = commentsPage*5; i < (commentsPage+1)*5; i++)
            if (i < comments.size()) {
                System.out.println();
                System.out.println(String.format("%-3s", i+1) + " | " + String.format("%-20s", comments.get(i).getUsername()+":") + comments.get(i).getDate());
                System.out.println("    | " + comments.get(i).getBody());
                System.out.println("      -------------------------------------------------");
            }else System.out.println("\n\n\n");
        System.out.println("==============================================================================================");
    }

}
