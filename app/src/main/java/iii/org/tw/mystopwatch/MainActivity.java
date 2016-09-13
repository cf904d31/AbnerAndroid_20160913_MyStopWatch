package iii.org.tw.mystopwatch;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Button btnLeft , btnRight;
    private TextView showTime;
    private boolean isRunning;
    private Timer timer;
    private MyTask myTask;
    private MyHandler myHandler;
    private int counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLeft = (Button) findViewById(R.id.btnLeft);
        btnRight = (Button) findViewById(R.id.btnRight);
        showTime = (TextView) findViewById(R.id.showTime);

        timer = new Timer();
        myHandler = new MyHandler();


    }

    //-----為了在跳出此程式後可以乾淨的清除Timer
    //-----所以在finish方法中添加關閉Timer的指令
    @Override
    public void finish() {
        timer.purge();  //-----將Timer清空
        timer.cancel(); //-----將Timer取消
        timer = null;   //-----將Timer指向null
        super.finish();
    }

    public void doRight(View v) {
        isRunning = !isRunning;
        btnRight.setText(isRunning?"Stop":"Start");
        btnLeft.setText(isRunning?"Lap":"Reset");
        if (isRunning) {
            doStart();
        } else {
            doStop();
        }
    }

    public void doLeft(View v) {
        if (isRunning) {
            doReset();
        } else {
            doLap();
        }
    }

    private void doStart() {
        myTask = new MyTask();
        timer.schedule(myTask,0,10);
    }

    private void doStop() {
        if (myTask != null) {
            myTask.cancel();
            myTask = null;
        }
        counter = 0;
        myHandler.sendEmptyMessage(113);
    }

    private void doLap() {

    }

    private void doReset() {

    }

    private class MyTask extends TimerTask {
        @Override
        public void run() {
            counter++;
            //-----只是為了要觸發  counter++  所以裡面的Int值隨便都可以
            myHandler.sendEmptyMessage(100);
        }
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            showTime.setText(""+counter);
        }
    }
}
