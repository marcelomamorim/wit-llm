/*
 * @test
 * @summary SimpleTimeZone.setDSTSavings rejects non-positive values and stores positive values
 * @run main SimpleTimeZoneWitupTest
 */

import java.util.SimpleTimeZone;

public class SimpleTimeZoneWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void expectIllegalArgument(SimpleTimeZone tz, int value) {
        boolean thrown = false;
        try {
            tz.setDSTSavings(value);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "setDSTSavings(" + value + ") should throw IllegalArgumentException");
    }

    public static void main(String[] args) {
        SimpleTimeZone tz = new SimpleTimeZone(0, "GMT+01:00");

        tz.setDSTSavings(3600000);
        check(tz.getDSTSavings() == 3600000, "positive value 3600000 should be stored");

        tz.setDSTSavings(1);
        check(tz.getDSTSavings() == 1, "minimum positive value 1 should be stored");

        tz.setDSTSavings(Integer.MAX_VALUE);
        check(tz.getDSTSavings() == Integer.MAX_VALUE, "Integer.MAX_VALUE should be stored");

        tz.setDSTSavings(1800000);
        expectIllegalArgument(tz, 0);
        check(tz.getDSTSavings() == 1800000, "value must be unchanged after setDSTSavings(0) rejection");

        expectIllegalArgument(tz, -1);
        check(tz.getDSTSavings() == 1800000, "value must be unchanged after setDSTSavings(-1) rejection");

        expectIllegalArgument(tz, Integer.MIN_VALUE);
        check(tz.getDSTSavings() == 1800000, "value must be unchanged after setDSTSavings(Integer.MIN_VALUE) rejection");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
