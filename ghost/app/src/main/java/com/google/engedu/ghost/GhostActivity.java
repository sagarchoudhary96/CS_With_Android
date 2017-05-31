/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.Random;

import static com.google.engedu.ghost.R.id.gameStatus;
import static com.google.engedu.ghost.R.id.ghostText;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();
    private String word = "";
    private TextView text;
    private TextView label;
    private Button challengeBtn, restartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
        text = (TextView) findViewById(ghostText);
        label = (TextView) findViewById(gameStatus);
        challengeBtn = (Button) findViewById(R.id.buttonChallenge);
        restartBtn = (Button) findViewById(R.id.buttonRestart);
        try {
            dictionary = new SimpleDictionary(assetManager.open("words.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        challengeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (word.length()>4 && dictionary.isWord(word)) {
                    label.setText("User Wins");
                }

                else if (dictionary.getAnyWordStartingWith(word)!= null) {
                    String new_word = dictionary.getAnyWordStartingWith(word);
                    label.setText("Computer Wins");
                    text.setText(new_word);
                }
                else {
                    label.setText("User Wins");
                }
            }
        });

        restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word = "";
                onStart(null);
            }
        });

        onStart(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
        text.setText("");
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }

    private void computerTurn() {
        // Do computer turn stuff then make it the user's turn again
        if (word.length() >= 4 && dictionary.isWord(word)) {
            label.setText("User Victory");
            return;
        }
        String newWord = dictionary.getAnyWordStartingWith(word);
        if (newWord == null) {
            label.setText("Computer won!");
            return;
        }
        else {
            word = newWord.substring(0, word.length() + 1);
            text.setText(word);
        }
        userTurn = true;
        label.setText(USER_TURN);
    }

    /**
     * Handler for user key presses.
     * @param keyCode
     * @param event
     * @return whether the key stroke was handled.
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (userTurn) {
            userTurn = false;
            if (keyCode>=29 && keyCode<=54){
                word += (char)event.getUnicodeChar();
                Log.v("w:", word);
                if (dictionary.isWord(word)) {
                    text.setText(word);
                }
            }
            computerTurn();
        }
        return super.onKeyUp(keyCode, event);
    }
}
