/*
 * @test
 * @summary Test for sun.util.calendar.ZoneInfo.inDaylightTime(Date)
 * @run main ZoneInfoWitupTest_P5bb120ff2554
 */

import sun.util.calendar.ZoneInfo;
import java.util.Date;

public class ZoneInfoWitupTest_P5bb120ff2554 {

    public static void main(String[] args) {
        testInDaylightTimeWithNull();
        testInDaylightTimeWithTransitionsNull();
        testInDaylightTimeBeforeTransitions();
        testInDaylightTimeWithinTransitions();
        testInDaylightTimeBeyondTransitionsWithLastRule();
    }

    static void testInDaylightTimeWithNull() {
        ZoneInfo zone = new ZoneInfo();
        try {
            zone.inDaylightTime(null);
            System.out.println("FAIL: Expected NullPointerException for null date");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    static void testInDaylightTimeWithTransitionsNull() {
        ZoneInfo zone = new ZoneInfo();
        zone.transitions = null; // forcibly set to null
        Date date = new Date();
        boolean result = zone.inDaylightTime(date);
        if (result == false) {
            System.out.println("PASS: inDaylightTime returns false when transitions is null");
        } else {
            System.out.println("FAIL: inDaylightTime should return false when transitions is null");
        }
    }

    static void testInDaylightTimeBeforeTransitions() {
        ZoneInfo zone = new ZoneInfo();
        zone.transitions = new long[0]; // empty array
        Date date = new Date(0); // epoch start
        boolean result = zone.inDaylightTime(date);
        if (result == false) {
            System.out.println("PASS: inDaylightTime returns false for date before transitions");
        } else {
            System.out.println("FAIL: inDaylightTime should return false for date before transitions");
        }
    }

    static void testInDaylightTimeWithinTransitions() {
        ZoneInfo zone = new ZoneInfo();
        // set transitions with a dummy transition indicating DST
        long transitionTime = 1625097600000L; // July 1, 2021
        zone.transitions = new long[] { transitionTime | 0x10 }; // DST bit set
        Date date = new Date(transitionTime);
        boolean result = zone.inDaylightTime(date);
        if (result == true) {
            System.out.println("PASS: inDaylightTime returns true for date within transitions");
        } else {
            System.out.println("FAIL: inDaylightTime should return true for date within transitions");
        }
    }

    static void testInDaylightTimeBeyondTransitionsWithLastRule() {
        ZoneInfo zone = new ZoneInfo();
        zone.transitions = new long[] { 1625097600000L }; // some transition
        // set getLastRule to return a SimpleTimeZone with inDaylightTime
        zone.getLastRule = () -> {
            return new java.util.SimpleTimeZone(0, "") {
                @Override
                public boolean inDaylightTime(Date date) {
                    return true;
                }
            };
        };
        Date date = new Date(20000L); // beyond transition
        boolean result = zone.inDaylightTime(date);
        if (result == true) {
            System.out.println("PASS: inDaylightTime returns true when beyond transition and getLastRule indicates DST");
        } else {
            System.out.println("FAIL: inDaylightTime should return true when beyond transition and getLastRule indicates DST");
        }
    }
}
