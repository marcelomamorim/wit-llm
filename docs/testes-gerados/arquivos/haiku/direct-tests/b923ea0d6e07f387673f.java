/*
 * @test
 * @summary Basic observable behavior of the InetSocketAddress(String hostname, int port) constructor
 * @run main InetSocketAddressWitupTest
 */

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class InetSocketAddressWitupTest {

    public static void main(String[] args) throws Exception {
        testLiteralIpResolves();
        testPortBoundaries();
        testUnresolvedHostname();
    }

    static void testLiteralIpResolves() throws Exception {
        InetSocketAddress sa = new InetSocketAddress("127.0.0.1", 8080);
        check(!sa.isUnresolved(), "literal IP should not be unresolved");
        check(sa.getPort() == 8080, "port should be 8080 but was " + sa.getPort());
        InetAddress addr = sa.getAddress();
        check(addr != null, "address should not be null for literal IP");
        check("127.0.0.1".equals(addr.getHostAddress()),
              "host address should be 127.0.0.1 but was " + addr.getHostAddress());
    }

    static void testPortBoundaries() {
        InetSocketAddress low = new InetSocketAddress("127.0.0.1", 0);
        check(low.getPort() == 0, "port 0 should be preserved");
        check(!low.isUnresolved(), "literal IP with port 0 should resolve");

        InetSocketAddress high = new InetSocketAddress("127.0.0.1", 65535);
        check(high.getPort() == 65535, "port 65535 should be preserved");
    }

    static void testUnresolvedHostname() {
        InetSocketAddress sa = new InetSocketAddress("invalid.invalid", 1234);
        check(sa.isUnresolved(), "'.invalid' host should be unresolved");
        check(sa.getAddress() == null, "address should be null when unresolved");
        check(sa.getPort() == 1234, "port should be 1234 but was " + sa.getPort());
        check("invalid.invalid".equals(sa.getHostString()),
              "host string should be the given hostname but was " + sa.getHostString());
        check("invalid.invalid".equals(sa.getHostName()),
              "host name should be the given hostname but was " + sa.getHostName());
        check("invalid.invalid:1234".equals(sa.toString()),
              "toString should be invalid.invalid:1234 but was " + sa.toString());
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
