package com.finance.portfolio.manager.service;


import com.finance.portfolio.manager.Constants;
import com.finance.portfolio.manager.factory.KiteFactory;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.User;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import java.util.HashMap;
import java.util.Map;

import static com.finance.portfolio.manager.Constants.FAILED;
import static com.finance.portfolio.manager.Constants.SUCCESS;

@Service
@Log4j2
public class AuthenticationService {

    private KiteFactory kiteFactory;

    @Value("${kite.api.secret:''}")
    private String apiSecret;

    public AuthenticationService(KiteFactory kiteFactory) {
        this.kiteFactory = kiteFactory;
    }

    public Map<String, Object> generateSessionToken(String requestToken) {
        Map<String, Object> map = new HashMap<>();
        try {
            User user = kiteFactory.create()
                    .generateSession(requestToken, apiSecret);
            map.put("response", user);
            map.put("status", SUCCESS);
            return map;
        } catch (KiteException e) {
            log.info("Kite API returned Exception: {}", e.getMessage());
            map.put("errorMessage", e.getMessage());
        } catch (Exception e) {
            log.info("Error occurred while generating sessions: {}", e.getMessage());
            map.put("errorMessage", e.getMessage());
        }
        map.put("response", null);
        map.put("status", FAILED);
        return map;
    }

}
