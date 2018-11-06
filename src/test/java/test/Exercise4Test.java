package test;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Exercise4Test {

    /*
    Returns true if the input sentence (as a String) is palindrome, false otherwise
    A sentence or a word is palindrome if, once reverted, it equals the original word
    e.g. 'anna' is palindrome, 'Bob' is palidrome, 'mark' is not palindrome
    */
    public static boolean isPalindrome(String s) {
        //validate input
        if (s.equalsIgnoreCase(""))
            return true;

        //normalise input: to lowercase, remove spaces
        s = s.toLowerCase();
        s = s.replace(" ", "");

        //revert word
        String reverted = "";
        for (int i = s.length() - 1; i >= 0; i--)
            reverted = reverted + s.charAt(i);

        //check if the word is palindrom (it equals the reverted)
        return s.equalsIgnoreCase(reverted);


    }

    /*
    Test the palindrome sentence functionality with positive (palindrome) and negative (not palindrome) words and sentences.
    Covers also cases e.g. upper / lower cases content, empty string etc.
     */
    @Test
    public void testPalindromeWordsTest() {
        System.out.println("Exercise4 - Testing Palindrome Sentences Checker Function - Start");

        //1. validation: empty string;
        System.out.println("1. validation: empty string");
        String test = "";
        System.out.println("1. Is '" + test + "' a palindrome word? " + isPalindrome(test));
        assertThat(isPalindrome(test)).isTrue().withFailMessage("Error with palindrome sentence: " + test + " Expected true, actual false");
        System.out.println("1. validation: empty string; > PASSED");

        //2. nominal: positive, word with uppercase initial letter;
        System.out.println("2. nominal: positive, word with uppercase initial letter");
        test = "Anna";
        System.out.println("2. Is '" + test + "' a palindrome word? " + isPalindrome(test));
        assertThat(isPalindrome(test)).isTrue().withFailMessage("Error with palindrome sentence: " + test + " Expected true, actual false");
        System.out.println("2. nominal: positive, word with uppercase initial letter > PASSED");

        //3. nominal: positive, word with uppercase internal letter;
        System.out.println("3. nominal: positive, word with uppercase internal letter");
        test = "aNna";
        System.out.println("3. Is '" + test + "' a palindrome word? " + isPalindrome(test));
        assertThat(isPalindrome(test)).isTrue().withFailMessage("Error with palindrome sentence: " + test + " Expected true, actual false");
        System.out.println("3. nominal: positive, word with uppercase internal letter > PASSED");

        //4. nominal: positive;
        System.out.println("4. nominal: positive");
        test = "bob";
        System.out.println("4. Is '" + test + "' a palindrome word? " + isPalindrome(test));
        assertThat(isPalindrome(test)).isTrue().withFailMessage("Error with palindrome sentence: " + test + " Expected true, actual false");
        System.out.println("4. nominal: positive > PASSED");

        //5. nominal: negative (not a palindrome sentence)
        System.out.println("5. nominal: negative (not a palindrome sentence)");
        test = "charlie goes to hollywood";
        System.out.println("5. Is '" + test + "' a palindrome word? " + isPalindrome(test));
        assertThat(isPalindrome(test)).isFalse().withFailMessage("Error with palindrome sentence: " + test + " Expected false, actual true");
        System.out.println("5. nominal: negative (not a palindrome sentence) > PASSED");

        //6. nominal: posotive sentence
        System.out.println("6. nominal: posotive sentence");
        test = "anna anna anna";
        System.out.println("6. Is '" + test + "' a palindrome word? " + isPalindrome(test));
        assertThat(isPalindrome(test)).isTrue().withFailMessage("Error with palindrome sentence: " + test + " Expected true, actual false");
        System.out.println("6. nominal: posotive sentence > PASSED");

        //7. nominal: negative word
        System.out.println("7. nominal: negative word");
        test = "mark";
        System.out.println("7. Is '" + test + "' a palindrome word? " + isPalindrome(test));
        assertThat(isPalindrome(test)).isFalse().withFailMessage("Error with palindrome sentence: " + test + " Expected false, actual true");
        System.out.println("7. nominal: negative word > PASSED");

        //8. nominal: positive sentence
        System.out.println("8. nominal: positive sentence");
        test = "Race car";
        System.out.println("8. Is '" + test + "' a palindrome word? " + isPalindrome(test));
        assertThat(isPalindrome(test)).isTrue().withFailMessage("Error with palindrome sentence: " + test + " Expected true, actual false");
        System.out.println("8. nominal: positive sentence > PASSED");

        //9. nominal: positive sentence (real Latin palindrome sentence);
        System.out.println("9. nominal: positive sentence (real Latin palindrome sentence)");
        test = "in girum imus nocte ecce et consumimur igni";
        System.out.println("9. Is '" + test + "' a palindrome word? " + isPalindrome(test));
        assertThat(isPalindrome(test)).isTrue().withFailMessage("Error with palindrome sentence: " + test + " Expected true, actual false");
        System.out.println("9. nominal: positive sentence (real Latin palindrome sentence) > PASSED");

        //10. nominal: positive sentence uppercased (real Latin palindrome sentence);
        System.out.println("10. nominal: positive sentence uppercased (real Latin palindrome sentence)");
        test = "SI SEDES NON IS, SI NON SEDES IS";
        System.out.println("10. Is '" + test + "' a palindrome word? " + isPalindrome(test));
        assertThat(isPalindrome(test)).isTrue().withFailMessage("Error with palindrome sentence: " + test + " Expected true, actual false");
        System.out.println("10. nominal: positive sentence uppercased (real Latin palindrome sentence) > PASSED");

        System.out.println("Exercise4 - Testing Palindrome Sentences Checker Function - Done, Status: PASSED!");
    }
}

