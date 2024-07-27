package com.kishan.core;
/**
 * KGS: Key Generator Service This class provide us with unique key every time it is called We will
 * only be having one instance, so we can ideally
 */

public class KGS {

  private int sequence = 0;
  private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
  private final int machineId = 1;

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
}
