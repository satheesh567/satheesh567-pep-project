package Controller;

import static org.mockito.ArgumentMatchers.anyCollection;

import java.nio.channels.SocketChannel;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.LoginService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    LoginService loginService;
    MessageService messageService;
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     * 
     * 
     */
    public SocialMediaController(){
        accountService=new AccountService();
        loginService=new LoginService();
        messageService=new MessageService();
       
    }
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("register",this::registerHandler);
        app.post("login",this::loginHandler);
        app.post("messages",this::createMessageHandler);
        app.get("messages",this::getAllMessages);
        app.get("messages/{message_id}",this::getMessageByID);
        app.delete("messages/{message_id}",this::deleteMessageById);
        app.patch("messages/{message_id}",this::updateMessageHandler );
        app.get("accounts/{account_id}/messages",this::messageUserHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    private void registerHandler(Context ctx) throws  JsonProcessingException{
        ObjectMapper om=new ObjectMapper();
        Account ac=om.readValue(ctx.body(),Account.class);
        Account ac1=accountService.registerAccount(ac);
        if(ac1!=null&&validate(ac.username, ac.password)){
           ctx.json(om.writeValueAsString(ac1));
        }
        else{
            ctx.status(400);
        }
        
    }
    private void loginHandler(Context ctx) throws  JsonProcessingException{
         ObjectMapper om=new ObjectMapper();
         Account ac=om.readValue(ctx.body(),Account.class);
           Account ac1=loginService.loginUser(ac);

           if(ac1!=null){
            ctx.json(om.writeValueAsString(ac1));
           }
           else{
            ctx.status(401);
           }
    }

    private void createMessageHandler(Context ctx) throws JsonProcessingException{
         ObjectMapper objectMapper = new ObjectMapper();
         Message message1=objectMapper.readValue(ctx.body(), Message.class);
         if(validateMessage(message1)==false){
            ctx.status(400);
            return;
         }
    
         Message message2=messageService.createMessage(message1);
         if(message2!=null){
            ctx.json(objectMapper.writeValueAsString(message2));
         }
         else
         {
            ctx.status(400);
         }
         
    }

    private void getAllMessages(Context ctx){
          ctx.json(messageService.getAllMessages());
    }
    private void getMessageByID(Context ctx) throws JsonProcessingException{
         ObjectMapper objectMapper = new ObjectMapper();
        String pathParam = ctx.pathParam("message_id");
       Message message= messageService.getMessageById(Integer.parseInt(pathParam));
        if(message!=null)
        ctx.json(objectMapper.writeValueAsString(message));
    }

    private void deleteMessageById(Context ctx) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        String pathParam = ctx.pathParam("message_id");
       Message message= messageService.deleteMessageById(Integer.parseInt(pathParam));
        if(message!=null)
        ctx.json(objectMapper.writeValueAsString(message));
    }
    private void updateMessageHandler(Context ctx) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        String pathParam = ctx.pathParam("message_id");
         Message message=objectMapper.readValue(ctx.body(), Message.class);
        if(validateMessage(message))
        {
           int id=Integer.parseInt(pathParam);
           Message message1=messageService.updateMessageById(message, id);
           if(message1!=null){
            ctx.json(objectMapper.writeValueAsString(message1));
            }
          else{
            ctx.status(400);
           }
         }
         else{
            ctx.status(400);
         }

    }

    private void messageUserHandler(Context ctx){
        String pathParam = ctx.pathParam("account_id");
        List<Message> message= messageService.getMessageByUser(Integer.parseInt(pathParam));
        if(message!=null)
           ctx.json(message);
        

    }

    private boolean validateMessage(Message message){
        if(message.message_text.equals("")||message.message_text.length()>255) return false;
        return true;
    }

    private boolean validate(String username,String password){
        if(username.equals("")||password.length()<4){
            return false;
        }
        return true;
    }





}