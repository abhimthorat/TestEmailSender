import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailSender {
	
	 public static void main(String[] args) throws IOException {
	    	Properties prop = new Properties();
	    	FileInputStream fileInput = new FileInputStream("conf.properties");			
	        prop.load(fileInput);
	        String path = prop.getProperty("test_report");
	        String Report_HTML=prop.getProperty("test_report_email");
	        String reportHTMLName="emailable-report.html";
	    	String reportFileName="Command_Line.html";
	    
	        String content = "";
	        try {
	            BufferedReader in = new BufferedReader(new FileReader(path));
	            String str;
	            while ((str = in.readLine()) != null) {
	                content +=str;
	            }
	            in.close();
	        } catch (IOException e){}
//	        
	        
	        String[] to={"abhijeet@jombay.com"};
	        String[] cc={};
	        String[] bcc={"abhijeet@jombay.com"};

	        MailSender.sendMail("bugandreport@gmail.com",
	                            "jombay123",
	                            "smtp.gmail.com",
	                            "465",
	                            "true",
	                            "true",
	                             true,
	                            "javax.net.ssl.SSLSocketFactory",
	                            "false",
	                             to,
	                             cc,
	                             bcc,
	                            "[Automation]Test Execution Report",
	                            "The Tests are Executed.Please refer the execution report attached",
	                            path,
	                            reportFileName,
	                            content,
	                        	Report_HTML,
	                	    	reportHTMLName);
	      }

	      public  static boolean sendMail(String userName,
	                String passWord,
	                String host,
	                String port,
	                String starttls,
	                String auth,
	                boolean debug,
	                String socketFactoryClass,
	                String fallback,
	                String[] to,
	                String[] cc,
	                String[] bcc,
	                String subject,
	                String text,
	                String attachmentPath,
	                String attachmentName,
	                String content,
                	String Report_HTML,
        	    	String reportHTMLName
	                
	                  ){

	        //Object Instantiation of a properties file.
	        Properties props = new Properties();

	        props.put("mail.smtp.user", userName);

	        props.put("mail.smtp.host", host);

	        if(!"".equals(port)){
	        props.put("mail.smtp.port", port);
	        }

	        if(!"".equals(starttls)){
	            props.put("mail.smtp.starttls.enable",starttls);
	            props.put("mail.smtp.auth", auth);
	        }

	        if(debug){

	        props.put("mail.smtp.debug", "true");

	        }else{

	        props.put("mail.smtp.debug", "false");

	        }

	        if(!"".equals(port)){
	            props.put("mail.smtp.socketFactory.port", port);
	        }
	        if(!"".equals(socketFactoryClass)){
	            props.put("mail.smtp.socketFactory.class",socketFactoryClass);
	        }
	        if(!"".equals(fallback)){
	            props.put("mail.smtp.socketFactory.fallback", fallback);
	        }

	        try{

	            Session session = Session.getDefaultInstance(props, null);

	            session.setDebug(debug);

	            MimeMessage msg = new MimeMessage(session);

	            
	           
	            msg.setSubject(subject);
	            
//	            msg.setText(text);
//	            msg.setContent(content, "text/html");
	           
	            Multipart multipart = new MimeMultipart();
	            
	            BodyPart part1 = new MimeBodyPart();
	            part1.setContent(content, "text/html;charset=utf-8");
	            multipart.addBodyPart(part1);
	            
	            MimeBodyPart messageBodyPart = new MimeBodyPart();
	            DataSource source = new FileDataSource(attachmentPath);
	            messageBodyPart.setDataHandler(new DataHandler(source));
	            messageBodyPart.setFileName(attachmentName);
	            multipart.addBodyPart(messageBodyPart);
	            msg.setContent(multipart);
	           
	            
	            //2nd AttachMenttest_report1
	            MimeBodyPart messageBodyPart1 = new MimeBodyPart();
                DataSource source1 = new FileDataSource(Report_HTML);
                messageBodyPart1.setDataHandler(new DataHandler(source1));
                messageBodyPart1.setFileName(reportHTMLName);
	            multipart.addBodyPart(messageBodyPart1);
	            msg.setContent(multipart);
	           

	            msg.setFrom(new InternetAddress(userName));
	             
	            for(int i=0;i<to.length;i++){
	                msg.addRecipient(Message.RecipientType.TO, new
	InternetAddress(to[i]));
	            }

	            for(int i=0;i<cc.length;i++){
	                msg.addRecipient(Message.RecipientType.CC, new
	InternetAddress(cc[i]));
	            }

	            for(int i=0;i<bcc.length;i++){
	                msg.addRecipient(Message.RecipientType.BCC, new
	InternetAddress(bcc[i]));
	            }

	            msg.saveChanges();

	            Transport transport = session.getTransport("smtp");

	            transport.connect(host, userName, passWord);

	            transport.sendMessage(msg, msg.getAllRecipients());

	            transport.close();

	            return true;

	        } catch (Exception mex){
	            mex.printStackTrace();
	            return false;
	        }
	    }


	
}
