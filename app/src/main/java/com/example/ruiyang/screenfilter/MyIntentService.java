package com.example.ruiyang.screenfilter;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Rui.yang on 6/11/15.
 */
public class MyIntentService extends Service{
    public static final String TAG = "com.example.ServiceExample";
    private NotificationManager mNM;


    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override

        public void onReceive(Context context, Intent intent) {

            int color = intent.getIntExtra("color", 0xffffff);
            mView.setBackgroundColor(color);
        }

        ;
    };



    View mView;

    LayoutInflater inflate;
    TextView t;

    @Override
    public void onCreate() {
        super.onCreate();

        Toast.makeText(getBaseContext(), R.string.local_service_started, Toast.LENGTH_LONG).show();


        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        Display display = wm.getDefaultDisplay();  //get phone display size
        Point size = new Point();
        display.getSize(size);

        int width = size.x;  // deprecated - get phone display width
        int height = size.y; // deprecated - get phone display height


        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                width,
                height,
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        |WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        |WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);


        params.gravity = Gravity.LEFT | Gravity.CENTER;
        params.setTitle("Load Average");

        inflate = (LayoutInflater) getBaseContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mView = inflate.inflate(R.layout.canvas, null);

        mView.setAlpha(0.5f);



        wm.addView(mView, params);
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        final IntentFilter myFilter = new IntentFilter();

        registerReceiver(mReceiver, myFilter);

    }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            int color = intent.getIntExtra("color", 0xffffff);
            mView.setBackgroundColor(color);

            return START_STICKY;
        }


    @Override
    public void onDestroy() {
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        if (mView != null)  wm.removeView(mView);
        mView = null;
        mNM.cancel(R.string.local_service_started);
        unregisterReceiver(mReceiver);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

}

