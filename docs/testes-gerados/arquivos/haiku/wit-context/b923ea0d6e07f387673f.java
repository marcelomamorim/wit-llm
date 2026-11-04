/*
 * @test
 * @summary Checks InetSocketAddress(String, int) rejects a null hostname, rejects an out-of-range port, and keeps unresolved hostnames
 * @run main InetSocketAddressWitupTest
 */

import java.net.InetSocketAddress;

public class InetSocketAddressWitupTest {

    public static void main(String[] args) throws Exception {
        testNullHostnameRejected();
        testInvalidPortRejected();
        testUnresolvedHostnameKept();
    }

    static void testNullHostnameRejected() {
        try {
            new InetSocketAddress((String) null, 80);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for null hostname");
    }

    static void testInvalidPortRejected() {
        try {
            new InetSocketAddress("unresolved.invalid", -1);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for port -1");
    }

    static void testUnresolvedHostnameKept() {
        InetSocketAddress addr = new InetSocketAddress("unresolved.invalid", 8080);
        if (!addr.isUnresolved()) {
            throw new RuntimeException("expected unresolved address for .invalid host");
        }
        if (addr.getPort() != 8080) {
            throw new RuntimeException("unexpected port: " + addr.getPort());
        }
        if (!"unresolved.invalid".equals(addr.getHostString())) {
            throw new RuntimeException("unexpected host string: " + addr.getHostString());
        }
    }
}
