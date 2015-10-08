package com.zero.debloper.InstaTalk;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by debloper on 13/9/15.
 *
 * A ListFragment that displays available peers on discovery and requests the
 * parent activity to handle user interaction events
 */

public class DeviceListFragment extends ListFragment implements PeerListListener{

    final String TAG = "DeviceListFragment";
    ProgressDialog mProgressDialog = null;
    private List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();

    @Override
    public void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

        peers.clear();
        peers.addAll(wifiP2pDeviceList.getDeviceList());


        if (peers.size() == 0) {
            Log.d(TAG, "No device found!!");
        }
    }

    public void onInitiateDiscovery() {

    }
}
