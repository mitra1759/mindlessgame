package bluetooth;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class BTBridge extends Thread {

    private BluetoothSocket socket;
    private final InputStream in;
    private final OutputStream out;

    private static BTBridge sharedInstance;

    private BridgeDelegate delegate;

    public BTBridge(BluetoothSocket socket) {
        sharedInstance = this;
        this.socket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        try{
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        }catch (IOException e){
            System.out.println("Error Getting Streams");
        }
        in = tmpIn;
        out = tmpOut;
    }

    public void run(){
        while(true){
            try{
                BTData d = (BTData) new ObjectInputStream(in).readObject();
                if(delegate != null){
                    delegate.onMessage(d);
                }
            }catch (IOException e){
                System.out.println(e);
            }catch (ClassNotFoundException c){
                System.out.println(c);
            }
        }
    }

    public void write(BTData d){
        try{
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(d);
        }catch (IOException e){
            System.out.println("Write Error");
        }
    }

    public static BTBridge getSharedInstance() {
        return sharedInstance;
    }

    public void setDelegate(BridgeDelegate delegate) {
        this.delegate = delegate;
    }
}
