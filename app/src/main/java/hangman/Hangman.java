package ith.android.bignerdranch.com.hangman;

/**
 * Handles the game logic.
 */

public class Hangman {

    private String word;
    private boolean visible[];
    private String correctLetters;
    private String badLetters;
    private int guessesLeft;

    private WordGenerator wordGenerator = new WordGenerator();

    public Hangman(){
        correctLetters = "";
        badLetters = "";
        guessesLeft = 10;
    }

    public  Hangman(String word, String correctLetters, String badLetters){
        this.word = word;
        this.correctLetters = correctLetters;
        this.badLetters = badLetters;

        // calculate guesses left
        guessesLeft = 10 - badLetters.length();

        // create and set visible array
        visible = new boolean[word.length()];
        for (int i=0; i < word.length(); i++) {
            String letter = String.valueOf(word.charAt(i));
            if(correctLetters.contains(letter))
                visible[i] = true;
        }
    }
    /**
     * Choose a new word. Changes the current word, by randomly choosing a word from all available words
     */
    public void newWord(){
        word = wordGenerator.random();
        visible = new boolean[word.length()];
    }

    /**
     * Returns the current word, hiding all the letters the user hasn't guessed yet Example: Word is "NIKLAS".
     * User has guessed 'N' and 'A' previously. This then returns the string "N---A-"
     */
    public String getHiddenWord(){
        String hiddenWord = "";
        for (int i = 0; i < visible.length; i++) {
            if(visible[i])
                hiddenWord += word.charAt(i);
            else hiddenWord += "-";
        }
        return hiddenWord;
    }

    /**
     * Returns the current word, without any hidden letters. Example: "NIKLAS"
     */
    public String getRealWord(){
        return word;
    }

    /**
     * Makes a guess for a letter. If letter is not in word, we decrease the number of guesses left and remember the letter.
     * If letter is in word, we mark that letter as "complete" and remember the letter.
     *
     * @param guess - the letter the user has entered
     */
    public void guess(char guess){
        //letter is not in word
        if (!word.contains(String.valueOf(guess))) {
            guessesLeft--;
            if(!badLetters.contains(String.valueOf(guess)))
                badLetters += guess;
            return;
        }
        //letter is in word
        for (int i=0; i < word.length(); i++) {
            if (word.charAt(i) == guess){
                visible[i] = true;
                if(!correctLetters.contains(String.valueOf(guess)))
                    correctLetters += guess;
            }
        }
    }

    /**
     * Returns the number of guesses left. (If 0, the user has lost)
     */
    public int getGuessesLeft(){
        return guessesLeft;
    }

    /**
     * Checks to see if the supplied char has been guessed for already (erroneously or correctly)
     * @param c - the letter to check
     * @return true if letter has been used already, false if letter is free
     */
    public boolean isLetterUsed(char c){
        return (correctLetters+badLetters).contains(String.valueOf(c));
    }

    /**
     * Returns a String with all wrong guesses the user has made. Split by comma ", ".
     */
    public String getBadLettersUsed(){
        String splitBadLetters = "";
        for (int i=0; i<badLetters.length(); i++) {
            splitBadLetters += ", " + badLetters.charAt(i);
        }
        return splitBadLetters.replaceFirst(", ",""); //removes ", " in the beginning
    }

    /**
     * Checks to see if the user has guessed all letters correctly
     * @return - true if user has won (all letters correctly guessed), false if not done yet
     */
    public boolean hasWon(){
        for(int i=0; i < visible.length; i++) {
            if(!visible[i])
                return false;
        }
        return true;
    }

    /**
     * Checks to see if the user has used up all her guesses
     * @return true if user has lost (has no guesses left), false if she can still play
     */
    public boolean hasLost(){
        return guessesLeft <= 0;
    }

    //for saving game
    public String getBadLetters(){
        return badLetters;
    }
    public String getCorrectLetters(){
        return correctLetters;
    }


}
