/*
 * @test
 * @summary Tests the public IPAddressName(String) constructor for invalid and valid textual addresses
 * @modules java.base/sun.security.x509
 * @run main IPAddressNameWitupTest
 */

import java.io.IOException;
import sun.security.x509.IPAddressName;

public class IPAddressNameWitupTest {

    public static void main(String[] args) throws Exception {
        testNullNameThrows();
        testEmptyNameThrows();
        testTrailingSlashThrows();
        testNoSeparatorThrows();
        testValidIPv4DoesNotThrow();
        testIPv4WithPrefixDoesNotThrow();
        testValidIPv6DoesNotThrow();
    }

    private static void expectIOException(String input) {
        try {
            new IPAddressName(input);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for input: " + input);
    }

    private static void testNullNameThrows() {
        try {
            new IPAddressName((String) null);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("Expected IOException for null name");
    }

    private static void testEmptyNameThrows() {
        expectIOException("");
    }

    private static void testTrailingSlashThrows() {
        expectIOException("192.168.1.1/");
        expectIOException("2001:db8::1/");
    }

    private static void testNoSeparatorThrows() {
        expectIOException("abc");
    }

    private static void testValidIPv4DoesNotThrow() throws IOException {
        new IPAddressName("192.168.1.1");
    }

    private static void testIPv4WithPrefixDoesNotThrow() throws IOException {
        new IPAddressName("10.9.8.0/255.255.255.0");
    }

    private static void testValidIPv6DoesNotThrow() throws IOException {
        new IPAddressName("2001:db8::1");
    }
}
