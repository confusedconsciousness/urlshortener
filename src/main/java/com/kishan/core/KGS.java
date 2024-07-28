package com.kishan.core;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

/**
 * KGS: Key Generator Service This class provide us with unique key every time it is called We will
 * only be having one instance, so we can ideally
 */

@Slf4j
public class KGS {

    Set<String> seen = new HashSet<>();
    private int sequence = 0;
    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final int machineId = 1;
    private final int datacenterId = 1;

    private String base62(long time, int machineId, int datacenterId, int sequence) {
        StringBuilder encodedString = new StringBuilder();
        long modifiedNumber = Long.parseLong(String.format("%d%d%d%d", time, machineId, datacenterId, sequence));
        while (modifiedNumber > 0) {
            int remainder = (int) (modifiedNumber % 62);
            encodedString.append(BASE62.charAt(remainder));
            modifiedNumber /= 62;
        }
        return encodedString.reverse().toString();
    }

    @SneakyThrows
    public String getUniqueKey() {
        // this is done to prevent overflowing
        // in one millisecond we'll support at max 1000 unique ids.
        sequence = (sequence + 1) % 1000;
        // inclusion of datacenterId, and machineId will definitely increase the unique key size by 1,
        // but it can now support more than ever keys

        // I think there can be duplicates if the processor is able to process more than 1000 sequence in a millisecond
        // In that case things will get wrapped up, and we might see duplicates

        // How to avoid that?
        String uniqueKey = base62(System.currentTimeMillis(), machineId, datacenterId, sequence);
        if (seen.contains(uniqueKey)) {
            // clear the set
            seen.clear();
            // not optimal solution but we have to rate limit things here itself
            // wait for 1ms to get unique keys
            Thread.sleep(1);
            return getUniqueKey();
        }
        try {
            seen.add(uniqueKey);
        } catch (Exception e) {
            // we might end up here if the size of the set has exceeded, in that case simply reset it.
            seen.clear();
            Thread.sleep(1);
        }
        return uniqueKey;
    }
}
