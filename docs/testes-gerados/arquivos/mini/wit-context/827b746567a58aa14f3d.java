/*
 * @test
 * @summary Test java.time.ZoneId.ofOffset(String prefix, ZoneOffset offset) for valid and invalid inputs
 * @run main ZoneIdWitupTest
 */

import java.time.ZoneId;
import java.time.ZoneOffset;

public class ZoneIdWitupTest {
    public static void main(String[] args) {
        testNullPrefix();
        testNullOffset();
        testEmptyPrefixReturnsOffset();
        testInvalidPrefixThrows();
        testValidPrefixesWithOffset();
    }

    private static void testNullPrefix() {
        try {
            ZoneId.ofOffset(null, ZoneOffset.UTC);
            throw new RuntimeException("Expected NullPointerException for null prefix");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testNullOffset() {
        try {
            ZoneId.ofOffset("UTC", null);
            throw new RuntimeException("Expected NullPointerException for null offset");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testEmptyPrefixReturnsOffset() {
        ZoneId result = ZoneId.ofOffset("", ZoneOffset.ofHours(2));
        if (!result.equals(ZoneOffset.ofHours(2))) {
            throw new RuntimeException("Expected ZoneOffset instance when prefix is empty");
        }
    }

    private static void testInvalidPrefixThrows() {
        try {
            ZoneId.ofOffset("ABC", ZoneOffset.UTC);
            throw new RuntimeException("Expected IllegalArgumentException for invalid prefix");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testValidPrefixesWithOffset() {
        ZoneOffset offset = ZoneOffset.ofHoursMinutes(5, 30);
        ZoneId zid1 = ZoneId.ofOffset("UTC", offset);
        ZoneId zid2 = ZoneId.ofOffset("GMT", offset);
        ZoneId zid3 = ZoneId.ofOffset("UT", offset);

        if (!zid1.getId().equals("UTC+05:30")) {
            throw new RuntimeException("Unexpected ZoneId id for UTC prefix");
        }
        if (!zid2.getId().equals("GMT+05:30")) {
            throw new RuntimeException("Unexpected ZoneId id for GMT prefix");
        }
        if (!zid3.getId().equals("UT+05:30")) {
            throw new RuntimeException("Unexpected ZoneId id for UT prefix");
        }

        // Test zero offset with valid prefix returns prefix only
        ZoneId zidZero = ZoneId.ofOffset("UTC", ZoneOffset.UTC);
        if (!zidZero.getId().equals("UTC")) {
            throw new RuntimeException("Expected prefix only when offset is zero");
        }
    }
}
