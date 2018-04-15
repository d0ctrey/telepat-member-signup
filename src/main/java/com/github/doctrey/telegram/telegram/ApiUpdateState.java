package com.github.doctrey.telegram.telegram;

import com.github.doctrey.telegram.telegram.api.TLPersistence;
import org.telegram.api.updates.TLUpdatesState;

/**
 * Created by Soheil on 12/25/17.
 */
public class ApiUpdateState extends TLPersistence<TLUpdatesState> {

    private static final String STATE_FILE_NAME = "state.bin";

    public ApiUpdateState(String phoneNumber) {
        super(phoneNumber, STATE_FILE_NAME, TLUpdatesState.class);
    }

    public void updateState(TLUpdatesState tlState) {
        getObj().setDate(tlState.getDate());
        getObj().setPts(tlState.getPts());
        getObj().setQts(tlState.getQts());
        getObj().setSeq(tlState.getSeq());
        getObj().setUnreadCount(tlState.getUnreadCount());
        super.write(STATE_FILE_NAME);
    }

}
