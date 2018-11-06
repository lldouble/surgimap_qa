package config;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/*
Define the configuration of the test cases to run
 */
public class TestConfig {
    private Properties locators;

    public TestConfig() {
        try {
            locators = new Properties();
            InputStream input = new FileInputStream("./src/resources/locators.properties");

            // load the properties file
            locators.load(input);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //get a property by its key
    public String getData(String propId){
        return locators.getProperty(propId);
    }
}
