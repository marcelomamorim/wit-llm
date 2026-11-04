/*
 * @test
 * @summary Observable behaviour of InetAddress.getAllByName for null, empty and localhost hosts, exercising the address list path that backs get()
 * @run main InetAddressWitupTest
 */

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressWitupTest {

    public static void main(String[] args) throws Exception {
        testNullHostReturnsLoopback();
        testEmptyHostReturnsLoopback();
        testLocalhostResolvesToNonEmptyArray();
    }

    static void testNullHostReturnsLoopback() throws UnknownHostException {
        InetAddress[] addresses = InetAddress.getAllByName(null);
        check(addresses != null, "getAllByName(null) returned null");
        check(addresses.length > 0, "getAllByName(null) returned empty array");
        boolean hasLoopback = false;
        for (InetAddress a : addresses) {
            check(a != null, "null element in result for null host");
            if (a.isLoopbackAddress()) {
                hasLoopback = true;
            }
        }
        check(hasLoopback, "no loopback address returned for null host");
    }

    static void testEmptyHostReturnsLoopback() throws UnknownHostException {
        InetAddress[] addresses = InetAddress.getAllByName("");
        check(addresses != null, "getAllByName(\"\") returned null");
        check(addresses.length > 0, "getAllByName(\"\") returned empty array");
        boolean hasLoopback = false;
        for (InetAddress a : addresses) {
            check(a != null, "null element in result for empty host");
            if (a.isLoopbackAddress()) {
                hasLoopback = true;
            }
        }
        check(hasLoopback, "no loopback address returned for empty host");
    }

    static void testLocalhostResolvesToNonEmptyArray() throws UnknownHostException {
        InetAddress[] addresses = InetAddress.getAllByName("localhost");
        check(addresses != null, "getAllByName(\"localhost\") returned null");
        check(addresses.length > 0, "getAllByName(\"localhost\") returned empty array");
        for (InetAddress a : addresses) {
            check(a != null, "null element in localhost result");
            check(a.getAddress() != null, "null raw address for localhost result");
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
