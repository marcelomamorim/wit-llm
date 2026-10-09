/*
 * @test
 * @summary Test java.time.ZoneId.ofOffset(String, ZoneOffset) method
 * @run main ZoneIdWitupTest_P2f66f3ffbfbe
 */

import java.time.ZoneId;
import java.time.ZoneOffset;

public class ZoneIdWitupTest_P2f66f3ffbfbe {
    public static void main(String[] args) {
        testNullPrefix();
        testNullOffset();
        testEmptyPrefixReturnsOffset();
        testInvalidPrefixThrows();
        testValidPrefixesWithZeroOffset();
        testValidPrefixesWithNonZeroOffset();
    }

    private static void testNullPrefix() {
        try {
            ZoneId.ofOffset(null, ZoneOffset.UTC);
            throw new RuntimeException("Expected NullPointerException for null prefix");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testNullOffset() {
        try {
            ZoneId.ofOffset("GMT", null);
            throw new RuntimeException("Expected NullPointerException for null offset");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testEmptyPrefixReturnsOffset() {
        ZoneOffset offset = ZoneOffset.ofHours(2);
        ZoneId result = ZoneId.ofOffset("", offset);
        if (result != offset) {
            throw new RuntimeException("Expected returned ZoneId to be the same ZoneOffset instance when prefix is empty");
        }
    }

    private static void testInvalidPrefixThrows() {
        try {
            ZoneId.ofOffset("ABC", ZoneOffset.UTC);
            throw new RuntimeException("Expected IllegalArgumentException for invalid prefix");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testValidPrefixesWithZeroOffset() {
        String[] prefixes = {"GMT", "UTC", "UT"};
        for (String prefix : prefixes) {
            ZoneId zid = ZoneId.ofOffset(prefix, ZoneOffset.UTC);
            String id = zid.getId();
            if (!id.equals(prefix)) {
                throw new RuntimeException("Expected ZoneId id to equal prefix when offset is zero, got: " + id);
            }
        }
    }

    private static void testValidPrefixesWithNonZeroOffset() {
        String[] prefixes = {"GMT", "UTC", "UT"};
        ZoneOffset offset = ZoneOffset.ofHoursMinutes(5, 30); // +05:30
        for (String prefix : prefixes) {
            ZoneId zid = ZoneId.ofOffset(prefix, offset);
            String expectedId = prefix + offset.getId();
            String actualId = zid.getId();
            if (!expectedId.equals(actualId)) {
                throw new RuntimeException("Expected ZoneId id to be '" + expectedId + "' but was '" + actualId + "'");
            }
        }
    }
}
