package gjoosen.nl.bluetoothtest.log;

import android.app.Activity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import org.w3c.dom.Text;

import gjoosen.nl.bluetoothtest.R;

/**
 * Created by gjoosen on 23/09/2017.
 */

public class BluetoothTextViewLogger implements BluetoothLoggerSubscriber {

    private Activity activity;
    private TextView logTextView;

    public BluetoothTextViewLogger(Activity activity) {
        this.activity = activity;
        logTextView = (TextView) activity.findViewById(R.id.bluetoothLogTextView);
        logTextView.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public void processBluetoothLoggerMessage(String bluetoothLoggerMessage) {
        activity.runOnUiThread(()->{
            logTextView.append("[BLE]" + bluetoothLoggerMessage + "\n");
        });
    }
}