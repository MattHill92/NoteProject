public class LogInManager {

    private Session session;
    private DatabaseManager dm;

    public LogInManager(DatabaseManager dm){
        this.dm = dm;
    }

    public Boolean registerAccount(String username, String password, String confirmPassword){
        if (checkUsernameTaken(username)) return false;
        if (checkPasswordsMatch(password, confirmPassword)) return false;

        String salt = PasswordHelper.generateSalt();
        String hashedPass = PasswordHelper.saltAndHash(password, salt);
        dm.addUser(username, hashedPass, salt);
        session = dm.getUserSession(username);

        String message = "Register Successful, logged in as " + username;
        System.out.println(message);
        Run.logger.info(message);

        return true;
    }

    private static boolean checkPasswordsMatch(String password, String confirmPassword) {
        if (password.equals(confirmPassword)) return false;
        String message = "Register Failed: Passwords do not match";
        System.out.println(message);
        Run.logger.info(message);
        return true;
    }

    private boolean checkUsernameTaken(String username) {
        if (!dm.checkUsernameExists(username)) return false;
        String message = "Register Failed: Username Taken";
        System.out.println(message);
        Run.logger.info(message);
        return true;
    }

    public Boolean login(String givenUsername, String givenPassword){

        if (!checkUserExists(givenUsername)) return false;

        String password = dm.getUserPassword(givenUsername);
        String salt = dm.getUserSalt(givenUsername);
        givenPassword = PasswordHelper.saltAndHash(givenPassword, salt);

        if (checkIncorrectPassword(givenPassword, password)) return false;

        session = dm.getUserSession(givenUsername);

        String message = "Successful: logged in as " + givenUsername;
        System.out.println(message);
        Run.logger.info(message);
        return true;
    }

    private boolean checkIncorrectPassword(String givenPassword, String password) {
        if (password.equals(givenPassword)) return false;

        String message = "Login Failed: Username / Password incorrect";
        System.out.println(message);
        Run.logger.info(message);
        return true;
    }

    private boolean checkUserExists(String givenUsername) {
        if (dm.checkUsernameExists(givenUsername)) return true;
        String message = "Login Failed: Username not found";
        System.out.println(message);
        Run.logger.info(message);
        return false;
    }

    public Session getSession(){
        return session;
    }

    public void logout(){
        session = null;
    }

}
