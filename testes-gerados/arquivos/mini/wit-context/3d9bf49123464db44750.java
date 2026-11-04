/*
 * @test
 * @summary Test sun.security.x509.IPAddressName(String) constructor for valid and invalid inputs
 * @run main IPAddressNameWitupTest
 */

import sun.security.x509.IPAddressName;
import java.io.IOException;

public class IPAddressNameWitupTest {
    public static void main(String[] args) throws Exception {
        testNullNameThrows();
        testEmptyNameThrows();
        testNameEndingWithSlashThrows();
        testValidIPv4Name();
        testValidIPv6Name();
        testInvalidNameThrows();
        System.out.println("All tests passed.");
    }

    private static void testNullNameThrows() {
        try {
            new IPAddressName((String) null);
            throw new RuntimeException("Expected IOException for null name");
        } catch (IOException e) {
            // expected
        }
    }

    private static void testEmptyNameThrows() {
        try {
            new IPAddressName("");
            throw new RuntimeException("Expected IOException for empty name");
        } catch (IOException e) {
            // expected
        }
    }

    private static void testNameEndingWithSlashThrows() {
        try {
            new IPAddressName("192.168.1.1/");
            throw new RuntimeException("Expected IOException for name ending with slash");
        } catch (IOException e) {
            // expected
        }
    }

    private static void testValidIPv4Name() throws IOException {
        IPAddressName ip = new IPAddressName("192.168.1.1");
        if (ip == null) {
            throw new RuntimeException("IPAddressName constructor returned null for valid IPv4");
        }
    }

    private static void testValidIPv6Name() throws IOException {
        IPAddressName ip = new IPAddressName("2001:0db8:85a3:0000:0000:8a2e:0370:7334");
        if (ip == null) {
            throw new RuntimeException("IPAddressName constructor returned null for valid IPv6");
        }
    }

    private static void testInvalidNameThrows() {
        try {
            new IPAddressName("invalid_ip_address");
            throw new RuntimeException("Expected IOException for invalid IP address string");
        } catch (IOException e) {
            // expected
        }
    }
}
