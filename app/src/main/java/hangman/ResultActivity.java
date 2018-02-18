package ith.android.bignerdranch.com.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Displays the result when the game is over
 */
public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String gameResult = intent.getStringExtra(HangmanActivity.GAME_WON_OR_LOST);
        String correctWord = intent.getStringExtra(HangmanActivity.CORRECT_WORD);
        String guessesLeft = intent.getStringExtra(HangmanActivity.GUESSES_LEFT);

        TextView tvGameResult = (TextView) findViewById(R.id.tv_game_result);
        TextView tvCorrectWord = (TextView) findViewById(R.id.tv_word);
        TextView tvGuessesLeft = (TextView) findViewById(R.id.tv_guesses_left);

        tvGameResult.setText(gameResult);
        tvCorrectWord.setText(correctWord);
        tvGuessesLeft.setText(guessesLeft);
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

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_play_game:
                startActivity(new Intent(this, HangmanActivity.class));
                return true;

            case R.id.toolbar_about_game:
                startActivity(new Intent(this, AboutActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Starts a new activity of Main Menu
     *
     * @param view - the current view
     */
    public void onClickBtnMainMenu(View view){
        startActivity(new Intent(this, MainMenuActivity.class));
    }
}
