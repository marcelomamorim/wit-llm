/*
 * @test
 * @summary Test setContentHandlerFactory method for URLConnection
 * @run main URLConnectionWitupTest
 */

import java.net.URLConnection;
import java.security.Permission;

public class URLConnectionWitupTest {

    public static void main(String[] args) {
        testSetContentHandlerFactoryNull();
        testSetContentHandlerFactoryAlreadySet();
    }

    private static void testSetContentHandlerFactoryNull() {
        // Reset factory to null for test
        // Since factory is static, ensure no previous state affects this test
        // Note: No public API to reset factory, so this test assumes isolated environment
        try {
            // First, set factory to null by calling setContentHandlerFactory with null
            // But method throws if factory != null, so simulate initial state
            // For test purposes, we assume factory is null at start
            // Call with null to verify no exception
            URLConnection.setContentHandlerFactory(null);
            System.out.println("testSetContentHandlerFactoryNull passed");
        } catch (Error e) {
            System.out.println("testSetContentHandlerFactoryNull failed: " + e.getMessage());
        }
    }

    private static void testSetContentHandlerFactoryAlreadySet() {
        // Set a dummy factory to simulate factory already set
        try {
            URLConnection.setContentHandlerFactory(new DummyContentHandlerFactory());
            // Now, attempt to set again, should throw Error
            URLConnection.setContentHandlerFactory(new DummyContentHandlerFactory());
            System.out.println("testSetContentHandlerFactoryAlreadySet failed: no error thrown");
        } catch (Error e) {
            if ("factory already defined".equals(e.getMessage())) {
                System.out.println("testSetContentHandlerFactoryAlreadySet passed");
            } else {
                System.out.println("testSetContentHandlerFactoryAlreadySet failed: " + e.getMessage());
            }
        }
    }

    static class DummyContentHandlerFactory implements java.net.ContentHandlerFactory {
        @Override
        public java.net.ContentHandler create(java.net.URLConnection uc) {
            return null;
        }
    }
}
