package gjoosen.nl.bluetoothtest.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.util.Log;

import gjoosen.nl.bluetoothtest.HandlerManager;
import gjoosen.nl.bluetoothtest.log.BluetoothLogger;

/**
 * Created by gjoosen on 22/09/2017.
 */

public class BluetoothLowEnergyDeviceScanCallback implements BluetoothAdapter.LeScanCallback {
    private static final String NODE_NAME = "<TODO>"; //TODO: BLE device name to connect to

    //Bluetooth connection states
    public enum BLUETOOTH_STATE {CONNECTED, DISCONNECTED, CONNECTINGATTEMPT};
    public static BLUETOOTH_STATE currentBluetoothState = BLUETOOTH_STATE.DISCONNECTED;

    //connection
    private BluetoothLowEnergyService bluetoothLowEnergyService;

    //logging
    public static int NUMBER_OF_DEVICES_FOUND = 0;

    public BluetoothLowEnergyDeviceScanCallback(BluetoothLowEnergyService bluetoothLowEnergyService) {
        this.bluetoothLowEnergyService = bluetoothLowEnergyService;
    }

    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        if(currentBluetoothState == BLUETOOTH_STATE.DISCONNECTED){
            String deviceName = device.getName();
            Log.d("BL_DEVICES_SCAN", "Found device: " + device.getAddress() +" -name: " + deviceName + " alias: " );

            //Increase devices found
            NUMBER_OF_DEVICES_FOUND++;

            if(deviceName == null){
                return;
            }

            //check name
            if(deviceName.equals(NODE_NAME)){
                //Found a device, stop the scan
                this.bluetoothLowEnergyService.stopScanningForNewDevices();
                BluetoothLogger.getBluetoothLogger().logMessage("Device found with name: " + NODE_NAME);

                //Connect to the device
                if(currentBluetoothState == BLUETOOTH_STATE.DISCONNECTED){
                    //state to connection attempt
                    currentBluetoothState = BLUETOOTH_STATE.CONNECTINGATTEMPT;
                    BluetoothLogger.getBluetoothLogger().logMessage("Bluetooth attempting to connect");


                    HandlerManager.getHandlerManager().getConnectionHandler().postDelayed(()->{
                        if(currentBluetoothState != BLUETOOTH_STATE.CONNECTED){
                            BluetoothLogger.getBluetoothLogger().logMessage("Gatt connect timed out!");
                            this.bluetoothLowEnergyService.restartBluetoothSearch();
                        }
                    }, 60 * 1000); //1min to connect

                    BluetoothGatt gatt = device.connectGatt(this.bluetoothLowEnergyService.getRootActivity().getApplication(), false, this.bluetoothLowEnergyService.getGattCallback());
                    if(gatt == null){
                        BluetoothLogger.getBluetoothLogger().logMessage("Bluetooth Gatt null, Disconnect");
                        currentBluetoothState = BLUETOOTH_STATE.DISCONNECTED;

                    }else{
                        this.bluetoothLowEnergyService.setGatt(gatt);
                    }
                }
            }
        }
    }
}