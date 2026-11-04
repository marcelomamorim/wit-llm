/*
 * @test
 * @summary Test ZoneInfo(String ID, int rawOffset) constructor for null ID and valid ID
 * @run main ZoneInfoWitupTest
 */

public class ZoneInfoWitupTest {
    public static void main(String[] args) {
        testConstructorWithNullID();
        testConstructorWithValidID();
        System.out.println("All tests passed.");
    }

    private static void testConstructorWithNullID() {
        try {
            new sun.util.calendar.ZoneInfo(null, 0);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testConstructorWithValidID() {
        String id = "GMT";
        int rawOffset = 3600000; // +1 hour
        sun.util.calendar.ZoneInfo zoneInfo = new sun.util.calendar.ZoneInfo(id, rawOffset);
        if (!id.equals(zoneInfo.getID())) {
            throw new RuntimeException("ZoneInfo ID mismatch: expected " + id + " but got " + zoneInfo.getID());
        }
        if (zoneInfo.getRawOffset() != rawOffset) {
            throw new RuntimeException("ZoneInfo rawOffset mismatch: expected " + rawOffset + " but got " + zoneInfo.getRawOffset());
        }
    }
}
