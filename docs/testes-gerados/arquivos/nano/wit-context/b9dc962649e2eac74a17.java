/* @test    @summary Tests for java.net.URL.openStream() method.
 * @run main URLWitupTest
 */

import java.net.URL;
import java.io.InputStream;
import java.io.IOException;

public class URLWitupTest {

    public static void main(String[] args) {
        testOpenStreamWithValidURL();
        testOpenStreamWithInvalidProtocol();
    }

    private static void testOpenStreamWithValidURL() {
        try {
            URL url = new URL("http://www.example.com");
            InputStream is = url.openStream();
            if (is != null) {
                is.close();
                System.out.println("testOpenStreamWithValidURL passed");
            } else {
                System.err.println("testOpenStreamWithValidURL failed: InputStream is null");
            }
        } catch (IOException e) {
            System.err.println("testOpenStreamWithValidURL failed with IOException: " + e);
        }
    }

    private static void testOpenStreamWithInvalidProtocol() {
        try {
            URL url = new URL("invalidprotocol://host");
            url.openStream();
            System.err.println("testOpenStreamWithInvalidProtocol failed: no exception thrown");
        } catch (IOException e) {
            System.out.println("testOpenStreamWithInvalidProtocol passed: caught IOException");
        }
    }
}
