package com.klef.fsd.sdp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RazorpayConfig {

    @Value("${razorpay.secret}")
    private String secret;

    public String getSecret() {
        return secret;
    }
}