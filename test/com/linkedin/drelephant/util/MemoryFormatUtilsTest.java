package com.linkedin.drelephant.util;

import java.util.Random;
import junit.framework.TestCase;


/**
 * This class test MemoryFormatUtils/
 *
 * @author yizhou
 */
public class MemoryFormatUtilsTest extends TestCase {
  public void testBytesToString() {
    // Check integer values
    assertEquals("0 B", MemoryFormatUtils.bytesToString(0L));
    assertEquals("1 B", MemoryFormatUtils.bytesToString(1L));
    assertEquals("1 KB", MemoryFormatUtils.bytesToString(1L << 10));
    assertEquals("1 MB", MemoryFormatUtils.bytesToString(1L << 20));
    assertEquals("1 GB", MemoryFormatUtils.bytesToString(1L << 30));
    assertEquals("1,000 GB", MemoryFormatUtils.bytesToString(1000L << 30));
    assertEquals("1 TB", MemoryFormatUtils.bytesToString(1L << 40));
    assertEquals("1,024 TB", MemoryFormatUtils.bytesToString(1L << 50));

    // Check double values
    assertEquals("8.79 KB", MemoryFormatUtils.bytesToString(9000L));
    assertEquals("8.79 MB", MemoryFormatUtils.bytesToString(9000L << 10));
    assertEquals("8.79 GB", MemoryFormatUtils.bytesToString(9000L << 20));
    assertEquals("8.79 TB", MemoryFormatUtils.bytesToString(9000L << 30));
    assertEquals("87,890.62 TB", MemoryFormatUtils.bytesToString((9000L * 10000) << 30));
  }

  public void testStringToBytes() {
    // Null
    assertEquals(0L, MemoryFormatUtils.stringToBytes(null));

    // Integer tests
    String[] units = new String[]{"", "K", "M", "G", "T"};
    long[] multiplers = new long[]{1L, 1L << 10, 1L << 20, 1L << 30, 1L << 40};
    Random random = new Random(System.currentTimeMillis());
    for (int i = 0; i < units.length; i++) {
      String unit = units[i];

      // Int values
      int[] integerNums =
          units.equals("T") ? new int[]{0, random.nextInt(1024), 2048} : new int[]{0, random.nextInt(1024)};
      for (int num : integerNums) {
        long expectedNum = num * multiplers[i];
        assertEquals(expectedNum, MemoryFormatUtils.stringToBytes(num + unit));
        assertEquals(expectedNum, MemoryFormatUtils.stringToBytes(num + " " + unit));
        assertEquals(expectedNum, MemoryFormatUtils.stringToBytes(num + unit + "b"));
        assertEquals(expectedNum, MemoryFormatUtils.stringToBytes(num + unit + "B"));
        assertEquals(expectedNum, MemoryFormatUtils.stringToBytes(num + " " + unit + "B"));
        assertEquals(expectedNum, MemoryFormatUtils.stringToBytes(num + " " + unit + "b"));

        unit = unit.toLowerCase();
        assertEquals(expectedNum, MemoryFormatUtils.stringToBytes(num + unit));
        assertEquals(expectedNum, MemoryFormatUtils.stringToBytes(num + " " + unit));
        assertEquals(expectedNum, MemoryFormatUtils.stringToBytes(num + unit + "b"));
        assertEquals(expectedNum, MemoryFormatUtils.stringToBytes(num + unit + "B"));
        assertEquals(expectedNum, MemoryFormatUtils.stringToBytes(num + " " + unit + "B"));
        assertEquals(expectedNum, MemoryFormatUtils.stringToBytes(num + " " + unit + "b"));
      }

      // Double values, notice that converting double values to long might lose some precision
      double[] doublleNums = new double[]{random.nextDouble(), random.nextDouble() + random.nextInt(1024), 8.79d};
      for (double num : doublleNums) {
        long expectedNum = (long) (num * multiplers[i]);
        assertEquals(expectedNum, MemoryFormatUtils.stringToBytes(num + unit));
        assertEquals(expectedNum, MemoryFormatUtils.stringToBytes(num + " " + unit));
        assertEquals(expectedNum, MemoryFormatUtils.stringToBytes(num + unit + "b"));
        assertEquals(expectedNum, MemoryFormatUtils.stringToBytes(num + unit + "B"));
        assertEquals(expectedNum, MemoryFormatUtils.stringToBytes(num + " " + unit + "B"));
        assertEquals(expectedNum, MemoryFormatUtils.stringToBytes(num + " " + unit + "b"));

        unit = unit.toLowerCase();
        assertEquals(expectedNum, MemoryFormatUtils.stringToBytes(num + unit));
        assertEquals(expectedNum, MemoryFormatUtils.stringToBytes(num + " " + unit));
        assertEquals(expectedNum, MemoryFormatUtils.stringToBytes(num + unit + "b"));
        assertEquals(expectedNum, MemoryFormatUtils.stringToBytes(num + unit + "B"));
        assertEquals(expectedNum, MemoryFormatUtils.stringToBytes(num + " " + unit + "B"));
        assertEquals(expectedNum, MemoryFormatUtils.stringToBytes(num + " " + unit + "b"));
      }
    }
  }
}
