/*
 * @test
 * @summary Verify Charset.isSupported returns true for supported names and aliases, false for unknown names, and agrees with Charset.forName
 * @run main CharsetWitupTest
 */

import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

public class CharsetWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSupportedCanonicalName() {
        check(Charset.isSupported("UTF-8"), "UTF-8 should be supported");
        check(Charset.isSupported("ISO-8859-1"), "ISO-8859-1 should be supported");
        check(Charset.isSupported("US-ASCII"), "US-ASCII should be supported");
    }

    static void testCaseInsensitiveLookup() {
        check(Charset.isSupported("utf-8"), "utf-8 should be supported regardless of case");
        check(Charset.isSupported("Utf-8"), "Utf-8 should be supported regardless of case");
    }

    static void testUnsupportedName() {
        check(!Charset.isSupported("x-witup-nonexistent-charset"),
              "unknown charset name should not be supported");
    }

    static void testConsistencyWithForName() {
        String[] names = { "UTF-8", "US-ASCII", "x-witup-nonexistent-charset" };
        for (String name : names) {
            boolean supported = Charset.isSupported(name);
            boolean found;
            try {
                Charset.forName(name);
                found = true;
            } catch (UnsupportedCharsetException e) {
                found = false;
            }
            check(supported == found,
                  "isSupported and forName disagree for " + name);
        }
    }

    public static void main(String[] args) throws Exception {
        testSupportedCanonicalName();
        testCaseInsensitiveLookup();
        testUnsupportedName();
        testConsistencyWithForName();
    }
}
