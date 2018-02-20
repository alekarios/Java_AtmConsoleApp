/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package afdemp_project_main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Alekarios
 */

public class File_Access extends Application_menu {
    
    public void writeToFile(String user,String Log_data) throws IOException {//writing transactions to log file
        
    Date now = new Date();
    SimpleDateFormat DateFormat = new SimpleDateFormat("dd.MM.yyy");
    File LogFile = new File(user+"_"+DateFormat.format(now)
            .substring(0, 2)+"_"+DateFormat.format(now).substring(3, 5)+
            "_"+DateFormat.format(now).substring(6, 10)+".txt");
    
    
    if(LogFile.exists() == false){//checks for existing file
        
        System.out.println("File not exists.Creating new file");
        LogFile.createNewFile();
        BufferedWriter Write = new BufferedWriter(new FileWriter(LogFile,true));
        Write.append(Log_data);
        Write.newLine();
        Write.close();
    }
    else{
        
        BufferedWriter Write = new BufferedWriter(new FileWriter(LogFile,true));
        Write.append(Log_data);
         Write.newLine();
        Write.close();
        
    }
    }   

    void writeToFile(String userLogIn, StringBuffer Buffer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
    
