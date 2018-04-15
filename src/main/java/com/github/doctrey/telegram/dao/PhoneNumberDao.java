package com.github.doctrey.telegram.dao;

import com.github.doctrey.telegram.utils.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Soheil on 4/14/18.
 */
public class PhoneNumberDao {

    public void save(String phoneNumber) {

        try(
                Connection conn = ConnectionPool.getInstance().getConnection();
                PreparedStatement statement = conn.prepareStatement("INSERT INTO tl_phone_numbers(status, phone_number) VALUES (1, ?)")
        ) {
            statement.setString(1, phoneNumber);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
