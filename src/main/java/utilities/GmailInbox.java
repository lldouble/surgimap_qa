package utilities;

import config.TestConfig;
import org.jsoup.Jsoup;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Properties;
import java.util.Vector;


public class GmailInbox {
    protected TestConfig config;

    public String email;
    public String password;
    public String emailServerHost;


    public GmailInbox(TestConfig c) {
        config = c;
        email = config.getData("email.account");
        password = config.getData("email.password");
        emailServerHost = config.getData("email.server.host");
    }

    public GmailInbox(TestConfig c, String email, String password, String emailServerHost) {
        this.config = c;
        this.email = email;
        this.password = password;
        this.emailServerHost = emailServerHost;
    }

    public GmailInbox(String email, String password, String emailServerHost) {
        this.email = email;
        this.password = password;
        this.emailServerHost = emailServerHost;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getEeailServerHost() {
        return emailServerHost;
    }


    public static Properties loadProps(String resourcePath) {
        Properties props = new Properties();
        return props;

    }

    /*
    Extract the email body, given an input message, as String;
     */
    public static String extractEmailContent(Message message) throws Exception {
        String contentType = message.getContentType();
        String messageContent = "";
        if (contentType.contains("multipart")) {
            Multipart multiPart = (Multipart) message.getContent();
            int numberOfParts = multiPart.getCount();
            for (int partCount = 0; partCount < numberOfParts; partCount++) {
                MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                messageContent = part.getContent().toString();
            }
        } else if (contentType.contains("text/plain")
            || contentType.contains("text/html")) {
            Object content = message.getContent();
            if (content != null) {
                messageContent = content.toString();
            }
        }

        return messageContent;
    }

    /*
    Extract the email body, given an input message, as HTML;
     */
    public static org.jsoup.nodes.Document extractEmailContentHtml(Message message) throws Exception {
        String messageContent = extractEmailContent(message);
        org.jsoup.nodes.Document doc = Jsoup.parse(messageContent);
        return doc;
    }



    /*
    get expected emails, filtered by sender, subject, date from
     */
    public Message[] getEmailsByFilters(Message[] messages, String sender, String subject, Calendar dateFrom) throws Exception {
        Vector filteredMessages = new Vector();

        //checks and build filtered messages list
        for (int i = 0; i < messages.length; i++) {
            Message message = messages[i];
            String senderAddressEmail = message.getFrom()[0].toString();
            String subjectEmail = message.getSubject();
            String dateFromEmail = message.getSentDate().toString();
            //     LOG.info("senderAddressEmail: " + senderAddressEmail);
            //     LOG.info("subjectEmail: " + subjectEmail);
            //     LOG.info("dateFromEmail: " + message.getSentDate());
            if (
                (senderAddressEmail.contains(sender))
                    && (subjectEmail.toLowerCase().contains(subject.toLowerCase()))
                    &&
                    message.getSentDate().after(dateFrom.getTime())
                ) {
                filteredMessages.add(message);
            }
        }

        //build filtered list
        Message[] results = new Message[filteredMessages.size()];
        for (int j = 0; j < filteredMessages.size(); j++) {
            results[j] = (Message) filteredMessages.get(j);
        }
        return results;
    }

    /*
    receive messages from inbox
     */
    public Message[] receiveMessages() {
        Properties props = new Properties();
        props.setProperty("mail.smtp.host","smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.port","465");
        props.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.auth","true");
        props.setProperty("mail.smtp.port","465");

        try {
            //  LOG.info("Properties found: " + props.size());
            Session session = Session.getDefaultInstance(props, null);

            Store store = session.getStore("imaps");
            store.connect(emailServerHost, email, password);

            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);
            int messageCount = inbox.getMessageCount();

            System.out.println("Total Messages: - " + messageCount);

            Message[] messages = inbox.getMessages();
            return messages;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }





}
