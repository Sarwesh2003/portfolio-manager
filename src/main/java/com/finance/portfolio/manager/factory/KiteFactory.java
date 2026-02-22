package com.finance.portfolio.manager.factory;


import com.zerodhatech.kiteconnect.KiteConnect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KiteFactory {
    @Value("${kite.api.key:''}")
    private String apiKey;

    public KiteConnect create(String accessToken) {
        KiteConnect kiteConnect = new KiteConnect(apiKey);
        kiteConnect.setAccessToken(accessToken);
        return kiteConnect;
    }

    public KiteConnect create() {
        return new KiteConnect(apiKey);
    }

}
