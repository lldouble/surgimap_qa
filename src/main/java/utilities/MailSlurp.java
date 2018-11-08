package utilities;

import config.TestConfig;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;




public class MailSlurp {


    private String user;
    private String apiKey;
    private String restBaseURL;
    private TestConfig config;

    public MailSlurp(String user, String apiKey, TestConfig config) {
        this.apiKey = apiKey;
        this.user = user;
        this.config = config;
        this.restBaseURL = restBaseURL;
    }

    public MailSlurp(TestConfig config) {
        this.config = config;
        this.apiKey = config.getData("mailslurp.api.key");
        this.user = config.getData("mailslurp.user");
        this.restBaseURL = config.getData("mailslurp.api.base.url");
        ;
    }

    public String getRestBaseURL() {
        return restBaseURL;
    }

    public void setRestBaseURL(String restBaseURL) {
        this.restBaseURL = restBaseURL;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /*
    get the email address of the generated mailbox
     */
    public String getMailboxEmailAddress(JSONObject jsonResponse) throws Exception {
        return jsonResponse.getJSONObject("payload").get("address").toString();
    }

    /*
    get the id address of the generated mailbox
    */
    public String getMailboxID(JSONObject jsonResponse) throws Exception {
        return jsonResponse.getJSONObject("payload").get("id").toString();
    }

    /*
    generate an unique email inbox
    */
    public JSONObject generateEmailbox() throws Exception {
        String apiKey = this.getApiKey();
        String user = this.getUser();
        String restBaseURL = this.getRestBaseURL();


        //$ curl -X POST https://api.mailslurp.com/inboxes?apiKey=test
        String query = "inboxes?apiKey=" + apiKey;
        URL url = new URL("https://" + restBaseURL + "/" + query);
        System.out.println("MailSlurp - generateEmailBox REST API url: " + url.toString());


        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(1500);
        connection.setReadTimeout(1500);
        connection.setDoOutput(true);

        OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
        //osw.write(query);
        osw.flush();
        osw.close();
        int responseCode = connection.getResponseCode();
        System.out.println("MailSlurp - generateEmailBox REST API Response code: " + responseCode);
        if (responseCode == 200) {
            System.out.println("MailSlurp - Update successfully done (RC=200)! returning true");
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            String response = sb.toString();
            //conver the response (JSON) into a Json object, to extract data;
            JSONObject jsonResponse = new JSONObject(response);

            //validate the response to contain the email address
            String emailAddr = jsonResponse.getJSONObject("payload").get("address").toString();
            if (!emailAddr.contains("@"))
                throw new Exception("(ERROR): The generated email address seems not correct: " + emailAddr);

            return jsonResponse;
        } else throw new Exception("(ERROR): Issue in generating a new mail box");
    }

    /*
   Get the first on the input email address (given its JSONObject for the creation), the (first) message content matching the filter (subject, content, sender, sent date);
    */
    public String getMessage(JSONObject emailInfo, String subject, String content, String senderEmail, Calendar date) throws Exception {
        String id = this.getMailboxID(emailInfo);
        String email = this.getMailboxEmailAddress(emailInfo);

        String apiKey = this.getApiKey();
        String user = this.getUser();
        String restBaseURL = this.getRestBaseURL();


        //$ curl -X POST https://api.mailslurp.com/inboxes?apiKey=test
        String query = "inboxes/" + id + "?apiKey=" + apiKey;
        URL url = new URL("https://" + restBaseURL + query);
        System.out.println("MailSlurp - REST API (getMessage) url: " + url.toString());

        BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream()));
        String line = "";
        String result = "";
        while ((line = bf.readLine()) != null) {
            result = result + "\n" + line;
        }
        bf.close();

        if (result != null && !result.equals("")) {

            JSONObject jsonResponse = new JSONObject(result);

            JSONArray messages = jsonResponse.getJSONArray("payload");
            for (int i = 0; i < messages.length(); i++) {
                JSONObject message = messages.getJSONObject(i);
                String emailFrom = message.getString("from");
                String emailSubject = message.getString("subject");
                String emailBody = message.getString("body");
                String receivedDateString = message.getString("received");

                if (
                    //    senderEmail.toLowerCase().trim().equalsIgnoreCase(emailFrom.trim())
                      //          &&
                                emailSubject.toLowerCase().contains(subject.toLowerCase())
                                &&
                                emailBody.toLowerCase().contains(content.toLowerCase())
                    //TODO add date part!
                        ) {
                    //Found email
                    return emailBody;
                }

            }
            return "";

        } else throw new Exception("Issue in generating a new mail box");

    }

    /*
    Check if an email box contains an email, given the subject, body content, sender, and timestamp
     */
    public boolean containsMessage(JSONObject emailInfo, String subject, String content, String senderEmail, Calendar date) throws Exception {
        return (!getMessage(emailInfo, subject, content, senderEmail, date).equals(""));
    }


}
