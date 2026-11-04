/*
 * @test
 * @summary Test ZoneInfo constructor with ID and rawOffset
 * @run main ZoneInfoWitupTest
 */

public class ZoneInfoWitupTest {
    public static void main(String[] args) {
        // Test with normal ID and rawOffset
        sun.util.calendar.ZoneInfo z1 = new sun.util.calendar.ZoneInfo("GMT", 0);
        if (!"GMT".equals(z1.getID())) {
            throw new RuntimeException("Expected ID GMT but got " + z1.getID());
        }
        if (z1.getRawOffset() != 0) {
            throw new RuntimeException("Expected rawOffset 0 but got " + z1.getRawOffset());
        }

        // Test with non-zero rawOffset
        int offset = 3600000; // +1 hour
        sun.util.calendar.ZoneInfo z2 = new sun.util.calendar.ZoneInfo("TestZone", offset);
        if (!"TestZone".equals(z2.getID())) {
            throw new RuntimeException("Expected ID TestZone but got " + z2.getID());
        }
        if (z2.getRawOffset() != offset) {
            throw new RuntimeException("Expected rawOffset " + offset + " but got " + z2.getRawOffset());
        }

        // Test with empty string ID
        sun.util.calendar.ZoneInfo z3 = new sun.util.calendar.ZoneInfo("", 12345);
        if (!"".equals(z3.getID())) {
            throw new RuntimeException("Expected empty ID but got " + z3.getID());
        }
        if (z3.getRawOffset() != 12345) {
            throw new RuntimeException("Expected rawOffset 12345 but got " + z3.getRawOffset());
        }

        // Test with null ID - should not throw but ID will be null
        sun.util.calendar.ZoneInfo z4 = new sun.util.calendar.ZoneInfo(null, -12345);
        if (z4.getID() != null) {
            throw new RuntimeException("Expected null ID but got " + z4.getID());
        }
        if (z4.getRawOffset() != -12345) {
            throw new RuntimeException("Expected rawOffset -12345 but got " + z4.getRawOffset());
        }
    }
}
