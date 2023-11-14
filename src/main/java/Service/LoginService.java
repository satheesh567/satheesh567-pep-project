package Service;

import DAO.LoginDao;
import Model.Account;

public class LoginService {
    public LoginDao loginDao;
    public LoginService(){
        loginDao=new LoginDao();
    }
    public LoginService(LoginDao log){
       loginDao=log;
    }

    public Account loginUser(Account accounnt){
          Account ac=loginDao.loginUser(accounnt);
          System.out.println("Login service "+ac);
          return ac;
    }

}
