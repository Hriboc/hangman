package ith.android.bignerdranch.com.hangman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The game play
 */
public class HangmanActivity extends AppCompatActivity {

    public static final String GAME_WON_OR_LOST = "0";
    public static final String CORRECT_WORD = "1";
    public static final String GUESSES_LEFT = "2";
    public static final String WRONG_GUESSES = "3";
    public static final String CORRECT_GUESSES = "4";
    public static final String RESUME_GAME = "5";
    public static final String FILE = "Hangman";

    private Hangman hangman;

    private TextView tvWord;
    private TextView tvGuessesLeft;
    private TextView tvLettersGuessed;
    private EditText etLetter;
    private ImageView ivHangman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);

        // check if the game should be resumed
        Intent intent = getIntent();
        boolean resumeGame = intent.getBooleanExtra(RESUME_GAME,false);
        if(resumeGame){
            String word = intent.getStringExtra(CORRECT_WORD);
            String wrongGuesses = intent.getStringExtra(WRONG_GUESSES);
            String correctGuesses = intent.getStringExtra(CORRECT_GUESSES);
            hangman = new Hangman(word,correctGuesses,wrongGuesses);
        }
        else{
            hangman = new Hangman();
            hangman.newWord();
        }

        //init views
        etLetter = (EditText) findViewById(R.id.et_letter);
        tvWord = (TextView) findViewById(R.id.tv_hangman_word);
        tvGuessesLeft = (TextView) findViewById(R.id.tv_hangman_guesses_left);
        tvLettersGuessed = (TextView) findViewById(R.id.tv_letters_guessed);
        ivHangman = (ImageView) findViewById(R.id.iv_hangman);

        refreshViews();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Display action bar icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_logo);

        // Display back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Add items to the action bar.
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);

        //Hide play game button
        menu.getItem(0).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_save_game:
                saveGame();
                return true;

            case R.id.toolbar_about_game:
                startActivity(new Intent(this, AboutActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Checks user input for correctness, makes a guess and checks if player has won or lost
     *
     * @param view - the current view
     */
    public void onClickBtnGuess(View view){
        String input = etLetter.getText().toString().toUpperCase();
        if(input.isEmpty())
            return;

        if(input.length() > 1){
            makeToast(R.string.toast_to_many_latters_entered);
            return;
        }

        char letter = input.charAt(0);
        if(hangman.isLetterUsed(letter)) {
            makeToast(R.string.toast_letter_already_guessed);
            return;
        }

        hangman.guess(letter);

        //set the content of the views
        refreshViews();

        //if won or lost and send result to ResultActivity
        if(hangman.hasWon() || hangman.hasLost()) {
            Intent intentResult = new Intent(this, ResultActivity.class);
            intentResult.putExtra(GAME_WON_OR_LOST, getGameWonOrLostText());
            intentResult.putExtra(CORRECT_WORD, makeCorrectWordText());
            intentResult.putExtra(GUESSES_LEFT, makeGuessesLeftForResultText());
            startActivity(intentResult);

            this.finish();
        }
    }


    /* Private help functions */

    private void setImage(int imgNum){
        int imgId = getResources().getIdentifier("hang"+ imgNum, "drawable", getPackageName());
        ivHangman.setImageResource(imgId);
    }

    private void makeToast(int messageId){
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
    }

    private String makeGuessesLeftText(){
        return hangman.getGuessesLeft()+ " " +getResources().getString(R.string.tv_hangman_guesses_left);
    }

    private String makeGuessesLeftForResultText(){
        return getResources().getString(R.string.tv_result_guesses_left) + " " + hangman.getGuessesLeft();
    }

    private String makeCorrectWordText(){
        return getResources().getString(R.string.tv_result_word) + " " + hangman.getRealWord();
    }

    private String getGameWonOrLostText(){
        return hangman.hasWon() ? getResources().getString(R.string.won) : getResources().getString(R.string.lost);
    }

    private void refreshViews() {
        setImage(hangman.getGuessesLeft());
        tvWord.setText(hangman.getHiddenWord());
        tvGuessesLeft.setText(makeGuessesLeftText());
        tvLettersGuessed.setText(hangman.getBadLettersUsed());
        etLetter.setText("");
    }

    private void saveGame() {
        SharedPreferences.Editor editor = getSharedPreferences(FILE,MODE_PRIVATE).edit();
        editor.putString(CORRECT_WORD, hangman.getRealWord());
        editor.putString(CORRECT_GUESSES, hangman.getCorrectLetters());
        editor.putString(WRONG_GUESSES, hangman.getBadLetters());
        editor.apply();
    }
}
