//package com.hmdm.persistence;
//
//import com.hmdm.persistence.mapper.CustomerMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import java.time.Instant;
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//
//@Repository
//public class CustomerDAO {
//
//    @Autowired
//    private final CustomerMapper mapper;
//
//
//    public CustomerDAO(CustomerMapper mapper) {
//        this.mapper = mapper;
//    }
//
//    /**
//     * Records the specified time of login for the user related to the specified customer account.
//     *
//     * @param customerId an ID of a customer account related to the authenticated user.
//     * @param time       a timestamp of successful authentication (in milliseconds since epoch).
//     */
//    public void recordLastLoginTime(int customerId, long time) {
//        mapper.recordLastLoginTime(customerId, time);
//    }
//
//    private Object customerId;
//    long lastLoginTime = mapper.getLastLoginTime(customerId);
//    if (lastLoginTime != null) {
//        Instant instant = Instant.ofEpochMilli(lastLoginTime);
//        ZonedDateTime dateTime = instant.atZone(ZoneId.systemDefault());
//        System.out.println("Last login: " + dateTime);
//        // Example output: "Last login: 2024-04-04T14:34:38.901+02:00[Europe/Berlin]"
//    }
//}
//
//updated version
package com.hmdm.persistence;

import com.hmdm.persistence.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Repository
public class CustomerDAO {

    private final CustomerMapper mapper;

    @Autowired
    public CustomerDAO(CustomerMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Records the specified time of login for the user related to the specified customer account.
     *
     * @param customerId an ID of a customer account related to the authenticated user.
     * @param time       a timestamp of successful authentication (in milliseconds since epoch).
     */
    public void recordLastLoginTime(int customerId, long time) {
        mapper.recordLastLoginTime(customerId, time);
    }

    /**
     * Gets the last login time for the specified customer.
     *
     * @param customerId the ID of the customer
     * @return ZonedDateTime representing the last login time, or null if never logged in
     */
    public ZonedDateTime getLastLoginTime(int customerId) {
        Long lastLoginTime = mapper.getLastLoginTime(customerId);
        if (lastLoginTime != null && lastLoginTime != 0L) {
            return Instant.ofEpochMilli(lastLoginTime)
                    .atZone(ZoneId.systemDefault());
        }
        return null;
    }

    /**
     * Prints the last login time for the specified customer.
     *
     * @param customerId the ID of the customer
     */
    public void printLastLoginTime(int customerId) {
        ZonedDateTime lastLogin = getLastLoginTime(customerId);
        if (lastLogin != null) {
            System.out.println("Last login: " + lastLogin);
        } else {
            System.out.println("No login record found for customer: " + customerId);
        }
    }
}