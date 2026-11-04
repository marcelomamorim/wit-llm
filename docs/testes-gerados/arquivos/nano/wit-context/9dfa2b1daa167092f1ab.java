/*
 * @test
 * @summary Test for URLConnection.getContent(Class<?>[])
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;

public class URLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testGetContentWithInputStream();
        testGetContentWithInvalidService();
    }

    private static void testGetContentWithInputStream() {
        try {
            URL url = new URL("http://example.com");
            URLConnection conn = url.openConnection();
            // Call getInputStream to simulate normal behavior
            conn.getInputStream();
            Object content = conn.getContent(new Class<?>[] { String.class });
            if (content == null) {
                System.err.println("Failed: getContent returned null");
            } else {
                System.out.println("Passed: getContent with input stream");
            }
        } catch (IOException e) {
            System.err.println("Exception in testGetContentWithInputStream: " + e);
        }
    }

    private static void testGetContentWithInvalidService() {
        try {
            URL url = new URL("http://invalid-service");
            URLConnection conn = url.openConnection();
            // Force getInputStream to throw UnknownServiceException
            conn.getInputStream();
            Object content = conn.getContent(new Class<?>[] { String.class });
            System.out.println("Unexpected success: getContent returned " + content);
        } catch (IOException e) {
            if (e.getClass().getSimpleName().equals("UnknownServiceException")) {
                System.out.println("Passed: getContent threw UnknownServiceException as expected");
            } else {
                System.err.println("Failed: Unexpected exception type: " + e);
            }
        }
    }
}
