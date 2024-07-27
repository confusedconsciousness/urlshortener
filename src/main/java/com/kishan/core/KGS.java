package com.kishan.core;

import java.util.stream.IntStream;

/**
 * KGS: Key Generator Service
 * This class provide us with unique key every time it is called
 * We will only be having one instance, so we can ideally
 */
public class KGS {

    private int sequence = 0;
    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final int machineId;

    KGS(int machineId) {
        this.machineId = machineId;
    }

    private String base62(long time, int machineId, int sequence) {
        StringBuilder encodedString = new StringBuilder();
        long modifiedNumber = Long.parseLong(String.format("%d%d%d", time, machineId, sequence));
        while (modifiedNumber > 0) {
            int remainder = (int) (modifiedNumber % 62);
            encodedString.append(BASE62.charAt(remainder));
            modifiedNumber /= 62;
        }
        return encodedString.reverse().toString();
    }

    public String getUniqueKey() {
        sequence += 1; // this might overflow at some point in time
        return base62(System.currentTimeMillis(), machineId, sequence);
    }

    /**
     * use this for testing the KGS service
     * Here we are providing the sequence number that is ever-increasing
     * Notice that it might overflow after some time
     *
     * @param args
     */
    public static void main(String[] args) {
        KGS kgs = new KGS(1);
        IntStream.range(0, 1000).forEach(i -> System.out.println(kgs.getUniqueKey()));
        System.out.println(kgs.getUniqueKey());
    }

}
