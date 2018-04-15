package com.github.doctrey.telegram.telegram;

import org.telegram.api.engine.ApiCallback;
import org.telegram.api.engine.Logger;
import org.telegram.api.engine.TelegramApi;
import org.telegram.api.updates.TLAbsUpdates;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by s_tayari on 4/12/2018.
 */
public class DefaultApiCallback implements ApiCallback {

    private static final String TAG = "DefaultApiCallback";

    @Override
    public void onUpdatesInvalidated(TelegramApi _api) {
        Logger.d(TAG, "");
    }

    @Override
    public void onAuthCancelled(TelegramApi _api) {
        Logger.d(TAG, "");
    }

    @Override
    public void onUpdate(TLAbsUpdates updates) {

    }

}
