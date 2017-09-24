package gjoosen.nl.bluetoothtest.log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gjoosen on 23/09/2017.
 */

public class BluetoothLogger {

    //variables
    private List<BluetoothLoggerSubscriber> bluetoothLoggerSubscriberList;

    //singleton
    private static BluetoothLogger bluetoothLogger;
    public static BluetoothLogger getBluetoothLogger(){
        if(bluetoothLogger == null){
            bluetoothLogger = new BluetoothLogger();
        }
        return bluetoothLogger;
    }

    //private constructor
    private BluetoothLogger(){
        this.bluetoothLoggerSubscriberList = new ArrayList<>();
    }

    /**
     * Log bluetooth message
     * @param messageToLog message to log
     */
    public void logMessage(String messageToLog){
        for (BluetoothLoggerSubscriber bluetoothLoggerSubscriber : this.bluetoothLoggerSubscriberList) {
            bluetoothLoggerSubscriber.processBluetoothLoggerMessage(messageToLog);
        }
    }

    /**
     * Add subscriber to bluetooth logger
     * @param subscriber to register for logger messages
     */
    public void addBluetoothLoggerSubscriber(BluetoothLoggerSubscriber subscriber){
        this.bluetoothLoggerSubscriberList.add(subscriber);
    }
}