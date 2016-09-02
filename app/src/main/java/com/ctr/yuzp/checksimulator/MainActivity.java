package com.ctr.yuzp.checksimulator;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class MainActivity extends Activity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasQEmuFiles()) {
                    Toast.makeText(MainActivity.this, "本台设备为模拟器", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "本台设备为真机", Toast.LENGTH_LONG).show();
                }
            }


        });
      ///  getMac();
    }


        public boolean hasQEmuFiles() {
        boolean isEmuFile = false;
        String a=null;
        a = getMac();
//            WifiManager wifi = (WifiManager)this.getSystemService(Context.WIFI_SERVICE);
//            WifiInfo info = wifi.getConnectionInfo();
//            String wifiMac = info.getMacAddress();

            if(a==null){
                isEmuFile = true;
        }else{
            final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

            String imei = tm.getDeviceId();

            String imsi = tm.getSubscriberId();
            if("000000000000000 ".equalsIgnoreCase(imei)||"310260000000000".equalsIgnoreCase(imsi)){
                isEmuFile = true;
            }else{
                String serial = android.os.Build.SERIAL;
                    if("unknown".equalsIgnoreCase(serial)){
                        isEmuFile = true;
                    }
            }
        }
        return isEmuFile;
    }


    String getMac() {
        String macSerial = null;
        String str = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);


            for (; null != str;) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }
        return macSerial;
    }
}
