/*
 * @test
 * @summary Test java.net.InetAddress.get() method for expected behavior and exception
 * @run main InetAddressWitupTest
 */

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressWitupTest {
    public static void main(String[] args) {
        testGetReturnsInetAddresses();
        testGetThrowsUnknownHostException();
    }

    private static void testGetReturnsInetAddresses() {
        try {
            // Create a subclass instance with inetAddresses set to a non-null value
            InetAddressWithInetAddresses ia = new InetAddressWithInetAddresses(new InetAddress[] {InetAddress.getLoopbackAddress()});
            InetAddress[] result = ia.get();
            if (result == null || result.length == 0) {
                throw new AssertionError("Expected non-null, non-empty InetAddress array");
            }
            if (!result[0].equals(InetAddress.getLoopbackAddress())) {
                throw new AssertionError("Returned InetAddress does not match expected value");
            }
        } catch (UnknownHostException e) {
            throw new AssertionError("Unexpected UnknownHostException thrown", e);
        }
    }

    private static void testGetThrowsUnknownHostException() {
        try {
            InetAddressWithInetAddresses ia = new InetAddressWithInetAddresses(null);
            ia.get();
            throw new AssertionError("Expected UnknownHostException was not thrown");
        } catch (UnknownHostException e) {
            // Expected exception
            if (e.getMessage() == null) {
                throw new AssertionError("UnknownHostException message should not be null");
            }
        }
    }

    // Helper subclass to set inetAddresses field for testing
    private static class InetAddressWithInetAddresses extends InetAddress {
        private final InetAddress[] inetAddresses;
        private final String host = "testhost";

        InetAddressWithInetAddresses(InetAddress[] inetAddresses) {
            this.inetAddresses = inetAddresses;
        }

        @Override
        public InetAddress[] get() throws UnknownHostException {
            if (inetAddresses == null) {
                throw new UnknownHostException(host);
            }
            return inetAddresses;
        }
    }
}
