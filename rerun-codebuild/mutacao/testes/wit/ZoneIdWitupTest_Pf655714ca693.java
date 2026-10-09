/*
 * @test
 * @summary Tests for java.time.ZoneId class, focusing on ofOffset method.
 * @run main ZoneIdWitupTest_Pf655714ca693
 */

import java.time.ZoneId;
import java.time.ZoneOffset;

public class ZoneIdWitupTest_Pf655714ca693 {

    public static void main(String[] args) {
        testOfOffset_NullPrefix();
        testOfOffset_NullOffset();
        testOfOffset_InvalidPrefix();
        testOfOffset_ValidPrefixAndOffset();
    }

    static void testOfOffset_NullPrefix() {
        try {
            ZoneId.ofOffset(null, ZoneOffset.UTC);
            System.out.println("FAIL: Expected NullPointerException for null prefix");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown for null prefix");
        }
    }

    static void testOfOffset_NullOffset() {
        try {
            ZoneId.ofOffset("UTC", null);
            System.out.println("FAIL: Expected NullPointerException for null offset");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown for null offset");
        }
    }

    static void testOfOffset_InvalidPrefix() {
        try {
            ZoneId.ofOffset("INVALID", ZoneOffset.UTC);
            System.out.println("FAIL: Expected IllegalArgumentException for invalid prefix");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: IllegalArgumentException thrown for invalid prefix");
        }
    }

    static void testOfOffset_ValidPrefixAndOffset() {
        try {
            ZoneOffset offset = ZoneOffset.ofHours(2);
            ZoneId zid = ZoneId.ofOffset("UTC", offset);
            if (zid != null && zid.getId().equals("UTC+02:00")) {
                System.out.println("PASS: Valid prefix and offset produce expected ZoneId");
            } else {
                System.out.println("FAIL: Unexpected ZoneId result: " + zid);
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
