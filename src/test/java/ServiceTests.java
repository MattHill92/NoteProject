import Model.User;
import Service.MessageBoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTests {
    DummyMessageBoardDAO dao = new DummyMessageBoardDAO();
    MessageBoardService service = new MessageBoardService(dao);

    @BeforeEach
    void setUp() {
        dao.methodCalled = false;
    }

    @Test
    void UserMethodTest1() {
        //check to see if addUser was called on database
        service.registerUser(new User(0, "null", "null", "null"));
        assertTrue(dao.methodCalled);
    }

    @Test
    void UserMethodTest2() {
        //check not null return
        User result = service.getUserById(0);
        assertNotNull(result);
    }

    @Test
    void UserMethodTest3() {
        //check password replaced
        User result = service.getUserById(0);
        assertEquals("*****", result.getPassword());
    }

    @Test
    void UserMethodTest4() {
        //check login
        User user = new User(0, "null", "null","null");
        Boolean result = service.attemptLogin(user);
        assertTrue(result);
    }

    @Test
    void AddPostTest1() {
        //check to see if dao was called
        service.addPost(dao.post);
        assertTrue(dao.methodCalled);
    }

    @Test
    void AddCommentTest1() {
        //check to see if dao was called
        service.addComment(dao.comment);
        assertTrue(dao.methodCalled);
    }
}
