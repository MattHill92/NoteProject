import DAO.*;
import Service.*;
import Model.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;
import io.javalin.http.Cookie;
import io.javalin.http.SameSite;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Date;

public class MessageBoardAPI {

    static MessageBoardDAO dao = new MessageBoardDAO();
    static MessageBoardService service = new MessageBoardService(dao);

    public static void main(String[] args) {

        Javalin app = Javalin.create(config -> {
            config.enableCorsForAllOrigins();
        }).start(7070);

        app.before(ctx -> {
            ctx.header("Access-Control-Allow-Credentials", "true");
            ctx.header("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
        });

        //TEST SESSION ATTRIBUTE
        app.get("/user", ctx -> {
           if (ctx.header("token").equals("null") || ctx.header("token") == null || ctx.header("token").equals(""))
               ctx.result("No Session Found");
           else {
               String jwt = ctx.header("token");
               User user = getUserFromJwt(jwt);
               if (user == null) {
                   ctx.result("No User Found");
                   ctx.header("token","null");
               }
               else {
                   ctx.json(user);
               }
           }
        });





        //post login/ ( supply username and password and receive User object )
        app.post("/login", ctx -> {
            ObjectMapper mapper = new ObjectMapper();
            User requestUser = mapper.readValue(ctx.body(), User.class);
            if (service.attemptLogin(requestUser)){
                String jwt =  generateUserJWT(service.getUserByUsername(requestUser.getUsername()));
                /*
                Cookie cookie = new Cookie("token", jwt);
                cookie.setSecure(true);
                cookie.setSameSite(SameSite.NONE);
                ctx.cookie(cookie);
                 */
                ctx.result(jwt);
            }
        });

        //get user/id
        app.get("/user/id/{id}", ctx -> ctx.json(service.getUserById(Integer.parseInt(ctx.pathParam("id")))));

        //get user/username
        app.get("/user/username/{name}", ctx -> ctx.json(service.getUserByUsername(ctx.pathParam("name"))));

        //post register/ ( supply User object and Service will add it to database )
        app.post("/register", ctx -> {
            ObjectMapper mapper = new ObjectMapper();
            User requestUser = mapper.readValue(ctx.body(), User.class);
            if(!service.registerUser(requestUser)) ctx.result("username taken");
            else {
                requestUser = service.getUserByUsername(requestUser.getUsername());
                String jwt = generateUserJWT(requestUser);
                ctx.result(jwt);
            }
        });

        //get Search/MyPosts/
        app.get("/search/user/{id}", ctx -> ctx.json(service.searchPostsByUserId(Integer.parseInt(ctx.pathParam("id")))));

        //get Search/Recent/
        app.get("/search/recent", ctx -> ctx.json(service.searchPostsByRecent()));

        //get Search/Topic/
        app.get("/search/topic/{topic}", ctx -> ctx.json(service.searchPostsByTopic(ctx.pathParam("topic"))));

        //get Search/Title/
        app.get("/search/title/{substring}", ctx -> ctx.json(service.searchPostsByTitle(ctx.pathParam("substring"))));

        //get Post/
        app.get("/post/{id}", ctx -> ctx.json(service.getPostById(Integer.parseInt(ctx.pathParam("id")))));

        //post Post/
        app.post("/post", ctx -> {
            // some code
            ObjectMapper mapper = new ObjectMapper();
            Post requestPost = mapper.readValue(ctx.body(), Post.class);
            service.addPost(requestPost);
        });

        //put Post/
        app.put("/post/{id}", ctx -> {
            // some code
            ObjectMapper mapper = new ObjectMapper();
            Post requestPost = mapper.readValue(ctx.body(), Post.class);
            service.updatePost(Integer.parseInt(ctx.pathParam("id")), requestPost);
        });


        //delete Post/
        app.delete("/post/{id}", ctx -> {
            service.deletePost(Integer.parseInt(ctx.pathParam("id")));
        });

        //get Comment/
        app.get("/comments/{id}", ctx -> ctx.json(service.getCommentsByPostId(Integer.parseInt(ctx.pathParam("id")))));

        //post Comment/
        app.post("/comment", ctx -> {
            // some code
            ObjectMapper mapper = new ObjectMapper();
            Comment requestComment = mapper.readValue(ctx.body(), Comment.class);
            service.addComment(requestComment);
        });

        //delete Comment/
        app.delete("/comment/{id}", ctx -> {
            service.deleteComment(Integer.parseInt(ctx.pathParam("id")));
        });

    }

    private static String generateUserJWT(User user){
        String jws = null;
        try {
            jws = Jwts.builder()
                    .setIssuer("Project1MessageBoard")
                    .setSubject(user.getUsername())
                    .setId(String.valueOf(user.getId()))
                    // Fri Jun 24 2016 15:33:42 GMT-0400 (EDT)
                    .setIssuedAt(Date.from(Instant.now()))
                    // Sat Jun 24 2116 15:33:42 GMT-0400 (EDT)
                    .setExpiration(Date.from(Instant.now().plusSeconds(600)))
                    .signWith(
                            Keys.hmacShaKeyFor("MySecretKeyIsReallyCoolButWasTooShort12345678910".getBytes("UTF-8")),
                            SignatureAlgorithm.HS256
                    )
                    .compact();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return jws;
    }

    private static User getUserFromJwt(String jwt) {
        Jws<Claims> jws;
        try {
            jws = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor("MySecretKeyIsReallyCoolButWasTooShort12345678910".getBytes("UTF-8")))
                    .requireIssuer("Project1MessageBoard")
                    .build()
                    .parseClaimsJws(jwt);
        }catch(ExpiredJwtException ex) {
            return null;
        }catch(UnsupportedEncodingException ex){
            ex.printStackTrace();
            return null;
        }
        if (jws.getBody().getExpiration().before(Date.from(Instant.now()))) return null;
        int userId = Integer.parseInt(jws.getBody().getId());
        User user = service.getUserById(userId);
        if (!user.getUsername().equals(jws.getBody().getSubject())) return null;
        return user;
    }

}
