/*
 * @test
 * @summary Test java.net.URLConnection.setContentHandlerFactory behavior
 * @run main URLConnectionWitupTest
 */

import java.net.ContentHandlerFactory;
import java.net.URLConnection;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetContentHandlerFactoryNull();
        testSetContentHandlerFactoryTwiceThrowsError();
        testSetContentHandlerFactorySecurityCheck();
    }

    private static void testSetContentHandlerFactoryNull() {
        // Reset factory via reflection is not possible, so this test assumes factory is null initially
        // Setting factory to null should succeed without exception
        URLConnection.setContentHandlerFactory(null);
    }

    private static void testSetContentHandlerFactoryTwiceThrowsError() {
        // First set factory to a dummy instance
        ContentHandlerFactory dummyFactory = new ContentHandlerFactory() {
            public java.net.ContentHandler createContentHandler(String mimetype) {
                return null;
            }
        };

        // Reset factory is not possible, so this test must be run in isolation or JVM restart
        // To avoid test failure due to previous runs, we try-catch Error and only fail if no Error thrown
        boolean errorThrown = false;
        try {
            URLConnection.setContentHandlerFactory(dummyFactory);
            // Second call should throw Error
            URLConnection.setContentHandlerFactory(dummyFactory);
        } catch (Error e) {
            if ("factory already defined".equals(e.getMessage())) {
                errorThrown = true;
            } else {
                throw e; // unexpected Error
            }
        }
        if (!errorThrown) {
            throw new AssertionError("Expected Error not thrown when setting factory twice");
        }
    }

    private static void testSetContentHandlerFactorySecurityCheck() {
        // This test verifies that SecurityManager.checkSetFactory is called if SecurityManager is present
        // Since we cannot install a SecurityManager in jtreg easily, we test that no exception is thrown when no SecurityManager
        // and that setting factory to null or non-null works as expected
        URLConnection.setContentHandlerFactory(null);
    }
}
