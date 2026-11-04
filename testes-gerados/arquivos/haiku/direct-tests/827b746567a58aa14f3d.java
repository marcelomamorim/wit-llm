/*
 * @test
 * @summary Verifies ZoneId.ofOffset prefix handling, null checks, and observable ids
 * @run main ZoneIdWitupTest
 */

import java.time.ZoneId;
import java.time.ZoneOffset;

public class ZoneIdWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void testEmptyPrefixReturnsOffset() {
        ZoneOffset offset = ZoneOffset.ofHours(2);
        ZoneId result = ZoneId.ofOffset("", offset);
        check(result == offset, "empty prefix should return the offset instance itself");
        check(offset.equals(result), "empty prefix should return an equal offset");
    }

    static void testNullPrefixThrowsNpe() {
        boolean thrown = false;
        try {
            ZoneId.ofOffset(null, ZoneOffset.UTC);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null prefix should throw NullPointerException");
    }

    static void testNullOffsetThrowsNpe() {
        boolean thrown = false;
        try {
            ZoneId.ofOffset("GMT", null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null offset should throw NullPointerException");
    }

    static void testInvalidPrefixThrowsIae() {
        boolean thrown = false;
        try {
            ZoneId.ofOffset("XYZ", ZoneOffset.UTC);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "invalid prefix should throw IllegalArgumentException");
    }

    static void testZeroOffsetKeepsPrefixAsId() {
        ZoneId result = ZoneId.ofOffset("UTC", ZoneOffset.UTC);
        check("UTC".equals(result.getId()), "UTC prefix with zero offset should keep id UTC, was " + result.getId());
        check(!(result instanceof ZoneOffset), "prefixed zone should not be a ZoneOffset");
    }

    static void testPositiveOffsetAppendedToPrefix() {
        ZoneId result = ZoneId.ofOffset("GMT", ZoneOffset.ofHours(2));
        check("GMT+02:00".equals(result.getId()), "expected GMT+02:00, was " + result.getId());
    }

    static void testNegativeOffsetAppendedToPrefix() {
        ZoneId result = ZoneId.ofOffset("UT", ZoneOffset.ofHoursMinutes(-5, -30));
        check("UT-05:30".equals(result.getId()), "expected UT-05:30, was " + result.getId());
    }

    static void testUtcPrefixWithOffset() {
        ZoneId result = ZoneId.ofOffset("UTC", ZoneOffset.ofHours(1));
        check("UTC+01:00".equals(result.getId()), "expected UTC+01:00, was " + result.getId());
    }

    public static void main(String[] args) {
        testEmptyPrefixReturnsOffset();
        testNullPrefixThrowsNpe();
        testNullOffsetThrowsNpe();
        testInvalidPrefixThrowsIae();
        testZeroOffsetKeepsPrefixAsId();
        testPositiveOffsetAppendedToPrefix();
        testNegativeOffsetAppendedToPrefix();
        testUtcPrefixWithOffset();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
