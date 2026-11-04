/* @test    @summary Test for InetSocketAddress(String hostname, int port) constructor handling null hostname.    @run main InetSocketAddressWitupTest */

import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class InetSocketAddressWitupTest {

    public static void main(String[] args) {
        testNullHostname();
        testValidHostname();
        testUnresolvableHostname();
    }

    private static void testNullHostname() {
        try {
            new InetSocketAddress(null, 80);
            System.out.println("testNullHostname: Failed - no exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testNullHostname: Passed");
        } catch (Exception e) {
            System.out.println("testNullHostname: Failed - wrong exception: " + e);
        }
    }

    private static void testValidHostname() {
        try {
            InetSocketAddress addr = new InetSocketAddress("localhost", 80);
            if (addr.getHostName().equals("localhost")) {
                System.out.println("testValidHostname: Passed");
            } else {
                System.out.println("testValidHostname: Failed - hostname mismatch");
            }
        } catch (Exception e) {
            System.out.println("testValidHostname: Failed - exception: " + e);
        }
    }

    private static void testUnresolvableHostname() {
        try {
            InetSocketAddress addr = new InetSocketAddress("nonexistent.host", 80);
            if (addr.getHostName().equals("nonexistent.host")) {
                System.out.println("testUnresolvableHostname: Passed");
            } else {
                System.out.println("testUnresolvableHostname: Failed - hostname mismatch");
            }
        } catch (Exception e) {
            System.out.println("testUnresolvableHostname: Failed - exception: " + e);
        }
    }
}
