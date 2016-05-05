package choudhary96.sagar.dicegame;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int user_overallscore = 0;
    private int user_turnscore = 0;
    private int computer_overallscore = 0;
    private int computer_turnscore =0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void  rolldice(View view){
        Random rand = new Random();
        int  ch = rand.nextInt(6)+1;
        update_image(ch);
        TextView gamestatus =(TextView)findViewById(R.id.game_status);
        if(ch!=1){
            user_turnscore+=ch;
            gamestatus.setText("Your turn score : "+user_turnscore);
        }
        else{
            user_turnscore=0;
            gamestatus.setText("Your turn score : 0");
            computerturn();
        }
    }

    public void update_image(int ch) {
        ImageView die = (ImageView) findViewById(R.id.dice);
        switch (ch) {
            case 1:
                die.setImageResource(R.drawable.dice1);
                break;
            case 2:
                die.setImageResource(R.drawable.dice2);
                break;
            case 3:
                die.setImageResource(R.drawable.dice3);
                break;
            case 4:
                die.setImageResource(R.drawable.dice4);
                break;
            case 5:
                die.setImageResource(R.drawable.dice5);
                break;
            default:
                die.setImageResource(R.drawable.dice6);
                break;

        }
    }

    public void reset_button(View v){
            user_overallscore = 0;
            user_turnscore = 0;
            computer_overallscore = 0;
            computer_turnscore = 0;
            TextView gamestatus =(TextView)findViewById(R.id.game_status);
            TextView score_card =(TextView)findViewById(R.id.score_card);
            score_card.setText(R.string.score);
            gamestatus.setText(R.string.default_message);
            Button b1 =(Button)findViewById(R.id.rolldice);
            b1.setEnabled(true);
            Button b2 =(Button)findViewById(R.id.holddice);
            b2.setEnabled(true);
    }

    public void hold_button(View v){
            user_overallscore+=user_turnscore;
            user_turnscore =0;
            TextView gamestatus =(TextView)findViewById(R.id.game_status);
            TextView score_card =(TextView)findViewById(R.id.score_card);
            score_card.setText("Your score : " + user_overallscore + " Computer score : " + computer_overallscore);
            gamestatus.setText("Your turn score : "+ user_turnscore);
            check_winner();
            if (gamestatus.getText()!="Congrats, You won!!")
                computerturn();
    }
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            TextView gamestatus =(TextView)findViewById(R.id.game_status);
            TextView score_card =(TextView)findViewById(R.id.score_card);
            Button b1 =(Button)findViewById(R.id.rolldice);
            Button b2 =(Button)findViewById(R.id.holddice);
            Random rand = new Random();

            int  ch = rand.nextInt(6)+1;
            update_image(ch);
            if(ch!=1){
                if(computer_turnscore <15) {
                    computer_turnscore += ch;
                    gamestatus.setText("Computer turn score : " + computer_turnscore);
                    handler.postDelayed(runnable, 1000);
                }
                else{
                    computer_overallscore+=computer_turnscore;
                    update_status();
                    handler.removeCallbacks(runnable);
                }
            }
            else{
                update_status();
                handler.removeCallbacks(runnable);
            }

        }
    };

    public void computerturn() {
        Button b1 =(Button)findViewById(R.id.rolldice);
        b1.setEnabled(false);
        Button b2 =(Button)findViewById(R.id.holddice);
        b2.setEnabled(false);
        handler.postDelayed(runnable,1000);

    }

    public void update_status(){
        computer_turnscore=0;
        TextView gamestatus =(TextView)findViewById(R.id.game_status);
        TextView score_card =(TextView)findViewById(R.id.score_card);
        Button b1 =(Button)findViewById(R.id.rolldice);
        Button b2 =(Button)findViewById(R.id.holddice);
        score_card.setText("Your score : " + user_overallscore + " Computer score : " + computer_overallscore);
        gamestatus.setText("It's your Turn....  ");
        b1.setEnabled(true);
        b2.setEnabled(true);
        check_winner();
    }

    public void check_winner(){
        TextView gamestatus =(TextView)findViewById(R.id.game_status);
        if(computer_overallscore>=100) {
            gamestatus.setText("Computer Wins!!!");
            Button b1 =(Button)findViewById(R.id.rolldice);
            b1.setEnabled(false);
            Button b2 =(Button)findViewById(R.id.holddice);
            b2.setEnabled(false);
        }
        else {
            if (user_overallscore >= 100) {
                gamestatus.setText("Congrats, You won!!");
                Button b1 =(Button)findViewById(R.id.rolldice);
                b1.setEnabled(false);
                Button b2 =(Button)findViewById(R.id.holddice);
                b2.setEnabled(false);
            }
        }
    }
}


