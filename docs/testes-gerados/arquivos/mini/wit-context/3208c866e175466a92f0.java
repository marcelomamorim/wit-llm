/*
 * @test
 * @summary Test sun.security.x509.IPAddressName constructor with byte[] input
 * @run main IPAddressNameWitupTest
 */

import sun.security.x509.IPAddressName;
import java.io.IOException;

public class IPAddressNameWitupTest {
    public static void main(String[] args) throws Exception {
        testValidIPv4AddressLength4();
        testValidIPv4AddressLength8();
        testValidIPv6AddressLength16();
        testValidIPv6AddressLength32();
        testInvalidAddressLength();
    }

    private static void testValidIPv4AddressLength4() throws IOException {
        byte[] addr = new byte[] { (byte)192, (byte)168, 1, 1 };
        IPAddressName ip = new IPAddressName(addr);
        if (ip == null) {
            throw new RuntimeException("IPAddressName constructor returned null for valid IPv4 length 4");
        }
    }

    private static void testValidIPv4AddressLength8() throws IOException {
        byte[] addr = new byte[] { (byte)192, (byte)168, 1, 1, (byte)255, (byte)255, (byte)255, 0 };
        IPAddressName ip = new IPAddressName(addr);
        if (ip == null) {
            throw new RuntimeException("IPAddressName constructor returned null for valid IPv4 length 8");
        }
    }

    private static void testValidIPv6AddressLength16() throws IOException {
        byte[] addr = new byte[16];
        for (int i = 0; i < 16; i++) {
            addr[i] = (byte) i;
        }
        IPAddressName ip = new IPAddressName(addr);
        if (ip == null) {
            throw new RuntimeException("IPAddressName constructor returned null for valid IPv6 length 16");
        }
    }

    private static void testValidIPv6AddressLength32() throws IOException {
        byte[] addr = new byte[32];
        for (int i = 0; i < 32; i++) {
            addr[i] = (byte) (i * 2);
        }
        IPAddressName ip = new IPAddressName(addr);
        if (ip == null) {
            throw new RuntimeException("IPAddressName constructor returned null for valid IPv6 length 32");
        }
    }

    private static void testInvalidAddressLength() {
        byte[] addr = new byte[] { 1, 2, 3 }; // invalid length
        try {
            new IPAddressName(addr);
            throw new RuntimeException("Expected IOException for invalid address length but none thrown");
        } catch (IOException e) {
            // expected
        }
    }
}
