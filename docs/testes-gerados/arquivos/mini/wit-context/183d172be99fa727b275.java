/*
 * @test
 * @summary Basic tests for javax.net.ssl.SNIHostName(String) constructor
 * @run main SNIHostNameWitupTest
 */

import javax.net.ssl.SNIHostName;
import java.net.IDN;
import java.nio.charset.StandardCharsets;

public class SNIHostNameWitupTest {
    public static void main(String[] args) {
        testValidHostName();
        testNullHostName();
        testEmptyHostName();
        testHostNameWithTrailingDot();
        testHostNameWithInvalidChars();
    }

    private static void testValidHostName() {
        // Valid ASCII hostname
        SNIHostName sni1 = new SNIHostName("example.com");
        if (!"example.com".equals(sni1.getAsciiName())) {
            throw new RuntimeException("Failed: ASCII hostname mismatch");
        }

        // Valid IDN hostname (Unicode), should be converted to ASCII ACE
        String unicodeHost = "münich.com";
        String asciiHost = IDN.toASCII(unicodeHost, IDN.USE_STD3_ASCII_RULES);
        SNIHostName sni2 = new SNIHostName(unicodeHost);
        if (!asciiHost.equals(sni2.getAsciiName())) {
            throw new RuntimeException("Failed: IDN hostname conversion mismatch");
        }
    }

    private static void testNullHostName() {
        try {
            new SNIHostName(null);
            throw new RuntimeException("Failed: NullPointerException expected for null hostname");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testEmptyHostName() {
        try {
            new SNIHostName("");
            throw new RuntimeException("Failed: IllegalArgumentException expected for empty hostname");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    private static void testHostNameWithTrailingDot() {
        try {
            new SNIHostName("example.com.");
            throw new RuntimeException("Failed: IllegalArgumentException expected for hostname with trailing dot");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    private static void testHostNameWithInvalidChars() {
        try {
            new SNIHostName("exa mple.com"); // space is invalid
            throw new RuntimeException("Failed: IllegalArgumentException expected for hostname with invalid chars");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}
