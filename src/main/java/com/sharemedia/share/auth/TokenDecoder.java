package com.sharemedia.share.auth;

import com.fasterxml.jackson.core.JsonParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class TokenDecoder {

    public static String decodeToken(String authHeader) {
        String token = authHeader.replace("Token ", "");
        String[] chunks = token.split("\\.");

        Base64.Decoder decoder = Base64.getUrlDecoder();

        return new String(decoder.decode(chunks[1]));
    }

    public static Map<String, Object> tokenToMap(String authHeader) {
        return new Gson().fromJson(
                decodeToken(authHeader), new TypeToken<HashMap<String, Object>>() {}.getType()
        );
    }
}
