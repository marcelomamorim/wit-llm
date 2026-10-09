/* @test    @summary Test the inDaylightTime method of ZoneInfo for various Date inputs    @run main ZoneInfoWitupTest_Pbc8dbb66f6ca */

import java.util.Date;
import sun.util.calendar.ZoneInfo;

public class ZoneInfoWitupTest_Pbc8dbb66f6ca {

    public static void main(String[] args) {
        testNullInput();
        testBeforeTransitions();
        testWithinTransitions();
        testBeyondTransitionsWithLastRule();
    }

    private static void testNullInput() {
        ZoneInfo zoneInfo = new ZoneInfo();
        boolean exceptionThrown = false;
        try {
            zoneInfo.inDaylightTime(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException for null input");
        }
    }

    private static void testBeforeTransitions() {
        ZoneInfo zoneInfo = new ZoneInfo();
        zoneInfo.transitions = null; // simulate no transitions
        Date date = new Date(0); // epoch time
        boolean result = zoneInfo.inDaylightTime(date);
        if (result != false) {
            throw new AssertionError("Expected false for date before transitions");
        }
    }

    private static void testWithinTransitions() {
        ZoneInfo zoneInfo = new ZoneInfo();
        // mock transitions with a transition indicating DST (mask set)
        zoneInfo.transitions = new long[] { 1000, 0x10 }; // DST mask set
        Date date = new Date(1500);
        boolean result = zoneInfo.inDaylightTime(date);
        if (result != true) {
            throw new AssertionError("Expected true for date within transition (DST)");
        }
        // mock transitions with a transition indicating standard time
        zoneInfo.transitions = new long[] { 1000, 0x00 }; // DST mask not set
        result = zoneInfo.inDaylightTime(date);
        if (result != false) {
            throw new AssertionError("Expected false for date within transition (standard time)");
        }
    }

    private static void testBeyondTransitionsWithLastRule() {
        ZoneInfo zoneInfo = new ZoneInfo();
        zoneInfo.transitions = new long[] { 1000, 0x10 };
        // simulate getLastRule returning a timezone with inDaylightTime returning true
        zoneInfo.getLastRule = () -> new sun.util.calendar.SimpleTimeZone(0, ""){ 
            @Override
            public boolean inDaylightTime(Date date) {
                return true;
            }
        };
        Date date = new Date(2000000000L);
        boolean result = zoneInfo.inDaylightTime(date);
        if (result != true) {
            throw new AssertionError("Expected true for date beyond transition with lastRule");
        }
    }
}
