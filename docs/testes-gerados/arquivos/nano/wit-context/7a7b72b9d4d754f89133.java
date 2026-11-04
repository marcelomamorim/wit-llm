/*
 * @test
 * @summary Test for setContentHandlerFactory method in java.net.URLConnection
 * @run main URLConnectionWitupTest
 */

import java.net.URLConnection;
import java.net.ContentHandlerFactory;

public class URLConnectionWitupTest {

    public static void main(String[] args) {
        testSetContentHandlerFactoryWithNull();
        testSetContentHandlerFactoryWithNonNull();
    }

    private static void testSetContentHandlerFactoryWithNull() {
        try {
            URLConnection.setContentHandlerFactory(null);
            System.out.println("testSetContentHandlerFactoryWithNull passed");
        } catch (Exception e) {
            System.err.println("testSetContentHandlerFactoryWithNull failed: " + e);
        }
    }

    private static void testSetContentHandlerFactoryWithNonNull() {
        try {
            // First, set factory to null to reset state
            URLConnection.setContentHandlerFactory(null);
            // Now, set a dummy factory
            URLConnection.setContentHandlerFactory(new DummyFactory());
            // Attempt to set again, should throw Error
            URLConnection.setContentHandlerFactory(new DummyFactory());
            System.err.println("testSetContentHandlerFactoryWithNonNull failed: did not throw as expected");
        } catch (Error e) {
            if (e.getMessage().equals("factory already defined")) {
                System.out.println("testSetContentHandlerFactoryWithNonNull passed");
            } else {
                System.err.println("testSetContentHandlerFactoryWithNonNull failed: " + e);
            }
        } catch (Exception e) {
            System.err.println("testSetContentHandlerFactoryWithNonNull failed: " + e);
        }
    }

    static class DummyFactory implements ContentHandlerFactory {
        @Override
        public Object create(java.net.URL url) {
            return null;
        }
    }
}
