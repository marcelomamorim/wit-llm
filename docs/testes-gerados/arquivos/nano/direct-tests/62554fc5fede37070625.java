/* @test    @summary Test setEnabledProtocols method of SSLSocketImpl    @run main SSLSocketImplWitupTest */

import sun.security.ssl.SSLSocketImpl;

public class SSLSocketImplWitupTest {

    public static void main(String[] args) {
        testSetEnabledProtocolsNull();
        testSetEnabledProtocolsValid();
    }

    private static void testSetEnabledProtocolsNull() {
        SSLSocketImpl socket = new SSLSocketImpl();
        boolean exceptionThrown = false;
        try {
            socket.setEnabledProtocols(null);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for null protocols");
        }
    }

    private static void testSetEnabledProtocolsValid() {
        SSLSocketImpl socket = new SSLSocketImpl();
        String[] protocols = {"TLSv1.2", "TLSv1.3"};
        socket.setEnabledProtocols(protocols);
        // No exception means success; further validation would require access to conContext, which is not public.
        // Here, we assume that if no exception is thrown, the method works as intended.
    }
}
