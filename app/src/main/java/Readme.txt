Bluetooth Test App Readme.

——How does it work———
This app will attempt to find a bluetooth low energy device with the name defined in the class BluetoothLowEnergyDeviceScanCallback, constant name: NODE_NAME
The device will attempt to connect to this device.
If successfully connected to the device the app will disconnect with the device after 1 minute and restart.
If something went wrong while connecting, the app will also restart searching and connecting.
After some period the device won’t find any devices anymore, the app will print an output like here (only logging Verbose - BLE)

---TODO---
Fill in NODE_NAME in BluetoothLowEnergyDeviceScanCallback, the NODE_NAME is a bluetooth low energy device which accepts connections.

———Example output when de device doesn’t find any devices————
09-25 16:18:39.147 17695-17695/gjoosen.nl.bluetoothtest D/BLE: Number of devices found in 1 minute: 0
09-25 16:18:39.160 17695-17695/gjoosen.nl.bluetoothtest D/BLE: Restart bluetooth devices search
09-25 16:18:39.171 17695-17695/gjoosen.nl.bluetoothtest D/BLE: Disconnect from Gatt connection
09-25 16:18:39.178 17695-17695/gjoosen.nl.bluetoothtest D/BLE: Stop scanning for devices
09-25 16:18:39.185 17695-17695/gjoosen.nl.bluetoothtest D/BLE: Scanning for new devices
09-25 16:19:39.227 17695-17695/gjoosen.nl.bluetoothtest D/BLE: Number of devices found in 1 minute: 0
09-25 16:19:39.235 17695-17695/gjoosen.nl.bluetoothtest D/BLE: Restart bluetooth devices search
09-25 16:19:39.244 17695-17695/gjoosen.nl.bluetoothtest D/BLE: Disconnect from Gatt connection
09-25 16:19:39.253 17695-17695/gjoosen.nl.bluetoothtest D/BLE: Stop scanning for devices
09-25 16:19:39.261 17695-17695/gjoosen.nl.bluetoothtest D/BLE: Scanning for new devices
09-25 16:20:39.317 17695-17695/gjoosen.nl.bluetoothtest D/BLE: Number of devices found in 1 minute: 0
09-25 16:20:39.329 17695-17695/gjoosen.nl.bluetoothtest D/BLE: Restart bluetooth devices search
09-25 16:20:39.338 17695-17695/gjoosen.nl.bluetoothtest D/BLE: Disconnect from Gatt connection
09-25 16:20:39.345 17695-17695/gjoosen.nl.bluetoothtest D/BLE: Stop scanning for devices
09-25 16:20:39.352 17695-17695/gjoosen.nl.bluetoothtest D/BLE: Scanning for new devices
09-25 16:21:39.446 17695-17695/gjoosen.nl.bluetoothtest D/BLE: Number of devices found in 1 minute: 0
09-25 16:21:39.454 17695-17695/gjoosen.nl.bluetoothtest D/BLE: Restart bluetooth devices search
09-25 16:21:39.461 17695-17695/gjoosen.nl.bluetoothtest D/BLE: Disconnect from Gatt connection
09-25 16:21:39.470 17695-17695/gjoosen.nl.bluetoothtest D/BLE: Stop scanning for devices
09-25 16:21:39.477 17695-17695/gjoosen.nl.bluetoothtest D/BLE: Scanning for new devices