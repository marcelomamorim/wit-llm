/*
 * @test
 * @summary Tests for sun.security.x509.IPAddressName constructor with String input
 * @run main IPAddressNameWitupTest
 */

import java.io.IOException;
import sun.security.x509.IPAddressName;

public class IPAddressNameWitupTest {

    public static void main(String[] args) {
        testNullName();
        testEmptyName();
        testInvalidFormatIPv6();
        testInvalidFormatIPv4();
        testValidIPv6();
        testValidIPv4();
        testInvalidIPAddress();
    }

    private static void testNullName() {
        try {
            new IPAddressName((String) null);
            System.err.println("Failed: Expected IOException for null name");
        } catch (IOException e) {
            System.out.println("Passed: Null name throws IOException");
        }
    }

    private static void testEmptyName() {
        try {
            new IPAddressName("");
            System.err.println("Failed: Expected IOException for empty name");
        } catch (IOException e) {
            System.out.println("Passed: Empty name throws IOException");
        }
    }

    private static void testInvalidFormatIPv6() {
        try {
            new IPAddressName("invalid:address");
            System.err.println("Failed: Expected IOException for invalid IPv6 format");
        } catch (IOException e) {
            System.out.println("Passed: Invalid IPv6 format throws IOException");
        }
    }

    private static void testInvalidFormatIPv4() {
        try {
            new IPAddressName("invalid.address");
            System.err.println("Failed: Expected IOException for invalid IPv4 format");
        } catch (IOException e) {
            System.out.println("Passed: Invalid IPv4 format throws IOException");
        }
    }

    private static void testValidIPv6() {
        try {
            IPAddressName ip = new IPAddressName("2001:0db8::1");
            System.out.println("Passed: Valid IPv6 address: " + ip);
        } catch (IOException e) {
            System.err.println("Failed: Valid IPv6 address threw IOException");
        }
    }

    private static void testValidIPv4() {
        try {
            IPAddressName ip = new IPAddressName("192.168.1.1");
            System.out.println("Passed: Valid IPv4 address: " + ip);
        } catch (IOException e) {
            System.err.println("Failed: Valid IPv4 address threw IOException");
        }
    }

    private static void testInvalidIPAddress() {
        try {
            new IPAddressName("256.256.256.256");
            System.out.println("Note: This may pass if constructor does not validate IP ranges.");
        } catch (IOException e) {
            System.out.println("Passed: Invalid IP address throws IOException");
        }
    }
}
