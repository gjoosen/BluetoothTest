package gjoosen.nl.bluetoothtest.ble;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;

import gjoosen.nl.bluetoothtest.HandlerManager;
import gjoosen.nl.bluetoothtest.log.BluetoothLogger;

import static gjoosen.nl.bluetoothtest.ble.BluetoothLowEnergyDeviceScanCallback.NUMBER_OF_DEVICES_FOUND;

/**
 * Created by gjoosen on 22/09/2017.
 */
public class BluetoothLowEnergyService {

    //reference to the Activity (only 1 Activity used)
    private Activity rootActivity;

    //adapter + callback to scan for devices
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLowEnergyDeviceScanCallback deviceScanCallback;
    private BluetoothLowEnergyGattCallback gattCallback;

    //gatt
    private BluetoothGatt bluetoothGatt;

    public BluetoothLowEnergyService(Activity rootActivity) {
        //keep reference to activity
        this.rootActivity = rootActivity;

        //set bluetooth adapter
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //create callback to scan for new devices
        this.deviceScanCallback = new BluetoothLowEnergyDeviceScanCallback(this);

        //create callback for gatt
        this.gattCallback = new BluetoothLowEnergyGattCallback(this);
    }

    /**
     * Stop bluetooth adapter from scanning for new devices (if not null)
     */
    public void stopScanningForNewDevices() {
        if (this.bluetoothAdapter != null) {
            BluetoothLogger.getBluetoothLogger().logMessage("Stop scanning for devices");
            this.bluetoothAdapter.stopLeScan(this.deviceScanCallback);
        }
    }

    /**
     * Start scanning for new bluetooth low energy devices
     */
    public void startScanningForNewDevices() {
        if(this.bluetoothAdapter != null){
            BluetoothLogger.getBluetoothLogger().logMessage("Scanning for new devices");
            this.bluetoothAdapter.startLeScan(this.deviceScanCallback);

            //start counting number of devices found, release after 1 minute
            NUMBER_OF_DEVICES_FOUND = 0;

            HandlerManager.getHandlerManager().getDeviceScanOneMinuteHandler().postDelayed(()->{
                BluetoothLogger.getBluetoothLogger().logMessage("Number of devices found in 1 minute: " + NUMBER_OF_DEVICES_FOUND);

                if(NUMBER_OF_DEVICES_FOUND == 0){
                    //Restart bluetooth
                    this.restartBluetoothSearch();
                }

            }, 60 * 1000); //1min
        }
    }

    public void stopGatt(){
        BluetoothLogger.getBluetoothLogger().logMessage("Disconnect from Gatt connection");
        if(this.bluetoothGatt != null){
            // For better reliability be careful to disconnect and close the connection.
            this.bluetoothGatt.disconnect();
            this.bluetoothGatt.close();
            this.bluetoothGatt = null;
            this.bluetoothGatt = null;
        }
        BluetoothLowEnergyDeviceScanCallback.currentBluetoothState = BluetoothLowEnergyDeviceScanCallback.BLUETOOTH_STATE.DISCONNECTED;
    }

    /**
     * Stop bluetooth scan and start bluetooth LE devices scan
     */
    private static boolean restartBluetoothSearchLock = false;

    public void restartBluetoothSearch(){
        if(!restartBluetoothSearchLock){
            restartBluetoothSearchLock = true;
            //stop force disconnect handler
            HandlerManager.getHandlerManager().getForceDisconnectHandler().removeCallbacksAndMessages(null);
            HandlerManager.getHandlerManager().getConnectionHandler().removeCallbacksAndMessages(null);
            HandlerManager.getHandlerManager().getDeviceScanOneMinuteHandler().removeCallbacksAndMessages(null);

//            BluetoothLogger.getBluetoothLogger().logMessage("Device found with name: " + NODE_NAME);

            BluetoothLogger.getBluetoothLogger().logMessage("Restart bluetooth devices search");
            BluetoothLowEnergyDeviceScanCallback.currentBluetoothState = BluetoothLowEnergyDeviceScanCallback.BLUETOOTH_STATE.DISCONNECTED;
            this.stopGatt();
            this.stopScanningForNewDevices();
            this.startScanningForNewDevices();
            restartBluetoothSearchLock = false;
        }
    }

    /**
     * Getters and Setters
     */
    public Activity getRootActivity() {
        return rootActivity;
    }

    public BluetoothLowEnergyGattCallback getGattCallback() {
        return gattCallback;
    }

    public void setGatt(BluetoothGatt gatt) {
        this.bluetoothGatt = gatt;
    }
}