package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;


public class MessageDao {
   public Message createMessage(Message message){

    Connection connection=ConnectionUtil.getConnection();
    String sql="insert into message (posted_by,message_text,time_posted_epoch) values (?,?,?)";
       try {
        PreparedStatement prepareStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        prepareStatement.setInt(1, message.getPosted_by());
        prepareStatement.setString(2, message.getMessage_text());
        prepareStatement.setLong(3, message.getTime_posted_epoch());
         prepareStatement.executeUpdate();
         ResultSet generatedKeys = prepareStatement.getGeneratedKeys();

         if(generatedKeys.next()){
            int id=(int)generatedKeys.getLong(1);
            return new Message(id,message.getPosted_by(),message.getMessage_text(),message.getTime_posted_epoch());
         }
    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    return null;
   }


public List<Message> getAllMessages(){
     List<Message> messages=new ArrayList<>();

     Connection connection=ConnectionUtil.getConnection();

     String sql="select * from message";
    try {
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        if(prepareStatement.execute()){
            ResultSet resultSet = prepareStatement.getResultSet();
            while(resultSet.next()){
                Message message=new Message(resultSet.getInt(1),resultSet.getInt(2),resultSet.getString(3), resultSet.getLong(4));
                messages.add(message);
            }
             return messages;
        }

    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    return null;
}

public Message getMessageById(int id){
   

     Connection connection=ConnectionUtil.getConnection();

     String sql="select * from message where message_id=?";
    try {
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, id);
        if(prepareStatement.execute()){
            ResultSet resultSet = prepareStatement.getResultSet();
            if(resultSet.next()){
                Message message=new Message(resultSet.getInt(1),resultSet.getInt(2),resultSet.getString(3), resultSet.getLong(4));
               return message;
            }
    
        }

    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    return null;
}
public Message deleteMessageById(int id){
    Message message=getMessageById(id);
    if(message!=null){

        Connection connection=ConnectionUtil.getConnection();

     String sql="delete from message where message_id=?";
    try {
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, id);
        int executeUpdate = prepareStatement.executeUpdate();
        if(executeUpdate>0) return message;
    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    

    }
    return null;
}

public Message UpdateMessage(Message message,int id){
    Message m1=getMessageById(id);

    if(m1!=null){

           Connection connection=ConnectionUtil.getConnection();

     String sql="update message set message_text =? where message_id=?";
    try {
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setString(1, message.getMessage_text());
        prepareStatement.setInt(2,id);
        int executeUpdate = prepareStatement.executeUpdate();
        if(executeUpdate>0) return getMessageById(id);
    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

    }
    return null;
}
public List<Message> getMessageByUser(int userId){
    List<Message> messages=new ArrayList<>();

     Connection connection=ConnectionUtil.getConnection();

     String sql="select * from message where posted_by=?";
    try {
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        prepareStatement.setInt(1, userId);
        if(prepareStatement.execute()){
            ResultSet resultSet = prepareStatement.getResultSet();
            while(resultSet.next()){
                Message message=new Message(resultSet.getInt(1),resultSet.getInt(2),resultSet.getString(3), resultSet.getLong(4));
                messages.add(message);
            }
             return messages;
        }

    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    return null;

}
}


