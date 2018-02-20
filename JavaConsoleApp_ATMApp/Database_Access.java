/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afdemp_project_main;

/**
 *
 * @author Alekarios
 */

import java.security.MessageDigest;
import java.sql.*;
import java.util.ArrayList;



public class Database_Access {
    
   //pivate fields for usernames and passwords
   private static String username;
   private static String password;
   private static String transactionDateAdmin;
   private static String amountAdmin;
   private static String transactionDateUser;
   private static String amountUser;
   private static ArrayList<String> simpleUsers = new ArrayList<String>();
   private static ArrayList<String> simpleUsers1 = new ArrayList<String>();
   private static ArrayList<String> allUserNames = new ArrayList<String>();
    
 
public void connection(String username,String password) throws ClassNotFoundException, SQLException{//connection method

 
 // Connect to a database
 Connection connection = DriverManager.getConnection
 ("jdbc:mysql://localhost/afdemp_java_1","root","12345");

 //prepare string statement
 String sql = "SELECT * FROM users "
   + "WHERE username = "+"'"+username+"'"+" AND password = "+"'"+password+"'";//return usernames and passwords

 // Create a statement
 Statement statement = connection.createStatement();
 ResultSet resultSet = statement.executeQuery(sql);

 // Iterate through the result and print 
 while (resultSet.next()){
     
     this.username = resultSet.getString(2);//username column
     this.password = resultSet.getString(3);//password column   
 }

 // Close the connection
 connection.close();
 }

//create new user
public void createNewUser(String userName,String passWord,double amount,int user_id) throws SQLException{
    
   
    
    Connection connection = DriverManager.getConnection
    ("jdbc:mysql://localhost/afdemp_java_1","root","12345");
    
    String sql = "INSERT INTO users (username,password) "
            + "VALUES("+"'"+userName+"'"+","+"'"+sha256(passWord)+"'"+");";
    
    String sql1 = "INSERT INTO accounts (user_id,transaction_date,amount) "
            + "VALUES("+"'"+user_id+"',(SELECT CURRENT_TIMESTAMP),"+"'"+amount+"'"+");";
    
    Statement statement = connection.createStatement();
    int resultSet = statement.executeUpdate(sql);
    
    Statement statement1 = connection.createStatement();
    int resultSet1 = statement1.executeUpdate(sql1);
    
    connection.close();
}

//create new deposit

//add to arraylist simple users
public void setSimpleUserArrayList() throws SQLException{
    
    // Connect to a database
 Connection connection = DriverManager.getConnection
 ("jdbc:mysql://localhost/afdemp_java_1","root","12345");
 
  String sql = "SELECT username FROM users"
         + " WHERE username != 'admin'";
  
  // Create a statement
 Statement statement = connection.createStatement();
 ResultSet resultSet = statement.executeQuery(sql);
 
 while(resultSet.next()){
         this.simpleUsers.add(resultSet.getString(1));
         this.simpleUsers1.add(resultSet.getString(1));
     }
 
 connection.close();
}

//gets admin's data account
public void adminAccount() throws SQLException{
    
  // Connect to a database
 Connection connection = DriverManager.getConnection
 ("jdbc:mysql://localhost/afdemp_java_1","root","12345");
 
 String sql = "SELECT transaction_date,amount"
         + " FROM users"
         + " INNER JOIN accounts"
         + " ON users.id = accounts.id"
         + " WHERE username = 'admin'";
 // Create a statement
Statement statement = connection.createStatement();
ResultSet resultSet = statement.executeQuery(sql);
 
 while (resultSet.next()){
     this.transactionDateAdmin = resultSet.getString(1);
     this.amountAdmin = resultSet.getString(2);
 }
 connection.close();
 
}


//gets simple user's account
public void simpleUserAccount(String user) throws SQLException{
    
  // Connect to a database
 Connection connection = DriverManager.getConnection
 ("jdbc:mysql://localhost/afdemp_java_1","root","12345");
 
 String sql = "SELECT transaction_date,amount "
            + "FROM users "
            + "INNER JOIN accounts "
            + "ON users.id = accounts.id "
            + "WHERE username = "+"'"+user+"';";
 // Create a statement
 Statement statement = connection.createStatement();
 ResultSet resultSet = statement.executeQuery(sql);
 
 while (resultSet.next()){
     this.transactionDateUser = resultSet.getString(1);
     this.amountUser = resultSet.getString(2);
 }
 connection.close();
 
}

//deposit money to users and update admin's account
public void depositAdminToSimpleUser(int userId,double deposit) throws SQLException{
    
  // Connect to a database
 Connection connection = DriverManager.getConnection
 ("jdbc:mysql://localhost/afdemp_java_1","root","12345");
 
 String sql = "UPDATE accounts "
              +"SET amount = (SELECT SUM(amount-"+deposit+") WHERE id = '1')"
              +"WHERE id = '1'";
 
 String sql2 = "UPDATE accounts "
              + "SET amount = (SELECT SUM(amount+"+deposit+") WHERE id ="+"'"+(userId+1)+"'"+") "
              +"WHERE id ="+"'"+(userId+1)+"'";
 
 String sql3 = "UPDATE accounts "
              +"SET transaction_date = (SELECT CURRENT_TIMESTAMP)"
              +"WHERE id = '1'";
 
 String sql4 = "UPDATE accounts "
              + "SET transaction_date = (SELECT CURRENT_TIMESTAMP) "
              +"WHERE id ="+"'"+(userId+1)+"'";
 
 // Create a statement
 Statement statement = connection.createStatement();
 double resultSet = statement.executeUpdate(sql);//update admin's account after deposit to simple user's account
 
 Statement statement1 = connection.createStatement();
 double resultSet1 = statement1.executeUpdate(sql2);//update admin's account after deposit to simple user's account
 
 Statement statement2 = connection.createStatement();
 double resultSet2 = statement2.executeUpdate(sql3);//update admin's account date
 
 Statement statement3 = connection.createStatement();
 double resultSet3 = statement3.executeUpdate(sql4);//update user's account date
 
 connection.close();
 
}

//admin withdraw from simple users
public void withdrawAdminFromSimpleUser(int userId,double withdraw) throws SQLException{
    
     // Connect to a database
 Connection connection = DriverManager.getConnection
 ("jdbc:mysql://localhost/afdemp_java_1","root","12345");
 
 String sql = "UPDATE accounts "
              +"SET amount = (SELECT SUM(amount+"+withdraw+") WHERE id = '1')"
              +"WHERE id = '1'";
 
 String sql2 = "UPDATE accounts "
              + "SET amount = (SELECT SUM(amount-"+withdraw+") WHERE id ="+"'"+(userId+1)+"'"+") "
              +"WHERE id ="+"'"+(userId+1)+"'";
 
 String sql3 = "UPDATE accounts "
              +"SET transaction_date = (SELECT CURRENT_TIMESTAMP)"
              +"WHERE id = '1'";
 
 String sql4 = "UPDATE accounts "
              + "SET transaction_date = (SELECT CURRENT_TIMESTAMP) "
              +"WHERE id ="+"'"+(userId+1)+"'";
 
 // Create a statement
 Statement statement = connection.createStatement();
 double resultSet = statement.executeUpdate(sql);//update admin's account after deposit to simple user's account
 
 Statement statement1 = connection.createStatement();
 double resultSet1 = statement1.executeUpdate(sql2);//update admin's account after deposit to simple user's account
 
 Statement statement2 = connection.createStatement();
 double resultSet2 = statement2.executeUpdate(sql3);//update admin's account date
 
 Statement statement3 = connection.createStatement();
 double resultSet3 = statement3.executeUpdate(sql4);//update user's account date
 
 connection.close();
}
//deposit to admin
public void depositToAdmin(double deposit,int id) throws SQLException{
    
    // Connect to a database
 Connection connection = DriverManager.getConnection
 ("jdbc:mysql://localhost/afdemp_java_1","root","12345");
 
 String sql = "UPDATE accounts "
              +"SET amount = (SELECT SUM(amount+"+deposit+") WHERE id = '1') "
              +"WHERE id = '1'";
 
 String sql2 = "UPDATE accounts "
              +"SET amount = (SELECT SUM(amount-"+deposit+") WHERE id = "+"'"+(id+1)
              +"'"+") WHERE id = "+"'"+(id+1)+"';";
 
 String sql3 = "UPDATE accounts "
              + "SET transaction_date = (SELECT CURRENT_TIMESTAMP) "
              +"WHERE id ="+"'"+(id+1)+"'";
 
 Statement statement = connection.createStatement();
 double resultSet = statement.executeUpdate(sql);//deposit to admin account
 
 Statement statement1 = connection.createStatement();
 double resultSet1 = statement1.executeUpdate(sql2);//deposit to admin account
 
 Statement statement2 = connection.createStatement();
 double resultSet2 = statement2.executeUpdate(sql3);//update transaction date
 
 connection.close();
 
}

//deposit from member to another member
public void depositMemberToMember(double deposit,int depositId,int withdrawId) throws SQLException{
    
     // Connect to a database
 Connection connection = DriverManager.getConnection
 ("jdbc:mysql://localhost/afdemp_java_1","root","12345");
    
 String sql = "UPDATE accounts "
              +"SET amount = (SELECT SUM(amount+"+deposit+") WHERE id = "+"'"+(depositId+1)
              +"'"+") WHERE id = "+"'"+(depositId+1)+"';";
 
 String sql2 = "UPDATE accounts "
              +"SET amount = (SELECT SUM(amount-"+deposit+") WHERE id = "+"'"+(withdrawId+1)
              +"'"+") WHERE id = "+"'"+(withdrawId+1)+"';";
 
 String sql3 = "UPDATE accounts "
              + "SET transaction_date = (SELECT CURRENT_TIMESTAMP) "
              +"WHERE id ="+"'"+(depositId+1)+"'";
 
  String sql4 = "UPDATE accounts "
              + "SET transaction_date = (SELECT CURRENT_TIMESTAMP) "
              +"WHERE id ="+"'"+(withdrawId+1)+"'";
 
 Statement statement = connection.createStatement();
 double resultSet = statement.executeUpdate(sql);//deposit to admin account
 
 Statement statement1 = connection.createStatement();
 double resultSet1 = statement1.executeUpdate(sql2);//deposit to admin account
 
 Statement statement2 = connection.createStatement();
 double resultSet2 = statement2.executeUpdate(sql3);//update transaction date
 
 Statement statement3 = connection.createStatement();
 double resultSet3 = statement3.executeUpdate(sql4);//update transaction date
}

//return username
public String getUsername(){
    
    return this.username;
}

//return password
public String getPassword(){
     
    return this.password;
     
}

//return last transaction date
public String getTransactionDateAdmin(){
    
    return this.transactionDateAdmin;  
}

//return admin's ammount
public String getAmmountAdmin(){
   
    return this.amountAdmin;
    
}

//return usernames of the simple users
public ArrayList<String> getSimpleUsers(){
    
    return this.simpleUsers;
}

//return last transaction date of simple user
public String getTransactionDateUser(){
    
    return this.transactionDateUser;
}

//return user's amount
public String getAmountUser(){
    
    return this.amountUser;
}

public ArrayList<String> getSimpleUsersB(){//helping method
    
    return this.simpleUsers1;
}

//encryption
public  String sha256(String base) {
    try{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(base.getBytes("UTF-8"));
        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    } catch(Exception ex){
       throw new RuntimeException(ex);
    }
}

//update encrypted password
public void encryption(String user,String pass) throws SQLException{

       // Connect to a database
       Connection connection = DriverManager.getConnection
       ("jdbc:mysql://localhost/afdemp_java_1","root","12345");

    String sql = "UPDATE users SET password = "+"'"+sha256(pass)+"'"+" WHERE username = "+"'"+user+"';";
    
    Statement statement = connection.createStatement();
    int resultSet = statement.executeUpdate(sql);
    
    
     connection.close();
   
}

public void allUserNames() throws SQLException{
    
    // Connect to a database
 Connection connection = DriverManager.getConnection
 ("jdbc:mysql://localhost/afdemp_java_1","root","12345");
 
  String sql = "SELECT username FROM users;";
         
  
  // Create a statement
 Statement statement = connection.createStatement();
 ResultSet resultSet = statement.executeQuery(sql);
 
 while(resultSet.next()){
         this.allUserNames.add(resultSet.getString(1));
         
     }
 
 connection.close();
}

public ArrayList<String> getAllUserNames(){
    
    return this.allUserNames;
}
  

}


    

