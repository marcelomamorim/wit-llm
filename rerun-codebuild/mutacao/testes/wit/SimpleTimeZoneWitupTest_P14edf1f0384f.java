/* @test    @summary Test for SimpleTimeZone.setDSTSavings to verify exception on invalid input    @run main SimpleTimeZoneWitupTest_P14edf1f0384f */

import java.util.SimpleTimeZone;

public class SimpleTimeZoneWitupTest_P14edf1f0384f {

    public static void main(String[] args) {
        testSetDSTSavingsWithNegative();
        testSetDSTSavingsWithZero();
        testSetDSTSavingsWithPositive();
    }

    private static void testSetDSTSavingsWithNegative() {
        SimpleTimeZone tz = new SimpleTimeZone(0, "TestTZ");
        try {
            tz.setDSTSavings(-1000);
            System.out.println("FAIL: Expected IllegalArgumentException for negative input");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for negative input");
        }
    }

    private static void testSetDSTSavingsWithZero() {
        SimpleTimeZone tz = new SimpleTimeZone(0, "TestTZ");
        try {
            tz.setDSTSavings(0);
            System.out.println("FAIL: Expected IllegalArgumentException for zero input");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for zero input");
        }
    }

    private static void testSetDSTSavingsWithPositive() {
        SimpleTimeZone tz = new SimpleTimeZone(0, "TestTZ");
        try {
            tz.setDSTSavings(3600000); // 1 hour in milliseconds
            System.out.println("PASS: setDSTSavings accepted positive input");
        } catch (IllegalArgumentException e) {
            System.out.println("FAIL: Unexpected IllegalArgumentException for positive input");
        }
    }
}
