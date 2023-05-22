package com.javarush.jira.profile;

import com.javarush.jira.common.util.JsonUtil;
import com.javarush.jira.login.Role;
import com.javarush.jira.login.User;
import org.checkerframework.checker.units.qual.A;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ProfileTestData {
    public static final long ADMIN_ID = 2;
    public static final String ADMIN_MAIL = "admin@gmail.com";

    public static User createUser() {
        User admin = new User(ADMIN_ID, ADMIN_MAIL, "adminPassword", "adminFirstName", "adminLastName",
                "adminDisplayName", Role.ADMIN, Role.DEV);
        return admin;
    }

    public static ProfileTo createProfileTo() {
        return new ProfileTo(ADMIN_ID, Collections.emptySet(), Collections.emptySet());
    }

}
