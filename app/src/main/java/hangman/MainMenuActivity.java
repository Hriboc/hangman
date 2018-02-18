package ith.android.bignerdranch.com.hangman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
/**
 * The main menu of the game with two choices
 *  - Play game
 *  - About game
 */
public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Display action bar icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_logo);

        // Add items to the action bar.
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_play_game:
                playGame();
                return true;

            case R.id.toolbar_about_game:
                aboutGame();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Starts a new activity of Hangman
     *
     * @param view - the current view
     */
    public void onClickBtnPlayGame(View view){
        playGame();
    }

    public void onClickBtnResumeGame(View view) {
        // load saved game
        SharedPreferences sh = getSharedPreferences(HangmanActivity.FILE,MODE_PRIVATE);
        String word = sh.getString(HangmanActivity.CORRECT_WORD,null);
        String wrongGuesses = sh.getString(HangmanActivity.WRONG_GUESSES,null);
        String correctGuesses = sh.getString(HangmanActivity.CORRECT_GUESSES,null);

        // resume saved game
        Intent intent = new Intent(this, HangmanActivity.class);
        intent.putExtra(HangmanActivity.RESUME_GAME,true);
        intent.putExtra(HangmanActivity.CORRECT_WORD,word);
        intent.putExtra(HangmanActivity.WRONG_GUESSES,wrongGuesses);
        intent.putExtra(HangmanActivity.CORRECT_GUESSES,correctGuesses);
        startActivity(intent);
    }

    /**
     * Starts a new activity of About
     *
     * @param view - the current view
     */
    public void onClickBtnAbout(View view){
        aboutGame();
    }


    /* Private functions */

    private void playGame(){
        startActivity(new Intent(this, HangmanActivity.class));
    }

    private void aboutGame(){
        startActivity(new Intent(this, AboutActivity.class));
    }

}
