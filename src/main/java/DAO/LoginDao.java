package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Account;
import Util.ConnectionUtil;

public class LoginDao {
     public Account loginUser(Account account){
        Connection connection=ConnectionUtil.getConnection();
        String sql="select * from account where username= ? and password= ?";
        try {
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.execute();
            ResultSet rs=ps.getResultSet();
            if(rs.next()){
               return new Account(rs.getInt(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }
    public Account getAccountById(int id){
        Connection connection=ConnectionUtil.getConnection();
        String sql="select * from account where account_id=?";
        
        try {
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
             
            prepareStatement.setInt(1,id);

            prepareStatement.execute();
           ResultSet rs= prepareStatement.getResultSet();

           if(rs.next()){
            Account ac=new Account();
              ac.setAccount_id(rs.getInt(1));
              ac.setUsername(rs.getString(2));
               ac.setPassword(rs.getString(3));

               return ac;
           }
           

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       return null;
    }
}
