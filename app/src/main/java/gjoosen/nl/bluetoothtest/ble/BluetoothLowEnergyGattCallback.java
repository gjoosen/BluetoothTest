package gjoosen.nl.bluetoothtest.ble;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import gjoosen.nl.bluetoothtest.HandlerManager;
import gjoosen.nl.bluetoothtest.log.BluetoothLogger;

import static gjoosen.nl.bluetoothtest.ble.BluetoothLowEnergyDeviceScanCallback.currentBluetoothState;

/**
 * Created by gjoosen on 23/09/2017.
 */
public class BluetoothLowEnergyGattCallback extends BluetoothGattCallback {
    //Reference to parent
    private BluetoothLowEnergyService bluetoothLowEnergyService;

    public BluetoothLowEnergyGattCallback(BluetoothLowEnergyService bluetoothLowEnergyService) {
        this.bluetoothLowEnergyService = bluetoothLowEnergyService;
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);

        //Connected with bluetooth low energy device
        if(newState == BluetoothGatt.STATE_CONNECTED){
            //connected
            BluetoothLogger.getBluetoothLogger().logMessage("Connected to BLE device");

            //discover services (else error)
            if(!gatt.discoverServices()){
                BluetoothLogger.getBluetoothLogger().logMessage("Failed to start discovering services");

                //restart bluetooth search
                this.bluetoothLowEnergyService.restartBluetoothSearch();
            }
        }else if(newState == BluetoothGatt.STATE_DISCONNECTED){
            BluetoothLogger.getBluetoothLogger().logMessage("Bluetooth connection disconnected");

            //restart bluetooth search
            this.bluetoothLowEnergyService.restartBluetoothSearch();
        }else{
            //Not yet defined state change
            Log.d("BLE", "Connection state changed to newState: " + newState);
        }
    }

    private static int numberOfLongConnections = 0;

    public static boolean longConnectionBlocked = false;

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        super.onServicesDiscovered(gatt, status);
        List<BluetoothGattService> services = gatt.getServices();
        List<BluetoothGattCharacteristic> characteristicsList = new ArrayList<>();
        for (BluetoothGattService service : services) {
            List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
            characteristicsList.addAll(characteristics);
        }

        //get all UUID service in BLE device
        for (BluetoothGattService gattService : gatt.getServices()) {
            Log.i("BLE_Services", "Service UUID Found: " + gattService.getUuid().toString());
        }

        //Succesfully connected
        if (status == BluetoothGatt.GATT_SUCCESS) {
            BluetoothLogger.getBluetoothLogger().logMessage("Gatt succesfully connected!");
            longConnectionBlocked = false;
        } else {
            BluetoothLogger.getBluetoothLogger().logMessage("Gatt failed, disconnect!");
            longConnectionBlocked = true;
            currentBluetoothState = BluetoothLowEnergyDeviceScanCallback.BLUETOOTH_STATE.DISCONNECTED;
        }

        currentBluetoothState = BluetoothLowEnergyDeviceScanCallback.BLUETOOTH_STATE.CONNECTED;

        //TODO; disconnect function
        HandlerManager.getHandlerManager().getForceDisconnectHandler().postDelayed(()->{
            if(currentBluetoothState == BluetoothLowEnergyDeviceScanCallback.BLUETOOTH_STATE.CONNECTED && !longConnectionBlocked){
                //Log number of long connections
                numberOfLongConnections++;
                BluetoothLogger.getBluetoothLogger().logMessage("Force disconnect, long connection number: " + numberOfLongConnections);

                //disconnect
                bluetoothLowEnergyService.restartBluetoothSearch();
            }
        }, 60 * 1000); //1min connection -> debug disconnect
    }
}