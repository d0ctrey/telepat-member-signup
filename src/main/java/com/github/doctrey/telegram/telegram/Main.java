package com.github.doctrey.telegram.telegram;

import com.github.doctrey.telegram.client.PhoneNumberService;
import com.github.doctrey.telegram.client.impl.AppRyanPhoneNumberService;
import com.github.doctrey.telegram.dao.PhoneNumberDao;
import com.github.doctrey.telegram.telegram.api.ApiConstants;
import com.github.doctrey.telegram.telegram.utils.RpcUtils;
import org.telegram.api.TLConfig;
import org.telegram.api.TLNearestDc;
import org.telegram.api.auth.TLCheckedPhone;
import org.telegram.api.engine.AppInfo;
import org.telegram.api.engine.TelegramApi;
import org.telegram.api.functions.auth.TLRequestAuthCheckPhone;
import org.telegram.api.functions.help.TLRequestHelpGetConfig;
import org.telegram.api.functions.help.TLRequestHelpGetNearestDc;

/**
 * Created by Soheil on 4/13/18.
 */
public class Main {

    private static boolean GOT_ONE = false;

    public static void main(String[] args) {

        ApiStorage apiStateStorage = new ApiStorage("default");
        DefaultApiCallback apiCallback = new DefaultApiCallback();
        TelegramApi api = new TelegramApi(apiStateStorage, new AppInfo(ApiConstants.API_ID,
                System.getenv("TL_DEVICE_MODEL"), System.getenv("TL_DEVICE_VERSION"), "0.0.1", "en"), apiCallback);
        RpcUtils rpcUtils = new RpcUtils(api);
        TLConfig config = null;
        try {
            config = rpcUtils.doRpc(new TLRequestHelpGetConfig(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        apiStateStorage.updateSettings(config);
        api.resetConnectionInfo();
        TLNearestDc tlNearestDc = null;
        try {
            tlNearestDc = rpcUtils.doRpc(new TLRequestHelpGetNearestDc(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        rpcUtils.switchToDc(tlNearestDc.getNearestDc());

        while (!GOT_ONE) {
            PhoneNumberService numberService = new AppRyanPhoneNumberService();
            String mobileNumber = numberService.getMobileNumber();

            TLRequestAuthCheckPhone requestAuthCheckPhone = new TLRequestAuthCheckPhone();
            requestAuthCheckPhone.setPhoneNumber(mobileNumber);
            try {
                TLCheckedPhone checkedPhone = rpcUtils.doRpc(requestAuthCheckPhone, false);
                System.out.println("GOT ONE -------------> " + mobileNumber);
                GOT_ONE = true;
                PhoneNumberDao phoneNumberDao = new PhoneNumberDao();
                phoneNumberDao.save(mobileNumber);
                api.close();
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    numberService.addToBlackList(mobileNumber);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
