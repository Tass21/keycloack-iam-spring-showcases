package com.tdksoft.demokeycloak.util;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class PhoneNumberValidator implements Predicate<String> {

    public static final String GERMAN_PHONE_PREFIX = "+49";

    @Override
    public boolean test(String phoneNumber) {
        return phoneNumber.startsWith(GERMAN_PHONE_PREFIX) &&
                phoneNumber.length() == 11;
    }
}
