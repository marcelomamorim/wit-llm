/*
 * @test
 * @summary Test java.net.URL.getContent() for expected behavior and exception
 * @run main java.net.URLWitupTest
 */

import java.net.URL;
import java.net.UnknownServiceException;
import java.io.IOException;

public class URLWitupTest {
    public static void main(String[] args) throws Exception {
        testGetContentHttp() ;
        testGetContentUnknownProtocol() ;
    }

    private static void testGetContentHttp() throws Exception {
        // Valid HTTP URL should return content without throwing UnknownServiceException
        URL url = new URL("http://www.example.com/");
        Object content = url.getContent();
        if (content == null) {
            throw new AssertionError("getContent() returned null for valid HTTP URL");
        }
    }

    private static void testGetContentUnknownProtocol() {
        // URL with unknown protocol should throw UnknownServiceException when getContent() is called
        try {
            URL url = new URL("unknownprotocol://host/resource");
            url.getContent();
            throw new AssertionError("Expected UnknownServiceException not thrown for unknown protocol");
        } catch (UnknownServiceException e) {
            // Expected exception
        } catch (IOException e) {
            // IOException is superclass, but we expect specifically UnknownServiceException
            throw new AssertionError("Expected UnknownServiceException but got IOException", e);
        }
    }
}
