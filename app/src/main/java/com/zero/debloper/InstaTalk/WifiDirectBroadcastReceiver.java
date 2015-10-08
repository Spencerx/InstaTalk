package com.zero.debloper.InstaTalk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.util.Log;

public class WifiDirectBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager mWifiP2pManager;
    private Channel mChannel;
    private MainActivity mActivity;
    private final String TAG = "WifiDirectReceiver";

    public WifiDirectBroadcastReceiver(WifiP2pManager wifiP2pManager, Channel channel, MainActivity activity) {
        super();
        mWifiP2pManager = wifiP2pManager;
        mChannel = channel;
        mActivity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        Log.v(TAG, "onReceive action<" + action + ">");
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Check to see if WiFi is enabled and notify appropriate activity
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                Log.d(TAG, "WiFi P2P is enabled.");
                mActivity.setIsWiFiP2PEnabled(true);
            } else {
                Log.d(TAG, "WiFi P2P is disabled!!");
                mActivity.setIsWiFiP2PEnabled(false);
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // Call WifiP2pManager.requestPeers to get a list of current peers
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Respond to new connection of disconnections
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            // Respond to this device's wifi state changing
        }

    }
}
