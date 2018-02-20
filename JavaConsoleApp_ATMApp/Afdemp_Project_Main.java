/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afdemp_project_main;

import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author Alekarios
 */



public class Afdemp_Project_Main {

    /**
     * @param args the command line arguments
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException, InterruptedException   {
        
        //Creation of necessary instances
        Database_Access connection = new Database_Access();
        Login_Screen login = new Login_Screen();
        Application_menu menu = new Application_menu();
        Internal_Bank_Accounts accountData = new Internal_Bank_Accounts();
        File_Access file = new File_Access();
        
        //calling of necessary methods
        login.checkInput(); //check credentials
        menu.Screen(); //show admin or user menu
        accountData.adminChoice();
        accountData.userChoice();
        
        
        
        
        
    }
    
}
