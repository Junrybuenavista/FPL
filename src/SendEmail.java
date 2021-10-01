import java.io.FileNotFoundException;
import java.util.Properties;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail {
	
public SendEmail() throws Exception
{
	 final String username = "buenavistajunry@gmail.com";
	    final String password = "xidwgumwwowibzwt";

	    Properties props = new Properties();
	    props.put("mail.smtp.ssl.protocols", "TLSv1.2");
	    props.put("mail.smtp.auth", true);
	    props.put("mail.smtp.starttls.enable", true);
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.port", "587");

	    Session session = Session.getInstance(props,
	            new javax.mail.Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(username, password);
	                }
	            });
	    while(true) {
	    try {
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress("buenavistajunry@gmail.com"));
	        message.setRecipients(Message.RecipientType.TO,
	                InternetAddress.parse("junrybuenavista@yahoo.com"));
	        message.setSubject("Testing Subject");
	        message.setText("PFA");

	        MimeBodyPart messageBodyPart = new MimeBodyPart();

	        Multipart multipart = new MimeMultipart();
	        
	        String file = "C:\\Users\\Jhunta\\Downloads\\Document.pdf";
	        String fileName = "Document-accout123.pdf";
	        DataSource source = new FileDataSource(file);
	        messageBodyPart.setDataHandler(new DataHandler(source));
	        messageBodyPart.setFileName(fileName);
	        multipart.addBodyPart(messageBodyPart);

	        message.setContent(multipart);

	        System.out.println("Sending PDF to email:");
	        
	        Transport.send(message);
	        
	        System.out.println("PDF sent!");
	        break;
		    } 
		    catch (MessagingException e) {
		        e.printStackTrace();
		        System.out.println("Sending Mail waiting:");
		        Thread.sleep(1000);
		    }
	    }
	}
	
public static void main(String[] args) {

   
  }
}
