/* @test    @summary Test setOOBInline method in BaseSSLSocketImpl for JDK 11+28    @run main BaseSSLSocketImplWitupTest */

import java.io.*;
import sun.security.ssl.BaseSSLSocketImpl;

public class BaseSSLSocketImplWitupTest {

    public static void main(String[] args) {
        testSetOOBInlineThrowsSocketException();
    }

    public static void testSetOOBInlineThrowsSocketException() {
        try {
            BaseSSLSocketImpl socket = new BaseSSLSocketImpl() {
                // Implement abstract methods if any, or use a concrete subclass if needed
            };
            socket.setOOBInline(true);
            // If no exception is thrown, the test fails
            System.err.println("FAIL: setOOBInline did not throw SocketException");
        } catch (SocketException e) {
            String expectedMessage = "This method is ineffective, since" +
                " sending urgent data is not supported by SSLSockets";
            if (e.getMessage().equals(expectedMessage)) {
                System.out.println("PASS");
            } else {
                System.err.println("FAIL: SocketException message mismatch: " + e.getMessage());
            }
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception: " + t);
        }
    }
}
