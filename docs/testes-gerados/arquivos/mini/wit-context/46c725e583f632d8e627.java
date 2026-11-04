/*
 * @test
 * @summary Test java.net.URLConnection.getContent(URLConnection) method for expected behavior and exception
 * @run main URLConnectionWitupTest
 */

import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownServiceException;
import java.io.IOException;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        testGetContentThrowsUnknownServiceException();
    }

    private static void testGetContentThrowsUnknownServiceException() throws Exception {
        // Create a URL with a protocol that does not have a content handler to provoke UnknownServiceException
        URL url = new URL("unknownprotocol://host");
        URLConnection uc = url.openConnection();

        try {
            // This should throw UnknownServiceException as per the expath and current implementation
            URLConnection.class.getMethod("getContent", URLConnection.class).invoke(null, uc);
            throw new AssertionError("Expected UnknownServiceException was not thrown");
        } catch (java.lang.reflect.InvocationTargetException ite) {
            Throwable cause = ite.getCause();
            if (!(cause instanceof UnknownServiceException)) {
                throw new AssertionError("Expected UnknownServiceException but got " + cause, cause);
            }
            // Expected exception caught, test passes
        }
    }
}
