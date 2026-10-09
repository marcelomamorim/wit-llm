/*
 * @test
 * @summary Tests for ZoneId.ofOffset(String, ZoneOffset)
 * @run main ZoneIdWitupTest_Pf655714ca693
 */

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects;

public class ZoneIdWitupTest_Pf655714ca693 {

    public static void main(String[] args) {
        testOfOffsetWithValidPrefixesAndZeroOffset();
        testOfOffsetWithValidPrefixesAndNonZeroOffset();
        testOfOffsetWithInvalidPrefix();
    }

    static void testOfOffsetWithValidPrefixesAndZeroOffset() {
        ZoneOffset offset = ZoneOffset.ofTotalSeconds(0);
        String[] prefixes = {"GMT", "UTC", "UT"};
        for (String prefix : prefixes) {
            ZoneId zid = ZoneId.ofOffset(prefix, offset);
            assert zid != null : "ZoneId should not be null for prefix: " + prefix;
            assert zid.getId().equals(prefix) : "Expected id: " + prefix + ", but got: " + zid.getId();
        }
    }

    static void testOfOffsetWithValidPrefixesAndNonZeroOffset() {
        ZoneOffset offset = ZoneOffset.ofHours(2);
        String[] prefixes = {"GMT", "UTC", "UT"};
        for (String prefix : prefixes) {
            ZoneId zid = ZoneId.ofOffset(prefix, offset);
            assert zid != null : "ZoneId should not be null for prefix: " + prefix;
            String expectedId = prefix + offset.getId();
            assert zid.getId().equals(expectedId) : "Expected id: " + expectedId + ", but got: " + zid.getId();
        }
    }

    static void testOfOffsetWithInvalidPrefix() {
        try {
            ZoneOffset offset = ZoneOffset.ofHours(1);
            ZoneId.ofOffset("INVALID", offset);
            assert false : "Expected IllegalArgumentException for invalid prefix";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("prefix should be GMT, UTC or UT") : "Unexpected message: " + e.getMessage();
        }
    }
}
