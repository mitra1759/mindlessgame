package slotmachine.johnnywaity.com.slotmachine;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import gameplay.GamePlay;
import gameplay.SlotIcons;

public class GameActivity extends Activity implements OnGestureListener {

    GestureDetector gestureDetector;

    private boolean isSpinning = false;

//test comment
    private boolean willRotate = true;
    private ArrayList<Integer> imgArray = new ArrayList<>();
    private int[][] dataArray = new int[3][3];
    private boolean killFirstCol = false;
    private boolean killSecondCol = false;
    private boolean killThirdCol = false;
    private boolean slowDownFC = false;
    private boolean slowDownSC = false;
    private boolean slowDownTC = false;
    private int imgTime = 100;
    private GamePlay g = new GamePlay();



    private void killFC(){
        killFirstCol = true;
    }

    private void killSC(){
        killSecondCol = true;
    }

    private void killTC(){
        killThirdCol = true;
    }

    private void scrollImages(final SlotIcons res1, final SlotIcons res2, final SlotIcons res3) {

        final ImageView lever = (ImageView) findViewById(R.id.lever);
        final ImageView topslot1 = (ImageView) findViewById(R.id.topslot1);
        final ImageView topslot2 = (ImageView) findViewById(R.id.topslot2);
        final ImageView topslot3 = (ImageView) findViewById(R.id.topslot3);
        final ImageView midslot1 = (ImageView) findViewById(R.id.midslot1);
        final ImageView midslot2 = (ImageView) findViewById(R.id.midslot2);
        final ImageView midslot3 = (ImageView) findViewById(R.id.midslot3);
        final ImageView botslot1 = (ImageView) findViewById(R.id.botslot1);
        final ImageView botslot2 = (ImageView) findViewById(R.id.botslot2);
        final ImageView botslot3 = (ImageView) findViewById(R.id.botslot3);

        final float initX = topslot1.getX();
        final float initY = topslot1.getY();


        topslot1.post(new Runnable() {
            int i =2;
            @Override
            public void run() {
                topslot1.setImageResource(imgArray.get(i));
                i++;
                if (i>5){
                    i=0;
                }
                if (!killFirstCol){
                    topslot1.postDelayed(this,imgTime);
                }


            }
        });

        topslot2.post(new Runnable() {
            int i =2;
            @Override
            public void run() {
                topslot2.setImageResource(imgArray.get(i));
                i++;
                if (i>5){
                    i=0;
                }
                if (!killSecondCol){
                    topslot2.postDelayed(this,imgTime);
                }


            }
        });

        topslot3.post(new Runnable() {
            int i =2;
            @Override
            public void run() {
                topslot3.setImageResource(imgArray.get(i));
                i++;
                if (i>5){
                    i=0;
                }
                if (!killThirdCol){
                    topslot3.postDelayed(this,imgTime);
                }


            }
        });

        midslot1.post(new Runnable() {
            int i =1;
            @Override
            public void run() {
                midslot1.setImageResource(imgArray.get(i));
                i++;
                if (i>5){
                    i=0;
                }
                if(!killFirstCol){
                    midslot1.postDelayed(this,imgTime);
                }
                if (slowDownFC){
                    imgTime = 500;
                    if (i==res1.iconToNum(res1)){
                        killFC();
                    }
                }

            }
        });

        midslot2.post(new Runnable() {
            int i =1;
            @Override
            public void run() {
                midslot2.setImageResource(imgArray.get(i));
                i++;
                if (i>5){
                    i=0;
                }
                if(!killSecondCol){
                    midslot2.postDelayed(this,imgTime);
                }
                if (slowDownSC){
                    imgTime = 500;
                    if (i==res2.iconToNum(res2)){
                        killSC();
                    }
                }

            }
        });

        midslot3.post(new Runnable() {
            int i =1;
            @Override
            public void run() {
                midslot3.setImageResource(imgArray.get(i));
                i++;
                if (i>5){
                    i=0;
                }
                if(!killThirdCol){
                    midslot3.postDelayed(this,imgTime);
                }
                if (slowDownTC){
                    imgTime = 500;
                    if (i==res3.iconToNum(res3)){
                        killTC();
                        TextView score = findViewById(R.id.myScore);
                        score.setText("My Score: " + g.getLastPayout());
                        isSpinning = false;
                    }
                }

            }
        });

        botslot1.post(new Runnable() {
            int i =0;
            @Override
            public void run() {
                botslot1.setImageResource(imgArray.get(i));
                i++;
                if (i>5){
                    i=0;
                }
                if (!killFirstCol){
                    botslot1.postDelayed(this,imgTime);
                }

            }
        });

        botslot2.post(new Runnable() {
            int i =0;
            @Override
            public void run() {
                botslot2.setImageResource(imgArray.get(i));
                i++;
                if (i>5){
                    i=0;
                }
                if (!killSecondCol){
                    botslot2.postDelayed(this,imgTime);
                }

            }
        });

        botslot3.post(new Runnable() {
            int i =0;
            @Override
            public void run() {
                botslot3.setImageResource(imgArray.get(i));
                i++;
                if (i>5){
                    i=0;
                }
                if (!killThirdCol){
                    botslot3.postDelayed(this,imgTime);
                }

            }
        });

        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                slowDownFC = true;
            }
        }.start();

        new CountDownTimer(7000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                slowDownSC = true;
            }
        }.start();

        new CountDownTimer(9000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                slowDownTC = true;
            }
        }.start();



    }

    private void reset(){
        killFirstCol = false;
        killSecondCol = false;
        killThirdCol = false;
        slowDownFC = false;
        slowDownSC = false;
        slowDownTC = false;
        imgTime = 100;

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if(g.isMultiplayer()){
            findViewById(R.id.opponentBox).setVisibility(View.VISIBLE);
            findViewById(R.id.playerRollCount).setVisibility(View.VISIBLE);

        }
        g.setActivity(this);

        imgArray.add(R.drawable.img1);
        imgArray.add(R.drawable.img2);
        imgArray.add(R.drawable.img3);
        imgArray.add(R.drawable.img4);
        imgArray.add(R.drawable.img5);
        imgArray.add(R.drawable.img6);

        final ImageView lever = (ImageView) findViewById(R.id.lever);
        final ImageView topslot1 = (ImageView) findViewById(R.id.topslot1);
        final ImageView topslot2 = (ImageView) findViewById(R.id.topslot2);
        final ImageView topslot3 = (ImageView) findViewById(R.id.topslot3);
        final ImageView midslot1 = (ImageView) findViewById(R.id.midslot1);
        final ImageView midslot2 = (ImageView) findViewById(R.id.midslot2);
        final ImageView midslot3 = (ImageView) findViewById(R.id.midslot3);
        final ImageView botslot1 = (ImageView) findViewById(R.id.botslot1);
        final ImageView botslot2 = (ImageView) findViewById(R.id.botslot2);
        final ImageView botslot3 = (ImageView) findViewById(R.id.botslot3);





        dataArray[0][0] = 2;
        dataArray[0][1] = 2;
        dataArray[0][2] = 2;
        dataArray[1][0] = 1;
        dataArray[1][1] = 1;
        dataArray[1][2] = 1;
        dataArray[2][0] = 0;
        dataArray[2][1] = 0;
        dataArray[2][2] = 0;

//        for (int i=0;i<dataArray.length;i++){
//            for (int j=0;j<dataArray[i].length;i++){
//                System.out.println();
//            }
//        }


        for(int i = 0; i<3; i++)
        {
            for(int j = 0; j<3; j++)
            {
                System.out.print(dataArray[i][j]);
            }
            System.out.println();
        }


//        topslot1.setImageDrawable(imgArray.get(0));



        gestureDetector = new GestureDetector(GameActivity.this, GameActivity.this);

    }

    @Override
    public boolean onFling(MotionEvent motionEvent1, MotionEvent motionEvent2, float X, float Y) {
        if(g.getRollCount() >= 3 && g.isMultiplayer()){
            Toast.makeText(getBaseContext(), "Ran Out Of Rolls", Toast.LENGTH_LONG).show();
            return true;
        }


        final ImageView lever = (ImageView) findViewById(R.id.lever);
        System.out.println(lever);
        if (motionEvent2.getY() - motionEvent1.getY() > 50) {
//            Toast.makeText(getBaseContext(), "Fling1", Toast.LENGTH_LONG).show();
        if (motionEvent1.getY() >= lever.getY() && motionEvent1.getY() <= lever.getY() + lever.getHeight() && motionEvent1.getX() >= lever.getX() && motionEvent2.getX() <= lever.getX() + lever.getWidth() || true){
            if(isSpinning){
                return true;
            }

            isSpinning = true;



//            Toast.makeText(getBaseContext(), "Fling", Toast.LENGTH_LONG).show();
                new CountDownTimer(500, 1000) {

                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                        reset();
                        ArrayList<SlotIcons> res = g.roll();
                        scrollImages(res.get(0),res.get(1),res.get(2));

                    }
                }.start();

                //Rotation animation
                //paramters: from degrees, to degrees, x pivot relative to either original picture position or picture at the moment,
                //  pivot x value (float), y pivot relative to either original picture position or picture at the moment, pivot y value
                //setFill makes sure that there is no flickering when running two animations one immediately after the other on same picture
                //setInterpolator creates a physics based animation with follwing pattern, slow-progressively faster-progressively slower
                Animation rotate = new RotateAnimation(0.0f, 105.0f,
                        Animation.RELATIVE_TO_SELF, .0f, Animation.RELATIVE_TO_PARENT,
                        .15f);
                rotate.setRepeatCount(0);
                rotate.setDuration(500);
                rotate.getFillBefore();
                rotate.setFillAfter(true);
                rotate.setInterpolator(new AccelerateDecelerateInterpolator());
                lever.startAnimation(rotate);

                new CountDownTimer(500, 1000) {

                    public void onTick(long millisUntilFinished) {
//                        text.setText("seconds remaining: " + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
//                        text.setText("done!");

                        Animation rotate = new RotateAnimation(105.0f, 0.0f,
                                Animation.RELATIVE_TO_SELF, .0f, Animation.RELATIVE_TO_PARENT,
                                .15f);
                        rotate.setRepeatCount(0);
                        rotate.setDuration(500);

                        rotate.setFillAfter(true);
                        rotate.setInterpolator(new AccelerateDecelerateInterpolator());
                        lever.startAnimation(rotate);
                        willRotate = false;

                    }
                }.start();


                //
            }



            return true;
        }

        return true;
    }

    public void updatePlayerRolls(String txt){
        final String txt2 = txt;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView v = findViewById(R.id.playerRollCount);
                v.setText(txt2);
            }
        });

    }
    public void updateOpponentRolls(String txt){
        final String txt2 = txt;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView v = findViewById(R.id.opponentRollCount);
                v.setText(txt2);
            }
        });

    }
    public void updateOpponentScore(String txt){
        final String txt2 = txt;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView v = findViewById(R.id.opponentScore);
                v.setText(txt2);
            }
        });

    }



    @Override
    public void onLongPress(MotionEvent arg0) {

    }

    @Override
    public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {

        return false;
    }

    @Override
    public void onShowPress(MotionEvent arg0) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent arg0) {

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        return gestureDetector.onTouchEvent(motionEvent);
    }

    @Override
    public boolean onDown(MotionEvent arg0) {
        return false;
    }

}
