/*
 * @test
 * @summary Test IPAddressName constructor with byte[] input for valid and invalid lengths
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
        testInvalidAddressLength3();
        testInvalidAddressLength9();
        testInvalidAddressLength0();
    }

    private static void testValidIPv4AddressLength4() throws IOException {
        byte[] addr = new byte[] { (byte)192, (byte)168, 1, 1 };
        IPAddressName ip = new IPAddressName(addr);
        if (ip == null) throw new RuntimeException("IPAddressName instance is null");
    }

    private static void testValidIPv4AddressLength8() throws IOException {
        byte[] addr = new byte[] { (byte)192, (byte)168, 1, 1, (byte)255, (byte)255, (byte)255, 0 };
        IPAddressName ip = new IPAddressName(addr);
        if (ip == null) throw new RuntimeException("IPAddressName instance is null");
    }

    private static void testValidIPv6AddressLength16() throws IOException {
        byte[] addr = new byte[16];
        for (int i = 0; i < 16; i++) {
            addr[i] = (byte) i;
        }
        IPAddressName ip = new IPAddressName(addr);
        if (ip == null) throw new RuntimeException("IPAddressName instance is null");
    }

    private static void testValidIPv6AddressLength32() throws IOException {
        byte[] addr = new byte[32];
        for (int i = 0; i < 32; i++) {
            addr[i] = (byte) (i * 2);
        }
        IPAddressName ip = new IPAddressName(addr);
        if (ip == null) throw new RuntimeException("IPAddressName instance is null");
    }

    private static void testInvalidAddressLength3() {
        byte[] addr = new byte[] { 1, 2, 3 };
        try {
            new IPAddressName(addr);
            throw new RuntimeException("Expected IOException for invalid length 3");
        } catch (IOException e) {
            // expected
        }
    }

    private static void testInvalidAddressLength9() {
        byte[] addr = new byte[9];
        try {
            new IPAddressName(addr);
            throw new RuntimeException("Expected IOException for invalid length 9");
        } catch (IOException e) {
            // expected
        }
    }

    private static void testInvalidAddressLength0() {
        byte[] addr = new byte[0];
        try {
            new IPAddressName(addr);
            throw new RuntimeException("Expected IOException for invalid length 0");
        } catch (IOException e) {
            // expected
        }
    }
}
