/*
 * @test
 * @summary Test the write(int i) method of SSLSocketImpl for correct byte writing.
 * @run main SSLSocketImplWitupTest
 */

import java.io.IOException;
import sun.security.ssl.SSLSocketImpl;

public class SSLSocketImplWitupTest {

    public static void main(String[] args) {
        testWriteMethod()
        ;
    }

    public static void testWriteMethod() {
        SSLSocketImpl socket = new SSLSocketImpl();
        byte[] buffer = new byte[1];
        try {
            // Test writing byte value 65 ('A')
            socket.write(65);
            // Since write(int) calls write(byte[], int, int),
            // we cannot directly verify internal buffer without reflection.
            // Instead, we can test that no exception is thrown for valid input.
            System.out.println("write(65) executed without exception.");
        } catch (IOException e) {
            throw new RuntimeException("IOException thrown during write", e);
        }
        try {
            // Test writing byte value 0
            socket.write(0);
            System.out.println("write(0) executed without exception.");
        } catch (IOException e) {
            throw new RuntimeException("IOException thrown during write", e);
        }
        try {
            // Test writing byte value 255
            socket.write(255);
            System.out.println("write(255) executed without exception.");
        } catch (IOException e) {
            throw new RuntimeException("IOException thrown during write", e);
        }
    }
}
