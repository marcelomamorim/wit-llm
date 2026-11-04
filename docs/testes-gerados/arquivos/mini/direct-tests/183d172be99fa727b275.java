/*
 * @test
 * @summary Basic tests for javax.net.ssl.SNIHostName(String) constructor
 * @run main javax.net.ssl.SNIHostNameWitupTest
 */

import javax.net.ssl.SNIHostName;
import javax.net.ssl.StandardConstants;
import java.net.IDN;
import java.nio.charset.StandardCharsets;

public class SNIHostNameWitupTest {
    public static void main(String[] args) throws Exception {
        testValidHostName();
        testValidIDNHostName();
        testNullHostName();
        testEmptyHostName();
        testHostNameWithTrailingDot();
    }

    private static void testValidHostName() {
        String hostname = "example.com";
        SNIHostName sni = new SNIHostName(hostname);
        // The hostname field should be the ASCII form (same as input here)
        if (!sni.getAsciiName().equals(hostname)) {
            throw new AssertionError("ASCII name mismatch for valid hostname");
        }
        if (sni.getType() != StandardConstants.SNI_HOST_NAME) {
            throw new AssertionError("SNI type mismatch");
        }
        byte[] encoded = sni.getEncoded();
        String decoded = new String(encoded, StandardCharsets.US_ASCII);
        if (!decoded.equals(hostname)) {
            throw new AssertionError("Encoded bytes do not match hostname string");
        }
    }

    private static void testValidIDNHostName() {
        // Unicode domain name with accented character
        String unicodeHost = "münich.com";
        SNIHostName sni = new SNIHostName(unicodeHost);
        // The ASCII name should be the punycode form
        String asciiName = sni.getAsciiName();
        String expectedAscii = IDN.toASCII(unicodeHost, IDN.USE_STD3_ASCII_RULES);
        if (!asciiName.equals(expectedAscii)) {
            throw new AssertionError("ASCII name does not match expected punycode");
        }
        // Encoded bytes should match ASCII name bytes
        byte[] encoded = sni.getEncoded();
        String decoded = new String(encoded, StandardCharsets.US_ASCII);
        if (!decoded.equals(expectedAscii)) {
            throw new AssertionError("Encoded bytes do not match ASCII name");
        }
    }

    private static void testNullHostName() {
        try {
            new SNIHostName(null);
            throw new AssertionError("Expected NullPointerException for null hostname");
        } catch (NullPointerException e) {
            // expected
            String msg = e.getMessage();
            if (msg == null || !msg.contains("cannot be null")) {
                throw new AssertionError("NullPointerException message unexpected: " + msg);
            }
        }
    }

    private static void testEmptyHostName() {
        try {
            new SNIHostName("");
            throw new AssertionError("Expected IllegalArgumentException for empty hostname");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testHostNameWithTrailingDot() {
        try {
            new SNIHostName("example.com.");
            throw new AssertionError("Expected IllegalArgumentException for hostname with trailing dot");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
