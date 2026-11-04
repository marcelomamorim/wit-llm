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
        testNullHostnameThrows();
        testValidHostnameResolves();
        testUnresolvableHostnameCreatesUnresolved();
        testPortBoundaryValues();
    }

    private static void testNullHostnameThrows() {
        try {
            new InetSocketAddress((String) null, 80);
            throw new RuntimeException("Expected IllegalArgumentException for null hostname");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    private static void testValidHostnameResolves() throws Exception {
        InetSocketAddress isa = new InetSocketAddress("localhost", 80);
        InetAddress addr = isa.getAddress();
        if (addr == null) {
            throw new RuntimeException("Expected resolved InetAddress for 'localhost'");
        }
        if (!"localhost".equals(isa.getHostName()) && !isa.getHostName().equals(addr.getHostName())) {
            throw new RuntimeException("Hostname mismatch");
        }
        if (isa.isUnresolved()) {
            throw new RuntimeException("Expected resolved InetSocketAddress for 'localhost'");
        }
    }

    private static void testUnresolvableHostnameCreatesUnresolved() {
        // Use a hostname that is very unlikely to resolve
        String badHost = "no.such.host.should.exist.example.invalid";
        InetSocketAddress isa = new InetSocketAddress(badHost, 12345);
        if (!isa.isUnresolved()) {
            throw new RuntimeException("Expected unresolved InetSocketAddress for unresolvable hostname");
        }
        if (!badHost.equals(isa.getHostName())) {
            throw new RuntimeException("Hostname should be preserved for unresolved address");
        }
        if (isa.getPort() != 12345) {
            throw new RuntimeException("Port mismatch");
        }
    }

    private static void testPortBoundaryValues() {
        // Test minimum valid port
        InetSocketAddress isaMin = new InetSocketAddress("localhost", 0);
        if (isaMin.getPort() != 0) {
            throw new RuntimeException("Port 0 expected");
        }

        // Test maximum valid port
        InetSocketAddress isaMax = new InetSocketAddress("localhost", 65535);
        if (isaMax.getPort() != 65535) {
            throw new RuntimeException("Port 65535 expected");
        }

        // Test invalid port (negative) - expect IllegalArgumentException
        try {
            new InetSocketAddress("localhost", -1);
            throw new RuntimeException("Expected IllegalArgumentException for negative port");
        } catch (IllegalArgumentException e) {
            // Expected
        }

        // Test invalid port (too large) - expect IllegalArgumentException
        try {
            new InetSocketAddress("localhost", 65536);
            throw new RuntimeException("Expected IllegalArgumentException for port > 65535");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}
