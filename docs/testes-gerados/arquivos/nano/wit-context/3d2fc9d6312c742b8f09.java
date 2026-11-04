/* @test    @summary Test setOOBInline method in BaseSSLSocketImpl for exception behavior    @run main BaseSSLSocketImplWitupTest */

import java.io.*;
import sun.security.ssl.BaseSSLSocketImpl;

public class BaseSSLSocketImplWitupTest {

    public static void main(String[] args) {
        testSetOOBInlineThrowsSocketException();
    }

    public static void testSetOOBInlineThrowsSocketException() {
        BaseSSLSocketImpl socket = new BaseSSLSocketImpl() {
            // Implement abstract methods if any, or use an anonymous subclass
        };
        try {
            socket.setOOBInline(true);
            // If no exception is thrown, the test fails
            System.err.println("FAIL: Expected SocketException was not thrown.");
            System.exit(1);
        } catch (SocketException e) {
            // Expected exception caught, test passes
            System.out.println("PASS: SocketException was thrown as expected.");
        } catch (Exception e) {
            // Unexpected exception, test fails
            System.err.println("FAIL: Unexpected exception: " + e);
            System.exit(1);
        }
    }
}
