package Service;

import java.util.List;

import DAO.MessageDao;
import Model.Message;

public class MessageService {
    MessageDao messageDao;

    public MessageService(){
        messageDao=new MessageDao();
    }
   MessageService(MessageDao messageDao){
       this.messageDao=messageDao;
   }
public Message createMessage(Message message){
    Message m=messageDao.createMessage(message);
    System.out.println("message service"+m);
    return m;
}
public List<Message> getAllMessages(){
    return messageDao.getAllMessages();
}
public Message getMessageById(int id){
    return messageDao.getMessageById(id);
}
public Message deleteMessageById(int id){
  return messageDao.deleteMessageById(id);
}
public Message updateMessageById(Message message,int id){
  return  messageDao.UpdateMessage(message, id);
}
public List<Message> getMessageByUser(int id){
 return messageDao.getMessageByUser(id);
}
}
