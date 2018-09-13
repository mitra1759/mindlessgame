package bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

public class BTConnector {
    private UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
    private static String TAG = "BTConnector";
    private final BTConnectorDelegate delegate;

    public BTConnector (BTConnectorDelegate delegate){
        this.delegate = delegate;
    }

    public void startAccept(){
        System.out.println("Start Host");
        AcceptThread accept = new AcceptThread();
        accept.start();
    }
    public void startConnect(){
        System.out.println("Start Connect");
        ConnectThread c = new ConnectThread((BluetoothDevice) adapter.getBondedDevices().toArray()[0]);
        c.start();
    }


    private class AcceptThread extends Thread {
        private final BluetoothServerSocket serverSocket;

        public AcceptThread(){
            BluetoothServerSocket tmp = null;
            try{
                tmp = adapter.listenUsingInsecureRfcommWithServiceRecord("Slot", MY_UUID);
            }catch (IOException e){
                Log.e(TAG, "Socket's listen() method failed", e);
            }
            serverSocket = tmp;
        }

        public void run(){
            BluetoothSocket socket = null;
            while(true){
                try{
                    socket = serverSocket.accept();
                }catch (IOException e){
                    Log.e(TAG, "Socket's accept() method failed", e);
                }
                if(socket != null){
                    try{
                        serverSocket.close();
                    }catch (IOException e){
                        Log.e(TAG, "Failed Closing Server Socket", e);
                    }

                    break;
                }
            }

            delegate.onConnect(socket);
        }

        public void cancel(){
            try {
                serverSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the connect socket", e);
            }
        }
    }

    private class ConnectThread extends Thread {
        private final BluetoothSocket socket;
        private final BluetoothDevice device;

        public ConnectThread(BluetoothDevice device){
            BluetoothSocket tmp = null;
            this.device = device;

            try{
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            }catch (IOException e){
                Log.e(TAG, "Socket's create() method failed", e);
                System.out.println("Failed To Create ");
            }
            socket = tmp;
        }

        public void run(){
            adapter.cancelDiscovery();

            try{
                socket.connect();
            }catch (IOException e){
                System.out.println("Coundn't Connect " + e.getLocalizedMessage());
                try {
                    socket.close();
                } catch (IOException closeException) {
                    Log.e(TAG, "Could not close the client socket", closeException);

                }
                return;
            }

            System.out.println("CONNECTED");
            delegate.onConnect(socket);

        }

        public void cancel() {
            try {
                socket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the client socket", e);
            }
        }
    }
}
