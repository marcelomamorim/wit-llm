/* @test    @summary Test getOOBInline method of BaseSSLSocketImpl class    @run main BaseSSLSocketImplWitupTest */

import java.net.SocketException;

public class BaseSSLSocketImplWitupTest {

    public static void main(String[] args) {
        testGetOOBInlineThrowsSocketException();
    }

    public static void testGetOOBInlineThrowsSocketException() {
        sun.security.ssl.BaseSSLSocketImpl socketImpl = new sun.security.ssl.BaseSSLSocketImpl() {
            @Override
            public boolean getOOBInline() throws SocketException {
                return super.getOOBInline();
            }
        };
        try {
            socketImpl.getOOBInline();
            throw new AssertionError("Expected SocketException was not thrown");
        } catch (SocketException e) {
            String expectedMessage = "This method is ineffective, since" +
                " sending urgent data is not supported by SSLSockets";
            if (!e.getMessage().equals(expectedMessage)) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
