package slotmachine.johnnywaity.com.slotmachine;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import bluetooth.BTBridge;
import bluetooth.BTConnector;
import bluetooth.BTConnectorDelegate;
import bluetooth.BTData;
import bluetooth.BTType;

public class BTConfig extends Activity  implements BTConnectorDelegate {

    private final BroadcastReceiver reciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Toast.makeText(getBaseContext(), device.getName(), Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btconfig);
        final BTConnector config = new BTConnector(this);
        Button join = findViewById(R.id.join);
        Button host = findViewById(R.id.host);

        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config.startConnect();
            }
        });
        host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config.startAccept();
            }
        });


        if(mBluetoothAdapter == null){
            //Bt not supported
            Toast.makeText(this.getBaseContext(), "Bluetooth Is Not supported On This Device", Toast.LENGTH_LONG).show();
            return;
        }
        if(!mBluetoothAdapter.isEnabled()){
            //Bt Not Enabled
            Toast.makeText(this.getBaseContext(), "Bluetooth Is Off Please Enable", Toast.LENGTH_LONG).show();
        }


    }


    @Override
    public void onConnect(BluetoothSocket s) {
        BTBridge bridge = new BTBridge(s);
        bridge.start();

        Intent t = new Intent(BTConfig.this, GameActivity.class);
        startActivity(t);
    }
}
