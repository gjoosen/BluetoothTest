package gjoosen.nl.bluetoothtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import gjoosen.nl.bluetoothtest.ble.BluetoothLowEnergyService;
import gjoosen.nl.bluetoothtest.log.BluetoothLogger;
import gjoosen.nl.bluetoothtest.log.BluetoothSystemLogger;
import gjoosen.nl.bluetoothtest.log.BluetoothTextViewLogger;

public class MainActivity extends AppCompatActivity {

    private BluetoothLowEnergyService bluetoothLowEnergyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.bluetoothLowEnergyService = new BluetoothLowEnergyService(this);

        //Add system log bluetooth logger
        BluetoothLogger.getBluetoothLogger().addBluetoothLoggerSubscriber(new BluetoothSystemLogger());
        BluetoothLogger.getBluetoothLogger().addBluetoothLoggerSubscriber(new BluetoothTextViewLogger(this));

        //Start scanning for devices
        this.bluetoothLowEnergyService.startScanningForNewDevices();
    }
}