/* @test    @summary Test InetSocketAddress(String hostname, int port) constructor for valid and unresolved hostnames    @run main InetSocketAddressWitupTest */

import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class InetSocketAddressWitupTest {

    public static void main(String[] args) {
        testValidHostname();
        testUnresolvedHostname();
    }

    private static void testValidHostname() {
        String hostname = "localhost";
        int port = 80;
        InetSocketAddress address = new InetSocketAddress(hostname, port);
        assert address.getPort() == port : "Port mismatch";
        String hostString = address.getHostString();
        assert hostString != null : "HostString should not be null";
        assert hostString.equals(hostname) || hostString.equals(address.getAddress().getHostAddress()) : "HostString mismatch";
        assert !address.isUnresolved() : "Address should be resolved";
    }

    private static void testUnresolvedHostname() {
        String hostname = "nonexistent.host";
        int port = 1234;
        InetSocketAddress address = new InetSocketAddress(hostname, port);
        assert address.getPort() == port : "Port mismatch";
        String hostString = address.getHostString();
        assert hostString != null : "HostString should not be null";
        assert hostString.equals(hostname) || hostString.equals(address.getAddress().getHostAddress()) : "HostString mismatch";
        assert address.isUnresolved() : "Address should be unresolved";
    }
}
