package bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public interface BTConnectorDelegate {
    void onConnect(BluetoothSocket s);
}
