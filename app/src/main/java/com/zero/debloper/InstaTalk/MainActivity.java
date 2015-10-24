package com.zero.debloper.InstaTalk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements DeviceListFragment.DeviceActionListener{

    public static final String TAG = "MainActivity";
    WifiP2pManager mManager;
    Channel mChannel;
    BroadcastReceiver mReceiver = null;
    private  final IntentFilter mIntentFiler = new IntentFilter();
    private boolean isWiFiP2PEnabled = false;

    /**
     *
     * @param isWiFiP2PEnabled true if WiFi P2p enabled
     *                         false otherwise
     */
    public void setIsWiFiP2PEnabled(boolean isWiFiP2PEnabled) {
        this.isWiFiP2PEnabled = isWiFiP2PEnabled;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);

        mIntentFiler.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFiler.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFiler.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFiler.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mReceiver = new WifiDirectBroadcastReceiver(mManager, mChannel, this);
        registerReceiver(mReceiver, mIntentFiler);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_direct_enable:
                if (mManager != null && mChannel != null) {
                    // Since this is the system wireless activity,
                    // it is not going to send us a result,
                    // We will be notified by WiFiDirectBroadcastReceiver instead.
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                }

                return true;
            case R.id.action_direct_discover:
                if (!isWiFiP2PEnabled) {
                    Toast.makeText(getApplicationContext(), R.string.p2p_off_warning, Toast.LENGTH_SHORT).show();
                    return true;
                }

                final DeviceListFragment fragment = (DeviceListFragment) getFragmentManager().findFragmentById(R.id.frag_list);
                fragment.onInitiateDiscovery();
                mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(MainActivity.this, "Discovery Initiated", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int reasonCode) {
                        Toast.makeText(MainActivity.this, "Discovery Failed!! reasonCode<" + reasonCode + ">", Toast.LENGTH_SHORT).show();
                    }
                });

                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Remove all peers and clear all fields. This is called on
     * BroadcastReceiver receiving a state change event.
     */
    public void resetData() {
        DeviceListFragment listFragment = (DeviceListFragment) getFragmentManager().findFragmentById(R.id.frag_list);
        if (listFragment != null) {
            listFragment.clearPeers();
        }
    }

    @Override
    public void showDetails(WifiP2pDevice device) {
        Log.v(TAG, "showDetails");
        DeviceDetailFragment fragment = (DeviceDetailFragment) getFragmentManager().findFragmentById(R.id.frag_detail);
        fragment.showDetails(device);
    }

    @Override
    public void connect(WifiP2pConfig config) {

    }

    @Override
    public void disconnect() {

    }

    @Override
    public void cancelDisconnect() {

    }
}
