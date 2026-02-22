package com.finance.portfolio.manager.controllers;


import com.finance.portfolio.manager.factory.KiteFactory;
import com.finance.portfolio.manager.service.AuthenticationService;
import com.zerodhatech.kiteconnect.KiteConnect;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

import static com.finance.portfolio.manager.Constants.SUCCESS;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Log4j2
public class AuthenticationController {

    private AuthenticationService authService;
    private KiteFactory kiteFactory;

    @GetMapping("v1/zerodha/login")
    public void login(HttpServletResponse response) {
        try {
            KiteConnect kiteConnect = kiteFactory.create();
            response.sendRedirect(kiteConnect.getLoginURL());
        } catch (IOException e) {
            log.error("Something went wrong while logging-in, error: {}", e.getMessage());
        }
    }
    @GetMapping("v1/zerodha/callback")
    public ResponseEntity<Map<String, Object>> generateAccessToken(@RequestParam("request_token") String requestParam) {
        log.info("Request Token Retrieved: {}", requestParam);
        Map<String, Object> response = authService.generateSessionToken(requestParam);
        return SUCCESS.equalsIgnoreCase(String.valueOf(response.get("status"))) ? ResponseEntity.ok().body(response)
                : ResponseEntity.internalServerError().body(response) ;
    }


}
