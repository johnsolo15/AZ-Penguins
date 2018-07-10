/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Activity;
import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.mail.AddressException;
import javax.mail.MessagingException;
import javax.mail.Properties;
/**
 *
 * @author syntel
 */
public class Sampler {
    
    static ArrayList<String> item_ids = new ArrayList<String>();
    
    public static TesterOrder currentOrder = new TesterOrder("sampl3_1d", "us3r_1d", 11.23f, 25.99f, 2400, 1134, "c4rd_1d",
        "instrcts", "d3l1v3r7_1d", "st0r3_1d", "st4t9s_1d", item_ids);
    
    public static void main(String[] args){
        
        createInvoice(currentOrder);
        
        /*
        // SMTP info
        String host = "smtp.gmail.com";
        String port = "587";
        String mailFrom = "luiszinzun@gmail.com";
        String password = "your-email-password";
        
        // message info
        String mailTo = "lakid@live.com";
        String subject = "Delivery Invoice/s";
        String message = "I have some attachments for you.";
        
        // attachments
        String[] attachFiles = new String[1];
        attachFiles[0] = "C:\\Users\\syntel.PHX440G3-2815Z5\\Desktop\\invoice.txt";
        
        try {
            sendInvoice(host, port, mailFrom, password, mailTo,
                subject, message, attachFiles);
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Could not send email.");
            ex.printStackTrace();
        }



	*/
        
    }
    
    public static void sendInvoice(String host, String port,
            final String userName, final String password, String toAddress,
            String subject, String message, String[] attachFiles)throws AddressException, MessagingException{
        
        
        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", userName);
        properties.put("mail.password", password);
        
        
        
        
    }
    
    
    public static void createInvoice(TesterOrder currentOrder){
        
        File file = new File ("C:\\Users\\syntel.PHX440G3-2815Z5\\Desktop\\invoice.txt");
        
        try{
            PrintWriter writer = new PrintWriter(file);
            writer.println(currentOrder.getCard_id());
            writer.println("Second linexx");
            writer.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        
        System.out.println("Success?");
        
    }
    
}
