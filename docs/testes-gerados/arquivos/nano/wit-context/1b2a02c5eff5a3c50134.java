/*
 * @test
 * @summary Tests the constructor ZoneInfo(String, int) for expected behavior.
 * @run main ZoneInfoWitupTest
 */

import sun.util.calendar.ZoneInfo;

public class ZoneInfoWitupTest {

    public static void main(String[] args) {
        testNullID();
        testValidIDAndOffset();
    }

    private static void testNullID() {
        try {
            new ZoneInfo(null, 3600000);
            System.out.println("testNullID: Failed - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testNullID: Passed");
        } catch (Exception e) {
            System.out.println("testNullID: Failed - Unexpected exception: " + e);
        }
    }

    private static void testValidIDAndOffset() {
        try {
            String id = "TestZone";
            int offset = 2 * 3600000; // 2 hours in milliseconds
            ZoneInfo zone = new ZoneInfo(id, offset);
            if (zone != null && zone.getID().equals(id) && zone.getRawOffset() == offset) {
                System.out.println("testValidIDAndOffset: Passed");
            } else {
                System.out.println("testValidIDAndOffset: Failed - Incorrect zone info");
            }
        } catch (Exception e) {
            System.out.println("testValidIDAndOffset: Failed - Exception: " + e);
        }
    }
}
