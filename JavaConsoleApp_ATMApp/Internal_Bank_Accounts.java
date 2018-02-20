/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afdemp_project_main;

import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alekarios
 */
public class Internal_Bank_Accounts extends File_Access {
   
    private static double DepositToMember; 
    private static double WithdrawFromMember;
    private static double DepositAmountToUser;
    private static double DepositAmountToAdmin;
    private static String DepostitToUser;
    private static String WithdrawFromUser;
    private static String DepositFromMemberToMember;
    private static String choiceUser;
    private static boolean Flag_Admin1 = false;
    private static boolean Flag_Admin2 = false;
    private static boolean Flag_Admin3 = false;
    private static boolean Flag_Admin4 = false;
    private static boolean Flag_User1 = false;
    private static boolean Flag_User2 = false;
    private static boolean Flag_User3 = false;
    private StringBuffer Buffer = new StringBuffer();
    private StringBuffer Buffer1 = new StringBuffer();
    NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("el", "Gr"));
   
       //check choices of admin
    public  void adminChoice() throws SQLException, ClassNotFoundException, IOException, InterruptedException{
        
         super.adminAccount();
         super.simpleUserAccount(super.getUserLogIn());
         
        while(super.getModeAdmin()){
            
         System.out.println("Enter operation:");
         try{
         int ChoiceModeAdmin = input.nextInt();
         
         switch(ChoiceModeAdmin){
             //print out admin's account data
             case 1:
                 this.Flag_Admin3 = true;
                 System.out.println("\n"+"Last update: "
                         +super.getTransactionDateAdmin()+" Ammount: "
                                 +super.getAmmountAdmin()+"\n");
                 Buffer.append(toString()+"\r\n");
                 this.Flag_Admin3 = false;
                 break;
             //print out simple user's names    
             case 2:
                 clear();
                 availableSimpleUsers() ;
                 System.out.println("choose user's account");
                 this.choiceUser = input.next();
                 if(super.getSimpleUsers().contains(this.choiceUser)){
                     this.Flag_Admin4 = true;
                    super.simpleUserAccount(this.choiceUser);
                    System.out.println("\n"+"Last update: "
                         +super.getTransactionDateUser()+" Ammount: "
                                 +super.getAmountUser()+"\n"); 
                    Buffer.append(toString()+"\r\n");
                    this.Flag_Admin4 = false;
                 }
                 else{
                     System.out.println("incorrect user");
                 }
                 break;
                //choosing users to deposit money
             case 3:
                 clear();
                 
                 availableSimpleUsers();
                 for(int i = 0;i <= super.getSimpleUsers().size()-1; i++){
                     
                 this.Flag_Admin1 = true;
                 
                 System.out.println("choose user's account you want to deposit money");
                 this.DepostitToUser = input.next();
                 
                 if(super.getSimpleUsers().contains(this.DepostitToUser)){
                    System.out.println("Give amount of money you want to deposit");
                    this.DepositToMember = input.nextDouble();
                    super.adminAccount();//execute query to database
                    
                    if(DepositToMember <= Double.parseDouble(super.getAmmountAdmin())){//checks if there ara enough money to account
                        
                        super.depositAdminToSimpleUser(super.getSimpleUsers().indexOf(this.DepostitToUser)+1,this.DepositToMember);//deposit to simple users
                        Buffer.append(toString()+"\r\n");
                        this.Flag_Admin1 = false;
                        
                    }
                    else{
                        System.out.println("not enough money");
                    }
                 }
                 else{
                    System.out.println("incorrect user");
                 }
                  }
                 
                 break;
                //withdraw money from simple users 
             case 4:
                clear();
                 
                 availableSimpleUsers();
                  for(int i = 0;i <= super.getSimpleUsers().size()-1; i++){
                      
                  this.Flag_Admin2 = true;
                  
                 System.out.println("choose user's account you want to withdraw money");
                 this.WithdrawFromUser = input.next();
                 
                 if(super.getSimpleUsers().contains(this.WithdrawFromUser)){//check if username exists in list
                     System.out.println("Give amount of money you want to withdraw");
                     this.WithdrawFromMember = input.nextDouble();
                     
                     super.simpleUserAccount(this.WithdrawFromUser);//execute query to database
                     
                     if(this.WithdrawFromMember <= Double.parseDouble(super.getAmountUser())){
                         
                         super.withdrawAdminFromSimpleUser
                        (super.getSimpleUsers().indexOf(this.WithdrawFromUser)+1, this.WithdrawFromMember); 
                          Buffer.append(toString()+"\r\n");
                          this.Flag_Admin2 = false;
                          
                         
                     }
                     else{
                         System.out.println("Account doesn't have enough money");
                     }
                 }
                 else{
                     System.out.println("incorrect user");
                 }
                  }
                 
                 break;
                 
             case 5:
                 clear();
                 
                 super.writeToFile(super.getUserLogIn(),Buffer.toString());
                 break;
                 
             case 6:
                 System.exit(0);
                 break;
                 
             default:
                 System.out.println("Invalid operation");
                 break;
         }
         ////
        }
         catch(InputMismatchException e){
             System.out.println("Invalid input.");
             break;
         }
        }
    }
    
    //check choices of user
    public void userChoice() throws SQLException, ClassNotFoundException, IOException, InterruptedException{
        
        while(super.getModeUser()){
            
         System.out.println("Enter operation:");
         try{
         int ChoiceModeUser = input.nextInt();
         
         switch(ChoiceModeUser){
             
             case 1:
                 this.Flag_User3 = true; 
                 super.simpleUserAccount(super.getUserLogIn());//execetu query to database
                 System.out.println("\n"+"Last update: "
                         +super.getTransactionDateUser()+" Ammount: "
                                 +super.getAmountUser()+"\n");
                  Buffer1.append(toString()+"\r\n");
                  this.Flag_User3 = false; 
                 break;
             case 2:
                 
                 clear();
                 this.Flag_User1 = true;
                 System.out.println("Enter amount of money you want to deposit to admin");
                 this.DepositAmountToAdmin = input.nextDouble();
                 super.simpleUserAccount(super.getUserLogIn());
                 
                 if(this.DepositAmountToAdmin <= Double.parseDouble(super.getAmountUser())){
                     
                     super.depositToAdmin
                    (this.DepositAmountToAdmin,super.getSimpleUsers().indexOf(super.getUserLogIn())+1);//update user's account
                     Buffer1.append(toString()+"\r\n");
                     this.Flag_User1 = false;
                 }
                 else{
                     System.out.println("not enough money");
                 }
                 break;
             case 3:
                 clear();
                 this.Flag_User2 = true;
                 availableSimpleUsers();
                 System.out.println("Select user you want to deposit money");
                 this.DepositFromMemberToMember = input.next();
                 if(super.getSimpleUsers().contains(this.DepositFromMemberToMember)){
                     
                    System.out.println("Enter amount of deposit");
                    this.DepositAmountToUser = input.nextDouble();
                     super.simpleUserAccount(super.getUserLogIn());//execute query to database
                     
                    if(this.DepositAmountToUser <= Double.parseDouble(super.getAmountUser())){//check if there are enough money in account
                        
                    super.depositMemberToMember(this.DepositAmountToUser,
                         super.getSimpleUsersB().indexOf(this.DepositFromMemberToMember)+1,
                         super.getSimpleUsersB().indexOf(super.getUserLogIn())+1);
                         Buffer1.append(toString()+"\r\n");
                         this.Flag_User2 = false;
                    }
                    else{
                        System.out.println("not enough money");
                    }
                 }
                 else{
                     System.out.println("incorrect user");
                 }
                 break;
             case 4:
                 
                 clear();
                 
                 super.writeToFile(super.getUserLogIn()
                         .substring(0,4)+"_"+
                         super.getUserLogIn().substring(4,5),Buffer1.toString());
                 break;
             case 5:
                 
               System.exit(0);
                 break;
                 
             default:
                 
                 System.out.println("Invalid operation");
                 break;
            
        }
         }
         catch(InputMismatchException e){
             
             System.out.println("Invalid input.");
             break;
         }
    }

    }
    //print out all the simple users from database 
    public void availableSimpleUsers(){
    if(super.getUserLogIn().equals("admin")){
        
     for(int i = 0;i <= super.getSimpleUsers().size()-1; i++){
         
                      System.out.println((i+1)+
                              "."+super.getSimpleUsers().get(i));
     }
    }
    else{//remove admin from arraylist
        int userPos = super.getSimpleUsers().indexOf(super.getUserLogIn());
        super.getSimpleUsers().remove(userPos);
        
        for(int i = 0;i <= super.getSimpleUsers().size()-1; i++){
            
                      System.out.println((i+1)+
                              "."+super.getSimpleUsers().get(i));
                   
     }
        super.getSimpleUsers().add(userPos, super.getUserLogIn());
        
    }
    }
    
    @Override
    public String toString(){try {
        //prepare content for writing to log file
        super.adminAccount();
        super.simpleUserAccount(super.getUserLogIn());
        String StringDepositToMember = formatter.format(this.DepositToMember);
        String StringGetAmmountAdmin = formatter.format(Double.parseDouble(super.getAmmountAdmin()));
        String StringWithdrawFromMember = formatter.format(this.WithdrawFromMember);
        
        String StringDepositAmountToAdmin = formatter.format(this.DepositAmountToAdmin);
        String StringGetAmountUser = formatter.format(Double.parseDouble(super.getAmountUser()));
        String StringDepositAmountToUser = formatter.format(this.DepositAmountToUser);
        
        if(this.Flag_Admin1 == true) {

            return  "\n"+"Username: "+super.getUserLogIn()+"\n"+
                    "\t"+"\t"+"Deposit: "+StringDepositToMember+
                    " TO "+this.DepostitToUser+
                    "\t"+"\t"+"Transaction Date: "+super.getTransactionDateAdmin()+
                    "\t"+"\t"+"Current Balance: "+StringGetAmmountAdmin;
            
        }
        else if(this.Flag_Admin2 == true){

            return "\n"+"Username: "+super.getUserLogIn()+"\n"+
                    "\t"+"\t"+"Withdraw : "+StringWithdrawFromMember+
                    " FROM "+this.WithdrawFromUser+
                    "\t"+"\t"+"Transaction Date: "+super.getTransactionDateAdmin()+
                    "\t"+"\t"+"Current Balance: "+StringGetAmmountAdmin;
        }
        
        else if(this.Flag_Admin3 == true){
            
            return "Admin checked his account";
                        
        }
        
        else if(this.Flag_Admin4 == true){
            
            return "Admin checked "+this.choiceUser+"'s account";
        }
        
        else if (this.Flag_User1 == true){

            return "\n"+"Username: "+super.getUserLogIn()+"\n"+
                    "\t"+"\t"+"Deposit: "+StringDepositAmountToAdmin+
                    " TO "+"Admin"+
                    "\t"+"\t"+"Transaction Date: "+super.getTransactionDateUser()+
                    "\t"+"\t"+"Current Balance: "+StringGetAmountUser;
        }
        else if(this.Flag_User2 == true){
  
            return "\n"+"Username: "+super.getUserLogIn()+"\n"+
                    "\t"+"\t"+"Deposit: "+StringDepositAmountToUser+
                    " TO "+this.DepositFromMemberToMember+
                    "\t"+"\t"+"Transaction Date: "+super.getTransactionDateUser()+
                    "\t"+"\t"+"Current Balance: "+StringGetAmountUser;
        }
        
        else if(this.Flag_User3 == true){
            
            return super.getUserLogIn()+" checked his account";
        }
        
        else{
            
            return "Failed to get data";
        }
        } catch (SQLException ex) {
            Logger.getLogger(Internal_Bank_Accounts.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

  
}

    //clear cmd
   public void clear() throws ClassNotFoundException, SQLException, IOException, InterruptedException{
            System.out.println("\n");
            new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
            super.Screen();

    }
   
}
