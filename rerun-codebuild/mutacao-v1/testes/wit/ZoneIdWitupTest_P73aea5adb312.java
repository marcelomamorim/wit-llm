/* @test    @summary Test of ZoneId.ofOffset(String, ZoneOffset) for exception paths and behavior    @run main ZoneIdWitupTest_P73aea5adb312 */

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects;

public class ZoneIdWitupTest_P73aea5adb312 {

    public static void main(String[] args) {
        testNullPrefix();
        testNullOffset();
        testInvalidPrefix();
        testValidPrefixWithZeroOffset();
        testValidPrefixWithNonZeroOffset();
    }

    static void testNullPrefix() {
        try {
            ZoneId.ofOffset(null, ZoneOffset.UTC);
            throw new AssertionError("Expected NullPointerException for null prefix");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    static void testNullOffset() {
        try {
            ZoneId.ofOffset("GMT", null);
            throw new AssertionError("Expected NullPointerException for null offset");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    static void testInvalidPrefix() {
        try {
            ZoneId.ofOffset("InvalidPrefix", ZoneOffset.UTC);
            throw new AssertionError("Expected IllegalArgumentException for invalid prefix");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    static void testValidPrefixWithZeroOffset() {
        ZoneOffset zeroOffset = ZoneOffset.UTC;
        ZoneId zid = ZoneId.ofOffset("UTC", zeroOffset);
        if (zid == null) {
            throw new AssertionError("ZoneId should not be null");
        }
        if (!zid.getId().equals("UTC")) {
            throw new AssertionError("Expected ID 'UTC', got: " + zid.getId());
        }
    }

    static void testValidPrefixWithNonZeroOffset() {
        ZoneOffset offsetPlus2 = ZoneOffset.ofHours(2);
        ZoneId zid = ZoneId.ofOffset("UTC", offsetPlus2);
        if (zid == null) {
            throw new AssertionError("ZoneId should not be null");
        }
        String expectedId = "UTC".concat(offsetPlus2.getId());
        if (!zid.getId().equals(expectedId)) {
            throw new AssertionError("Expected ID '" + expectedId + "', got: " + zid.getId());
        }
    }
}
