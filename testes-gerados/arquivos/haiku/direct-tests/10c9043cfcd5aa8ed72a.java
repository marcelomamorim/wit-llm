/*
 * @test
 * @summary Verify FtpURLConnection.setRequestProperty accepts the 'type' values i, a, d (case-insensitive) and rejects others
 * @modules java.base/sun.net.www.protocol.ftp
 * @run main FtpURLConnectionWitupTest
 */

import java.net.URL;
import sun.net.www.protocol.ftp.FtpURLConnection;

public class FtpURLConnectionWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static FtpURLConnection newConnection() throws Exception {
        URL url = new URL("ftp://example.com/pub/file.txt");
        return new FtpURLConnection(url);
    }

    static boolean throwsIllegalArgument(FtpURLConnection c, String key, String value) {
        try {
            c.setRequestProperty(key, value);
            return false;
        } catch (IllegalArgumentException e) {
            return true;
        }
    }

    static void testValidTypeValuesDoNotThrow() throws Exception {
        String[] valid = {"i", "a", "d", "I", "A", "D"};
        for (String v : valid) {
            FtpURLConnection c = newConnection();
            boolean threw = false;
            try {
                c.setRequestProperty("type", v);
            } catch (IllegalArgumentException e) {
                threw = true;
            }
            check(!threw, "type=" + v + " should be accepted");
        }
    }

    static void testInvalidTypeValuesThrow() throws Exception {
        String[] invalid = {"x", "", "ii", "binary", " i"};
        for (String v : invalid) {
            FtpURLConnection c = newConnection();
            check(throwsIllegalArgument(c, "type", v),
                  "type=\"" + v + "\" should throw IllegalArgumentException");
        }
    }

    static void testNullTypeValueThrows() throws Exception {
        FtpURLConnection c = newConnection();
        check(throwsIllegalArgument(c, "type", null),
              "type=null should throw IllegalArgumentException");
    }

    static void testOtherKeysIgnoreValue() throws Exception {
        FtpURLConnection c = newConnection();
        boolean threw = false;
        try {
            c.setRequestProperty("User-Agent", "x");
            c.setRequestProperty("type", "i");
            c.setRequestProperty("Accept", "anything");
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check(!threw, "non-type keys must not be validated against type values");
    }

    static void testTypeKeyIsCaseSensitive() throws Exception {
        FtpURLConnection c = newConnection();
        boolean threw = false;
        try {
            c.setRequestProperty("TYPE", "x");
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check(!threw, "key 'TYPE' does not equal 'type' and must not be validated");
    }

    public static void main(String[] args) throws Exception {
        testValidTypeValuesDoNotThrow();
        testInvalidTypeValuesThrow();
        testNullTypeValueThrows();
        testOtherKeysIgnoreValue();
        testTypeKeyIsCaseSensitive();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
