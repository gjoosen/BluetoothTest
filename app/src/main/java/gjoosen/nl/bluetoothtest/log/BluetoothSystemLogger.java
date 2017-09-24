package gjoosen.nl.bluetoothtest.log;

import android.util.Log;

/**
 * Created by gjoosen on 23/09/2017.
 */

public class BluetoothSystemLogger implements BluetoothLoggerSubscriber {

    private static final String TAG = "BLE";

    @Override
    public void processBluetoothLoggerMessage(String bluetoothLoggerMessage) {
        Log.d(TAG, bluetoothLoggerMessage);
    }
}
