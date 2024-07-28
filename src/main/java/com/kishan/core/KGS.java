package com.kishan.core;

import java.util.stream.IntStream;

/**
 * KGS: Key Generator Service This class provide us with unique key every time it is called We will
 * only be having one instance, so we can ideally
 */

public class KGS {
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

  public String getUniqueKey() {
    // this is done to prevent overflowing
    // in one millisecond we'll support at max 10,000 unique ids.
    sequence = (sequence + 1) % 10000;
    // inclusion of datacenterId, and machineId will definitely increase the unique key size by 1,
    // but it can now support more than ever keys
    return base62(System.currentTimeMillis(), machineId, datacenterId, sequence);
  }

  public static void main(String[] args) {
    KGS kgs = new KGS();
    IntStream.range(0,1000).forEach(i -> System.out.println(kgs.getUniqueKey()));
  }
}
