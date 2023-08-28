package com.example.demo.app.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.app.appdb.Dao.PasswordGetter;

@Component
public class CheckUserPassword {
    private final PasswordEncoder passwordEncoder;
    private final PasswordGetter passwordDAO;

    @Autowired
    public CheckUserPassword(PasswordEncoder passwordEncoder, PasswordGetter passwordDAO) {
        this.passwordEncoder = passwordEncoder;
        this.passwordDAO = passwordDAO;
    }

    public Boolean check(JsonRequestModel data) {
        String encodedPassword = passwordDAO.Find(data.getUser());
        return passwordEncoder.matches(data.getPassword(), encodedPassword);
    }
}
