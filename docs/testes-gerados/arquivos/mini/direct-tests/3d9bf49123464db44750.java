/*
 * @test
 * @summary Test sun.security.x509.IPAddressName(String) constructor for valid and invalid inputs
 * @run main IPAddressNameWitupTest
 */

import sun.security.x509.IPAddressName;
import java.io.IOException;

public class IPAddressNameWitupTest {
    public static void main(String[] args) throws Exception {
        testNullOrEmpty();
        testTrailingSlash();
        testValidIPv4();
        testValidIPv6();
        testInvalidFormat();
    }

    private static void testNullOrEmpty() {
        try {
            new IPAddressName(null);
            throw new RuntimeException("Expected IOException for null input");
        } catch (IOException e) {
            if (!"IPAddress cannot be null or empty".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for null input: " + e.getMessage());
            }
        }

        try {
            new IPAddressName("");
            throw new RuntimeException("Expected IOException for empty input");
        } catch (IOException e) {
            if (!"IPAddress cannot be null or empty".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message for empty input: " + e.getMessage());
            }
        }
    }

    private static void testTrailingSlash() {
        try {
            new IPAddressName("192.168.1.1/");
            throw new RuntimeException("Expected IOException for trailing slash");
        } catch (IOException e) {
            if (!e.getMessage().startsWith("Invalid IPAddress:")) {
                throw new RuntimeException("Unexpected message for trailing slash: " + e.getMessage());
            }
        }
    }

    private static void testValidIPv4() throws IOException {
        IPAddressName ip = new IPAddressName("192.168.1.1");
        if (!ip.isIPv4) {
            throw new RuntimeException("Expected IPv4 address");
        }
    }

    private static void testValidIPv6() throws IOException {
        IPAddressName ip = new IPAddressName("2001:0db8:85a3:0000:0000:8a2e:0370:7334");
        if (ip.isIPv4) {
            throw new RuntimeException("Expected IPv6 address");
        }
    }

    private static void testInvalidFormat() {
        try {
            new IPAddressName("invalid_ip_address");
            throw new RuntimeException("Expected IOException for invalid format");
        } catch (IOException e) {
            if (!e.getMessage().startsWith("Invalid IPAddress:")) {
                throw new RuntimeException("Unexpected message for invalid format: " + e.getMessage());
            }
        }
    }
}
