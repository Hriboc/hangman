package ith.android.bignerdranch.com.hangman;

import android.os.StrictMode;

import java.net.URL;
import java.util.Scanner;

/**
 * Generates random english words.
 */
public class WordGenerator {

    public WordGenerator(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    /**
     * Gets a random english word from the site setgetgo.com.
     *
     * @return a random word. If it fails the word "HANGMAN" is returned.
     */
    public String random(){
        Scanner sc = null;
        String word = "HANGMAN";

        try {
            URL url = new URL("http://randomword.setgetgo.com/get.php");
            sc = new Scanner(url.openStream());
            word = sc.next().toUpperCase();
        }
        catch (Exception e) {}

        if(sc != null)
            sc.close();

        return word;
    }

}
