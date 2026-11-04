/*
 * @test
 * @summary Test for ZoneOffset.ofHours(int)
 * @run main ZoneOffsetWitupTest
 */

import java.time.ZoneOffset;

public class ZoneOffsetWitupTest {

    public static void main(String[] args) {
        testOfHoursZero();
        testOfHoursPositive();
        testOfHoursNegative();
        testOfHoursLargePositive();
        testOfHoursLargeNegative();
    }

    static void testOfHoursZero() {
        ZoneOffset offset = ZoneOffset.ofHours(0);
        assert offset.getTotalSeconds() == 0 : "Expected 0 seconds for offset 0";
    }

    static void testOfHoursPositive() {
        ZoneOffset offset = ZoneOffset.ofHours(5);
        assert offset.getTotalSeconds() == 5 * 3600 : "Expected 18000 seconds for offset +5";
    }

    static void testOfHoursNegative() {
        ZoneOffset offset = ZoneOffset.ofHours(-3);
        assert offset.getTotalSeconds() == -3 * 3600 : "Expected -10800 seconds for offset -3";
    }

    static void testOfHoursLargePositive() {
        ZoneOffset offset = ZoneOffset.ofHours(14);
        assert offset.getTotalSeconds() == 14 * 3600 : "Expected 50400 seconds for offset +14";
    }

    static void testOfHoursLargeNegative() {
        ZoneOffset offset = ZoneOffset.ofHours(-12);
        assert offset.getTotalSeconds() == -12 * 3600 : "Expected -43200 seconds for offset -12";
    }
}
