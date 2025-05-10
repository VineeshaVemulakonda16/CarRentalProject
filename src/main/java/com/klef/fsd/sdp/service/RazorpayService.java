package com.klef.fsd.sdp.service;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.RazorpayClient;
import com.razorpay.Utils;

import jakarta.annotation.PostConstruct;

@Service
public class RazorpayService {

    private RazorpayClient razorpayClient;

    @Value("${payment.gateway.secretKey}")
    private String secretKey;

    @Value("${payment.gateway.publishableKey}")
    private String publishableKey;

    @PostConstruct
    public void init() {
        try {
            razorpayClient = new RazorpayClient(publishableKey, secretKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String createOrder(int amount) {
        JSONObject options = new JSONObject();
        options.put("amount", amount * 100);
        options.put("currency", "INR");
        options.put("receipt", "order_rcptid_11");
        options.put("payment_capture", 1);

        try {
            return razorpayClient.orders.create(options).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean verifyPayment(String orderId, String paymentId, String razorpaySignature) {
        String payload = orderId + '|' + paymentId;
        try {
            return Utils.verifySignature(payload, razorpaySignature, secretKey);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}