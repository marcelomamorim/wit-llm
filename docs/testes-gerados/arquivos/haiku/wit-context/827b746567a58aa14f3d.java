/*
 * @test
 * @summary Witup-derived checks for ZoneId.ofOffset prefix and offset validation
 * @run main ZoneIdWitupTest
 */

public class ZoneIdWitupTest {

    public static void main(String[] args) {
        testNullPrefixThrowsNpe();
        testNullOffsetThrowsNpe();
        testInvalidPrefixThrowsIae();
        testLowercasePrefixThrowsIae();
        testEmptyPrefixReturnsOffset();
        testUtcPrefixWithZeroOffsetKeepsId();
        testGmtPrefixWithOffsetAppendsOffsetId();
        testUtPrefixWithNegativeOffsetAppendsOffsetId();
        testGmtPrefixWithZeroOffsetKeepsPrefixOnly();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullPrefixThrowsNpe() {
        try {
            ZoneId.ofOffset(null, ZoneOffset.ofHours(1));
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for null prefix");
    }

    static void testNullOffsetThrowsNpe() {
        try {
            ZoneId.ofOffset("GMT", null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for null offset");
    }

    static void testInvalidPrefixThrowsIae() {
        try {
            ZoneId.ofOffset("XYZ", ZoneOffset.ofHours(1));
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for prefix XYZ");
    }

    static void testLowercasePrefixThrowsIae() {
        try {
            ZoneId.ofOffset("utc", ZoneOffset.UTC);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for prefix utc");
    }

    static void testEmptyPrefixReturnsOffset() {
        ZoneOffset offset = ZoneOffset.ofHours(2);
        ZoneId result = ZoneId.ofOffset("", offset);
        check(offset.equals(result), "empty prefix should return the offset itself");
    }

    static void testUtcPrefixWithZeroOffsetKeepsId() {
        ZoneId result = ZoneId.ofOffset("UTC", ZoneOffset.UTC);
        check("UTC".equals(result.getId()), "expected id UTC but was " + result.getId());
    }

    static void testGmtPrefixWithOffsetAppendsOffsetId() {
        ZoneId result = ZoneId.ofOffset("GMT", ZoneOffset.ofHours(2));
        check("GMT+02:00".equals(result.getId()), "expected id GMT+02:00 but was " + result.getId());
    }

    static void testUtPrefixWithNegativeOffsetAppendsOffsetId() {
        ZoneId result = ZoneId.ofOffset("UT", ZoneOffset.ofHours(-5));
        check("UT-05:00".equals(result.getId()), "expected id UT-05:00 but was " + result.getId());
    }

    static void testGmtPrefixWithZeroOffsetKeepsPrefixOnly() {
        ZoneId result = ZoneId.ofOffset("GMT", ZoneOffset.UTC);
        check("GMT".equals(result.getId()), "expected id GMT but was " + result.getId());
    }
}
