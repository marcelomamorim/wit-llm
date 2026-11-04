/*
 * @test
 * @summary Test InetAddress.get() method behavior
 * @run main InetAddressWitupTest
 */

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressWitupTest {

    // Helper subclass to set inetAddresses and host for testing
    static class TestInetAddress extends InetAddress {
        private InetAddress[] inetAddresses;
        private String host;

        TestInetAddress(String host, InetAddress[] inetAddresses) {
            this.host = host;
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

    public static void main(String[] args) throws Exception {
        testGetReturnsInetAddresses();
        testGetThrowsUnknownHostException();
    }

    private static void testGetReturnsInetAddresses() throws Exception {
        InetAddress loopback = InetAddress.getByName("127.0.0.1");
        InetAddress[] addresses = new InetAddress[] { loopback };
        TestInetAddress testAddr = new TestInetAddress("localhost", addresses);

        InetAddress[] result = testAddr.get();
        if (result == null || result.length != 1 || !result[0].equals(loopback)) {
            throw new RuntimeException("testGetReturnsInetAddresses failed: unexpected result");
        }
    }

    private static void testGetThrowsUnknownHostException() {
        TestInetAddress testAddr = new TestInetAddress("unknownhost", null);
        try {
            testAddr.get();
            throw new RuntimeException("testGetThrowsUnknownHostException failed: expected UnknownHostException");
        } catch (UnknownHostException e) {
            if (!"unknownhost".equals(e.getMessage())) {
                throw new RuntimeException("testGetThrowsUnknownHostException failed: unexpected exception message");
            }
        }
    }
}
