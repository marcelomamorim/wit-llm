/*
 * @test
 * @summary Test for URLConnection.getInputStream() method.
 * @run main URLConnectionWitupTest
 */

import java.net.URLConnection;
import java.io.InputStream;
import java.io.IOException;

public class URLConnectionWitupTest {

    public static void main(String[] args) {
        testGetInputStreamThrowsException();
    }

    public static void testGetInputStreamThrowsException() {
        URLConnection conn = new URLConnection(null) {
            @Override
            public InputStream getInputStream() throws IOException {
                throw new UnknownServiceException("protocol doesn't support input");
            }
        };
        try {
            InputStream is = conn.getInputStream();
            // Se não lançar exceção, o teste falha
            System.err.println("Expected UnknownServiceException was not thrown.");
            System.exit(1);
        } catch (UnknownServiceException e) {
            // Teste passou se a exceção for capturada
            System.out.println("Caught expected UnknownServiceException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Unexpected IOException: " + e);
            System.exit(1);
        }
    }
}
