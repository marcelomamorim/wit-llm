/*
 * @test
 * @summary Test InetSocketAddress(String hostname, int port) constructor behavior
 * @run main InetSocketAddressWitupTest
 */

import java.net.InetSocketAddress;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetSocketAddressWitupTest {
    public static void main(String[] args) throws Exception {
        testValidHostname();
        testUnresolvedHostname();
        testPortBoundaries();
    }

    private static void testValidHostname() throws Exception {
        InetSocketAddress isa = new InetSocketAddress("localhost", 80);
        InetAddress addr = isa.getAddress();
        if (addr == null) {
            throw new RuntimeException("Expected resolved address for 'localhost', got null");
        }
        if (!"localhost".equals(isa.getHostName()) && !isa.getHostName().equals(addr.getHostName())) {
            throw new RuntimeException("Hostname mismatch: expected 'localhost' or address hostname, got " + isa.getHostName());
        }
        if (isa.getPort() != 80) {
            throw new RuntimeException("Port mismatch: expected 80, got " + isa.getPort());
        }
    }

    private static void testUnresolvedHostname() {
        // Use a hostname that is very unlikely to resolve
        String unresolvedHost = "no.such.host.should.exist.example.test";
        InetSocketAddress isa = new InetSocketAddress(unresolvedHost, 1234);
        if (isa.isUnresolved() == false) {
            throw new RuntimeException("Expected unresolved InetSocketAddress for hostname: " + unresolvedHost);
        }
        if (!unresolvedHost.equals(isa.getHostName())) {
            throw new RuntimeException("Hostname mismatch for unresolved: expected '" + unresolvedHost + "', got '" + isa.getHostName() + "'");
        }
        if (isa.getPort() != 1234) {
            throw new RuntimeException("Port mismatch: expected 1234, got " + isa.getPort());
        }
    }

    private static void testPortBoundaries() {
        // Test minimum valid port
        InetSocketAddress isaMin = new InetSocketAddress("localhost", 0);
        if (isaMin.getPort() != 0) {
            throw new RuntimeException("Port mismatch: expected 0, got " + isaMin.getPort());
        }

        // Test maximum valid port
        InetSocketAddress isaMax = new InetSocketAddress("localhost", 65535);
        if (isaMax.getPort() != 65535) {
            throw new RuntimeException("Port mismatch: expected 65535, got " + isaMax.getPort());
        }
    }
}
