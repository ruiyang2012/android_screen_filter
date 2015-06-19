package com.example.ruiyang.screenfilter;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.ValueBar;


public class MainActivity extends Activity implements ColorPicker.OnColorChangedListener {

    private final android.view.WindowManager.LayoutParams c = new android.view.WindowManager.LayoutParams(-1, -1, 2006, 0x80418, -3);
    private int curColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        curColor = 0xffffff;



        setUpColor();
        final Button startBtn = (Button) findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent t = new Intent(getApplicationContext(), MyIntentService.class);
                t.addCategory(MyIntentService.TAG);
                t.putExtra("color", curColor);
                startService(t);
            }
        });
        final Button stopBtn = (Button) findViewById(R.id.stopBtn);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent t = new Intent(getApplicationContext(), MyIntentService.class);
                t.addCategory(MyIntentService.TAG);
                stopService(t);
            }
        });

    }

    private void setBk() {
        final WindowManager.LayoutParams params =  this.getWindow().getAttributes();
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        getWindow().setAttributes(params);
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        //     setContentView(R.layout.activity_main);

        final WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                      | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                      | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);

        WindowManager wm =
                (WindowManager) getApplicationContext()
                        .getSystemService(Context.WINDOW_SERVICE);



    }

    private void setUpColor() {
        ColorPicker picker = (ColorPicker) findViewById(R.id.picker);



//To get the color
        picker.setColor(0xffffff);
        picker.getColor();

//To set the old selected color u can do it like this
        picker.setOldCenterColor(picker.getColor());
// adds listener to the colorpicker which is implemented
//in the activity
        picker.setOnColorChangedListener(this);

//to turn of showing the old color
        picker.setShowOldCenterColor(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onColorChanged(int color) {
        curColor = color;

        Intent t = new Intent(getApplicationContext(), MyIntentService.class);
        t.addCategory(MyIntentService.TAG);
        t.putExtra("color", curColor);
        sendBroadcast(t);

    }
}


