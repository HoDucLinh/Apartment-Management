/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apartment_management.configs;

import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author thien
 */
@Configuration
public class ZaloPayConfig {

    public static final Map<String, String> config = new HashMap<String, String>() {
        {
            put("app_id", "2553");
            put("key1", "PcY4iZIKFCIdgZvA6ueMcMHHUbRLYjPL");
            put("endpoint", "https://sb-openapi.zalopay.vn/v2/create");
            put("orderstatus", "https://sb-openapi.zalopay.vn/v2/query");
            put("callback_url",
                    " http://apartment-management/api/zalopay/callback");
            

        }
    };
}
