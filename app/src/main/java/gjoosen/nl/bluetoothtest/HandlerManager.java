package gjoosen.nl.bluetoothtest;

import android.os.Handler;

/**
 * Created by gjoosen on 23/09/2017.
 */

public class HandlerManager {

    private static HandlerManager handlerManager;
    private Handler connectionHandler;
    private Handler forceDisconnectHandler;
    private Handler deviceScanOneMinuteHandler;


    public static HandlerManager getHandlerManager(){
        if(handlerManager == null){
            handlerManager = new HandlerManager();
        }
        return handlerManager;
    }

    private HandlerManager(){
        //gatt connection timeout handler
        this.connectionHandler = new Handler();

        //force disconnect handler
        this.forceDisconnectHandler = new Handler();

        //Handler to print out number of devices found in 1min, if 0 there is a problem
        this.deviceScanOneMinuteHandler = new Handler();
    }


    public Handler getConnectionHandler() {
        return connectionHandler;
    }

    public Handler getForceDisconnectHandler() {
        return forceDisconnectHandler;
    }

    public Handler getDeviceScanOneMinuteHandler() {
        return deviceScanOneMinuteHandler;
    }
}