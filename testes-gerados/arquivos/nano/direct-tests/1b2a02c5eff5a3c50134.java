/* @test    @summary Test constructor ZoneInfo(String, int) for correct initialization and behavior    @run main ZoneInfoWitupTest */

import sun.util.calendar.ZoneInfo;

public class ZoneInfoWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidParameters();
        testConstructorWithZeroOffset();
        testConstructorWithNegativeOffset();
        testConstructorWithLargeOffset();
    }

    private static void testConstructorWithValidParameters() {
        String id = "TestZone";
        int offset = 3600000; // 1 hour in milliseconds
        ZoneInfo zone = new ZoneInfo(id, offset);
        assert zone != null : "ZoneInfo instance should not be null";
        assert zone.getID().equals(id) : "ID should match the input";
        assert zone.getRawOffset() == offset : "Raw offset should match the input";
    }

    private static void testConstructorWithZeroOffset() {
        String id = "ZeroOffsetZone";
        int offset = 0;
        ZoneInfo zone = new ZoneInfo(id, offset);
        assert zone != null : "ZoneInfo instance should not be null";
        assert zone.getID().equals(id) : "ID should match the input";
        assert zone.getRawOffset() == offset : "Raw offset should be zero";
    }

    private static void testConstructorWithNegativeOffset() {
        String id = "NegativeOffsetZone";
        int offset = -18000000; // -5 hours in milliseconds
        ZoneInfo zone = new ZoneInfo(id, offset);
        assert zone != null : "ZoneInfo instance should not be null";
        assert zone.getID().equals(id) : "ID should match the input";
        assert zone.getRawOffset() == offset : "Raw offset should match the negative input";
    }

    private static void testConstructorWithLargeOffset() {
        String id = "LargeOffsetZone";
        int offset = 14 * 60 * 60 * 1000; // 14 hours in milliseconds
        ZoneInfo zone = new ZoneInfo(id, offset);
        assert zone != null : "ZoneInfo instance should not be null";
        assert zone.getID().equals(id) : "ID should match the input";
        assert zone.getRawOffset() == offset : "Raw offset should match the large input";
    }
}
