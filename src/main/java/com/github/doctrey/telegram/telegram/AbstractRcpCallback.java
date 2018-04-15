package com.github.doctrey.telegram.telegram;

import org.telegram.api.engine.Logger;
import org.telegram.api.engine.RpcCallbackEx;
import org.telegram.tl.TLObject;

/**
 * Created by Soheil on 12/26/17.
 */
public abstract class AbstractRcpCallback<T extends TLObject> implements RpcCallbackEx<T> {

    private static final String TAG = "AbstractRcpCallback";

    @Override
    public void onConfirmed() {

    }

    @Override
    public abstract void onResult(T result);

    @Override
    public void onError(int errorCode, String errorText) {
        Logger.w(TAG, "RCP call failed with error -----> " + errorCode + " " + errorText);
    }
}
