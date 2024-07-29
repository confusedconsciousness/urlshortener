package com.kishan.core.engines;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * KGS: Key Generator Service This class provide us with unique key every time it is called We will
 * only be having one instance.
 */

@Slf4j
public class KeyGeneratorEngine extends Engine {
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

    @Override
    @SneakyThrows
    public String getUniqueKey(String url) {
        // this is done to prevent overflowing
        // in one millisecond we'll support at max 10000 unique ids.
        sequence = (sequence + 1) % 10000;
        // inclusion of datacenterId, and machineId will definitely increase the unique key size by 1,
        // but it can now support more than ever keys

        // I think there can be duplicates if the processor is able to process more than 10,000 sequence in a millisecond
        // In that case things will get wrapped up, and we might see duplicates

        // How to avoid that? That will require us to implement thread safe fixed size hashset, which is out of scope
        return base62(System.currentTimeMillis(), machineId, datacenterId, sequence);
    }
}
