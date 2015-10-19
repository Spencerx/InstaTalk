package com.zero.debloper.InstaTalk;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by debloper on 19/10/15.
 */
public class FileTransferService extends IntentService {
    public static final String ACTION_SEND_FILE = "com.zero.debloper.InstaTalk.SEND_FILE";
    public static final String EXTRAS_FILE_PATH = "file_url";
    public static final String EXTRAS_GROUP_OWNER_ADDRESS = "go_host";
    public static final String EXTRAS_GROUP_OWNER_PORT = "go_port";

    public FileTransferService(String name) {
        super(name);
    }

    public FileTransferService() {
        super("FileTransferService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
