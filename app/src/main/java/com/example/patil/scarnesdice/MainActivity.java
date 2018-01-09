package com.example.patil.scarnesdice;

import android.content.Context;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public int u_score;
    public int u_turn;
    public int comp_score;
    public int comp_turn;
    public TextView status;
    public ImageView dice;
    public Button rollButton;
    public Button holdButton;
    public Button resetButton;
    public TextView label;
    public Random random;
    Handler handler;
    Thread thread;
    public int num=0;

    public Integer[] imageId={R.drawable.dice1,R.drawable.dice2,R.drawable.dice3,R.drawable.dice4,R.drawable.dice5,R.drawable.dice6,R.drawable.winner};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        random=new Random();
        u_score=0;
        u_turn=0;
        comp_score=0;
        comp_turn=0;
        label=findViewById(R.id.label);
        status=findViewById(R.id.status);
        dice=findViewById(R.id.dice);
        rollButton=findViewById(R.id.rollButton);
        resetButton=findViewById(R.id.resetButton);
        holdButton=findViewById(R.id.holdButton);

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (u_score<100&&comp_score<100)
                rollDice(imageId);
            }
        });


        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (u_score<100&&comp_score<100){
                    u_score=u_score+u_turn;
                    comp_score=comp_score+comp_turn;
                }
                u_turn=0;
                comp_turn=0;
                status.setText("Your Score: "+u_score+"\tComputer Score: "+comp_score+"\n"+"Your Turn Score: "+u_turn+"  Computer Turn Score: "+comp_turn);
                if (comp_score<100&&u_score>=100){
                    label.setText("You Win");
                    dice.setImageResource(imageId[6]);
                }
                if (comp_score<100&&u_score<100)
                helperMethod();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                u_score=0;
                u_turn=0;
                comp_score=0;
                comp_turn=0;
                dice.setImageResource(imageId[2]);
                status.setText("Your Score: "+u_score+"\tComputer Score: "+comp_score+"\n"+"Your Turn Score: "+u_turn+"  Computer Turn Score: "+comp_turn);
                label.setText("Your Turn");
            }
        });
    }


    public void helperMethod(){
        label.setText("Computer Turn");
        holdButton.setEnabled(false);
        resetButton.setEnabled(false);

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            Random random=new Random();
            int i=0;
            public void run() {

                i=random.nextInt(6)+1;
                dice.setImageResource(imageId[i-1]);

                if(i==1){
                    comp_turn=0;
                    status.setText("Your Score: "+u_score+"\tComputer Score: "+comp_score+"\n"+"Your Turn Score: "+u_turn+"  Computer Turn Score: "+comp_turn);
                    label.setText("Your Turn");
                    handler.sendEmptyMessage(0);
                    holdButton.setEnabled(true);
                    resetButton.setEnabled(true);
                }
                else{
                    comp_turn=comp_turn+i;
                    if (comp_turn>=20||comp_score>=20) {
                        if (comp_score<100)
                        comp_score = comp_score + comp_turn;
                        comp_turn=0;
                        label.setText("Your Turn");
                        status.setText("Your Score: "+u_score+"\tComputer Score: "+comp_score+"\n"+"Your Turn Score: "+u_turn+"  Computer Turn Score: "+comp_turn);
                        holdButton.setEnabled(true);
                        resetButton.setEnabled(true);
                        handler.sendEmptyMessage(0);
                    }
                    else {
                        status.setText("Your Score: " + u_score + "  Computer Score: " + comp_score + "\n" + "Your Turn Score: " + u_turn + " Computer Turn Score: " + comp_turn);
                        label.setText("Computer Turn");
                        handler.postDelayed(this, 1000);
                    }
                }
                if (comp_score>=100&&u_score<100){
                    label.setText("Computer Win");
                    dice.setImageResource(imageId[6]);
                    handler.sendEmptyMessage(0);
                    holdButton.setEnabled(true);
                    resetButton.setEnabled(true);
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    public void rollDice(Integer[] imageId){
        num=random.nextInt(6)+1;
        dice.setImageResource(imageId[num-1]);
        if(num==1){
            u_turn=0;
            status.setText("Your Score: "+u_score+"\tComputer Score: "+comp_score+"\n"+"Your Turn Score: "+u_turn+"  Computer Turn Score: "+comp_turn);
            label.setText("Computer Turn");
            helperMethod();
        }
        else{
            u_turn=u_turn+num;
            status.setText("Your Score: "+u_score+"\tComputer Score: "+comp_score+"\n"+"Your Turn Score: "+u_turn+"  Computer Turn Score: "+comp_turn);
            label.setText("Your Turn");
        }
        if (comp_score<100&&u_score>=100){
            label.setText("You Win");
            dice.setImageResource(imageId[6]);
        }
    }
}


