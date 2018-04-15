package com.github.doctrey.telegram.client;

/**
 * Created by s_tayari on 4/4/2018.
 */
public interface PhoneNumberService {

    String getMobileNumber();
    void addToBlackList(String mobileNumber) throws Exception;
    String getCode(String mobileNumber);

}
