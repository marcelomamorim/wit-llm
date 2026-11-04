/* @test    @summary Test IPAddressName constructor with valid IPv4 and IPv6 addresses    @run main IPAddressNameWitupTest */

import java.io.IOException;
import sun.security.x509.IPAddressName;

public class IPAddressNameWitupTest {

    public static void main(String[] args) throws IOException {
        testValidIPv4();
        testValidIPv6();
        testInvalidAddressLength();
    }

    static void testValidIPv4() throws IOException {
        byte[] ipv4Address = { (byte)192, (byte)168, 1, 1 };
        IPAddressName ipName = new IPAddressName(ipv4Address);
        assert ipName != null : "Failed to create IPAddressName with IPv4 address";
    }

    static void testValidIPv6() throws IOException {
        byte[] ipv6Address = {
            0x20, 0x01, 0x0d, (byte)0xb8,
            0x85, 0xa3, 0x00, 0x00,
            0x00, 0x00, 0x8a, 0x2e,
            0x03, 0x70, 0x73, 0x34
        };
        IPAddressName ipName = new IPAddressName(ipv6Address);
        assert ipName != null : "Failed to create IPAddressName with IPv6 address";
    }

    static void testInvalidAddressLength() {
        byte[] invalidAddress = { 1, 2, 3 }; // invalid length
        try {
            new IPAddressName(invalidAddress);
            assert false : "Expected IOException for invalid address length";
        } catch (IOException e) {
            assert e.getMessage().equals("Invalid IPAddressName") : "Unexpected exception message";
        }
    }
}
