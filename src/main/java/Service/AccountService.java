package Service;

import DAO.AccountDao;
import Model.Account;

public class AccountService {

      AccountDao accountDao;

      public AccountService(){
        accountDao= new AccountDao();
      }
    public AccountService(AccountDao ado){
        this.accountDao=ado;
    }
     public Account registerAccount(Account account){
    Account c= accountDao.registerUser(account);
     System.out.println(account);
    System.out.println("hello wlrl"+c);
    return c;
             
     }
}
