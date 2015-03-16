package cc.arduino.bean_test;

import android.bluetooth.BluetoothDevice;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Collection;

import nl.littlerobots.bean.Bean;
import nl.littlerobots.bean.BeanDiscoveryListener;
import nl.littlerobots.bean.BeanListener;
import nl.littlerobots.bean.BeanManager;
import nl.littlerobots.bean.message.Callback;
import nl.littlerobots.bean.message.SketchMetaData;

public class MainActivity extends ActionBarActivity {

    Bean mBean;

    String TAG = "Verkstad Bean";

    /*
     code from: http://stackoverflow.com/questions/26854480/android-app-bluetooth-lightblue-bean
     */
    BeanDiscoveryListener blistener = new BeanDiscoveryListener() {
        @Override
        public void onBeanDiscovered(Bean bean) {
            mBean = bean;

            BeanManager.getInstance().cancelDiscovery();
            Toast.makeText(getApplicationContext(), "Bean discovered - "+this, Toast.LENGTH_LONG).show();

            // some information for the log
            Log.i(TAG,  "Bean discovered - name: "+ bean.getDevice().getName());
            Log.i(TAG,  "Bean discovered - address: "+ bean.getDevice().getAddress());
            Log.i(TAG,  "Bean discovered - BT class: "+ bean.getDevice().getBluetoothClass().toString());

            /*
             this is the connected fun
             */
            mBean.connect(getApplicationContext(), myBeanListener);

            Log.i(TAG, "Starting connection");
        }

        @Override
        public void onDiscoveryComplete() {
            int numbre = BeanManager.getInstance().getBeans().size();
            Collection<Bean> beans = BeanManager.getInstance().getBeans();

            Toast.makeText(getApplicationContext(), numbre+" Beans Found", Toast.LENGTH_LONG).show();
        }
    };

    BeanListener myBeanListener = new BeanListener() {
        @Override
        public void onConnected() {
            Toast.makeText(getApplicationContext(), "CONNECTED TO BEAN", Toast.LENGTH_LONG).show();

            // reading things takes a callback:
            mBean.readSketchMetaData(new Callback<SketchMetaData>() {
                @Override
                public void onResult(SketchMetaData result) {
                    Log.i(TAG, "Running sketch " + result);
                }
            });

        }

        @Override
        public void onConnectionFailed() {
            Toast.makeText(getApplicationContext(), "CONNECTED FAILED", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onDisconnected() {
            Toast.makeText(getApplicationContext(), "BEAN DISCONNECTED", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onSerialMessageReceived(byte[] bytes) {
            Toast.makeText(getApplicationContext(), "Byte - " + new String(bytes), Toast.LENGTH_LONG).show();
            Log.i(TAG, "Serial received: " + new String(bytes));
        }

        @Override
        public void onScratchValueChanged(int i, byte[] bytes) {

        }
    };

    private void cancelBeanDiscovery(){
        BeanManager.getInstance().cancelDiscovery();
    }

    /*
     end of code
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    /*
      and here the fun
     */
        BeanManager.getInstance().startDiscovery(blistener);

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
    public void onDestroy() {
        // when you're done, don't forget to disconnect
        Log.i(TAG, "Disconnect");
        mBean.disconnect();
    }

    public void lightOff(View v) {
        // after the connection is instantiated, briefly flash the led:
        Log.i(TAG, "Sending command: light off");
        mBean.sendSerialMessage("L 6 0\n");
    }

    public void lightOn(View v) {
        // after the connection is instantiated, briefly flash the led:
        Log.i(TAG, "Sending command: light on");
        mBean.sendSerialMessage("L 6 99\n");
    }

    public void errorTest(View v) {
        // after the connection is instantiated, briefly flash the led:
        Log.i(TAG, "Sending command: lalala");
        mBean.sendSerialMessage("l");
    }
}
