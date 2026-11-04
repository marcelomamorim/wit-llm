/* @test    @summary Test IPAddressName constructor with valid IPv4 and IPv6 addresses    @run main IPAddressNameWitupTest */

import java.io.IOException;
import sun.security.x509.IPAddressName;

public class IPAddressNameWitupTest {

    public static void main(String[] args) throws Exception {
        testValidIPv4Address();
        testValidIPv6Address();
        testInvalidEmptyString();
        testInvalidNull();
        testInvalidTrailingSlash();
        testInvalidAddressFormat();
    }

    static void testValidIPv4Address() throws IOException {
        String ipv4 = "192.168.1.1";
        IPAddressName ip = new IPAddressName(ipv4);
        assert ip != null : "Failed to create IPAddressName with IPv4 address";
    }

    static void testValidIPv6Address() throws IOException {
        String ipv6 = "2001:0db8:85a3:0000:0000:8a2e:0370:7334";
        IPAddressName ip = new IPAddressName(ipv6);
        assert ip != null : "Failed to create IPAddressName with IPv6 address";
    }

    static void testInvalidEmptyString() {
        try {
            new IPAddressName("");
            assert false : "Expected IOException for empty string";
        } catch (IOException e) {
            assert e.getMessage().contains("null or empty") : "Unexpected message: " + e.getMessage();
        }
    }

    static void testInvalidNull() {
        try {
            new IPAddressName(null);
            assert false : "Expected IOException for null";
        } catch (IOException e) {
            assert e.getMessage().contains("null or empty") : "Unexpected message: " + e.getMessage();
        }
    }

    static void testInvalidTrailingSlash() {
        try {
            new IPAddressName("192.168.1.1/");
            assert false : "Expected IOException for trailing slash";
        } catch (IOException e) {
            assert e.getMessage().contains("Invalid IPAddress") : "Unexpected message: " + e.getMessage();
        }
    }

    static void testInvalidAddressFormat() {
        try {
            new IPAddressName("InvalidAddress");
            assert false : "Expected IOException for invalid address";
        } catch (IOException e) {
            assert e.getMessage().contains("Invalid IPAddress") : "Unexpected message: " + e.getMessage();
        }
    }
}
