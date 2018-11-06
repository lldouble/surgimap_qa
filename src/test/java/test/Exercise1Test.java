package test;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static org.assertj.core.api.Assertions.assertThat;

public class Exercise1Test {
    //define the name of the dictionary file;
    public final static String DICTIONARY_FILE_NAME = "dictionary.txt";

    /*
    Check if the file with the dictionary exists or not
     */
    public static boolean doesFileExist(String filePath) throws Exception{
        boolean fileFound = true;
        try {
            File f = new File(filePath + "/" + DICTIONARY_FILE_NAME);
            fileFound = f.exists();
            if (fileFound)
                System.out.println(">>> File was found!");
            else
                System.out.println(">>> File was not found!");
        }
        catch (Exception e){
            System.out.println("File not found!");
            return false;
        }
        return fileFound;
    }

    /*
    Read the dictionary content:
    w0 - def0, def1, ..., def<n>
    w1 - def0, def1, ..., def<m>
    ...
    w<x> - ...
    where w is a word, and def is a possible definition (meaning) for the word

    And print it as
    w0
    def0
    def1
    def<n>
    w1
    def0
    def1
    def<m>
    ..
    w<x>
     */
    public static String printDictionary() throws Exception {
        String wordDefSeparator = "-";
        String defsSeparator = ",";
        String result ="";
        File f = new File("./src/resources/" + DICTIONARY_FILE_NAME);
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        while ((line = br.readLine()) != null) {
          //  System.out.println("current line: " + line);
            String[] parts = line.split(wordDefSeparator);
            String word = parts[0].trim();
            String part1 = parts[1];
            String[] defs = part1.split(defsSeparator);
            result = result + word;
            result = result + "\n";
            for (int i = 0; i < defs.length; i++){
                    result = result + defs[i].trim();
                    result = result + "\n";
                }
            }
        return result;
    }

    /*
    Test cases to verify the doesFileExist() method;
     */
    @Test
    public void testDoesFileExistTest() throws Exception {
        System.out.println("Exercise1a - Testing Dictionary file existence - Start");

        //1. Positive test case: provided the right file path of the dictionary file in this project, expect true;
        System.out.println("1. Positive test case: provided the right file path of the dictionary file in this project, expect true");
        String path = "./src/resources/";
        assertThat(doesFileExist(path)).isTrue().withFailMessage("Expected to find the dictionary, it was not!");
        System.out.println("1. Positive test case: provided the right file path of the dictionary file in this project, expect true > PASSED");

        //2. Negative test case: provide a wrong file path of the dictionary file in this project, expect false;
        System.out.println("2. Negative test case: provide a wrong file path of the dictionary file in this project, expect false");
        path = "./src/a/wrong/path/";
        assertThat(doesFileExist(path)).isFalse().withFailMessage("Expected not to find the dictionary, it was!");
        System.out.println("2. Negative test case: provide a wrong file path of the dictionary file in this project, expect false > PASSED");

        System.out.println("Exercise1a - Testing Dictionary file existence - Done, Status: PASSED");
    }

    /*
    Test the print dictionary functionality
     */
    @Test
    public void testPrintDictionaryTest() throws Exception {
        System.out.println("Exercise1b - Testing Print Dictionary - Start");
        System.out.println("");

        String actualDictionary = printDictionary();
        System.out.println("1. Reading actual dictionary: ");
        System.out.println( actualDictionary);

        String expectedDictionary = "Apple\n" +
                "a fruit\n" +
                "a tech firm\n" +
                "Table\n" +
                "an object\n" +
                "contains rows and columns when used in context of computers\n" +
                "Orange\n" +
                "a fruit\n";
        System.out.println("2. Loading expected dictionary: ");
        System.out.println( expectedDictionary);
        System.out.println("");

        System.out.println("3. Comparing actual and expected dictionary to be the same");
        assertThat(actualDictionary.equals(expectedDictionary)).isTrue().withFailMessage("Actual dictionary different than the expected dictionary!");
        System.out.println("3. Comparing actual and expected dictionary to be the same > Done");

        System.out.println("Exercise1b - Testing Print Dictionary - Done, Status: PASSED");
    }
}
