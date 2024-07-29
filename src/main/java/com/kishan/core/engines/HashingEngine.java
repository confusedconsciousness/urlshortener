package com.kishan.core.engines;

import com.kishan.db.DB;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * This class is responsible to manage collisions and provide unique key
 */

@Slf4j
public class HashingEngine extends Engine {

    @Override
    public String getUniqueKey(String url) {
        // only consider the initial 7 characters to be used as short key
        String hash = MD5(url).substring(0, 7);
        while (DB.cache.containsKey(hash)) {
            // this mean a collision has occurred, append some random string
            log.warn("Collision detected for url: {}", url);
            hash = MD5(hash + UUID.randomUUID()).substring(0, 7);
        }
        // if we are here no collisions found, return it
        return hash;
    }


    public static String MD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

