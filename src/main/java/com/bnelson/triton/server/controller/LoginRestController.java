package com.bnelson.triton.server.controller;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/rest/security")
public class LoginRestController {

    //TODO provide this via a yaml configuration
    private final Map<String, String> users;

    public LoginRestController() {
        users = new HashMap<>();
        users.put("brnelsons","123");
    }

    @GetMapping("login")
    public ResponseEntity<Boolean> login(String username, String password){
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache())
                .body(users.containsKey(username) && users.get(username).equals(password));
    }

}
