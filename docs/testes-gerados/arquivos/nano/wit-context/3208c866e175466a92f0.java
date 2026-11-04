/*
 * @test
 * @summary Tests for sun.security.x509.IPAddressName constructor with various byte array inputs.
 * @run main IPAddressNameWitupTest
 */

import java.io.IOException;
import sun.security.x509.IPAddressName;

public class IPAddressNameWitupTest {

    public static void main(String[] args) {
        testValidIPv4();
        testValidIPv6();
        testInvalidTooShort();
        testInvalidTooLong();
    }

    static void testValidIPv4() {
        byte[] ipv4 = {127, 0, 0, 1};
        try {
            IPAddressName ip = new IPAddressName(ipv4);
            System.out.println("IPv4 test passed: " + ip);
        } catch (IOException e) {
            System.err.println("IPv4 test failed: " + e);
        }
    }

    static void testValidIPv6() {
        byte[] ipv6 = {
            0x20, 0x01, 0x0d, (byte)0xb8,
            0x85, 0xa3, 0x00, 0x00,
            0x00, 0x00, 0x8a, 0x2e,
            0x03, 0x70, 0x73, 0x34
        };
        try {
            IPAddressName ip = new IPAddressName(ipv6);
            System.out.println("IPv6 test passed: " + ip);
        } catch (IOException e) {
            System.err.println("IPv6 test failed: " + e);
        }
    }

    static void testInvalidTooShort() {
        byte[] shortAddress = {1, 2, 3};
        try {
            IPAddressName ip = new IPAddressName(shortAddress);
            System.err.println("Invalid short address test failed: no exception thrown");
        } catch (IOException e) {
            System.out.println("Invalid short address test passed: " + e.getMessage());
        }
    }

    static void testInvalidTooLong() {
        byte[] longAddress = new byte[50];
        try {
            IPAddressName ip = new IPAddressName(longAddress);
            System.err.println("Invalid long address test failed: no exception thrown");
        } catch (IOException e) {
            System.out.println("Invalid long address test passed: " + e.getMessage());
        }
    }
}
