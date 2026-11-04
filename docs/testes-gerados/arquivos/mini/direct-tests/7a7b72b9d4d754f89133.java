/*
 * @test
 * @summary Test java.net.URLConnection.setContentHandlerFactory behavior
 * @run main java.net.URLConnectionWitupTest
 */

import java.net.ContentHandlerFactory;
import java.net.URLConnection;

public class URLConnectionWitupTest {
    public static void main(String[] args) throws Exception {
        testSetContentHandlerFactoryOnce();
        testSetContentHandlerFactoryTwiceThrowsError();
        testSetContentHandlerFactorySecurityCheck();
    }

    private static void testSetContentHandlerFactoryOnce() {
        // Reset factory via reflection is not possible, so this test must be run in isolation
        // This test verifies that setting factory once works without exception
        ContentHandlerFactory fac = new ContentHandlerFactory() {
            public java.net.ContentHandler createContentHandler(String mimetype) {
                return null;
            }
        };
        try {
            URLConnection.setContentHandlerFactory(fac);
        } catch (Error e) {
            // If factory already set, test is inconclusive, skip
            if (!"factory already defined".equals(e.getMessage())) {
                throw e;
            }
        } catch (SecurityException se) {
            // SecurityManager may prevent setting factory, acceptable
        }
    }

    private static void testSetContentHandlerFactoryTwiceThrowsError() {
        // This test attempts to set factory twice and expects Error
        ContentHandlerFactory fac1 = new ContentHandlerFactory() {
            public java.net.ContentHandler createContentHandler(String mimetype) {
                return null;
            }
        };
        ContentHandlerFactory fac2 = new ContentHandlerFactory() {
            public java.net.ContentHandler createContentHandler(String mimetype) {
                return null;
            }
        };

        try {
            URLConnection.setContentHandlerFactory(fac1);
        } catch (Error e) {
            if (!"factory already defined".equals(e.getMessage())) {
                throw e;
            }
            // factory already set, skip test
            return;
        } catch (SecurityException se) {
            // SecurityManager may prevent setting factory, skip test
            return;
        }

        try {
            URLConnection.setContentHandlerFactory(fac2);
            throw new RuntimeException("Expected Error not thrown when setting factory twice");
        } catch (Error e) {
            if (!"factory already defined".equals(e.getMessage())) {
                throw e;
            }
            // expected
        } catch (SecurityException se) {
            // SecurityManager may prevent setting factory, acceptable
        }
    }

    private static void testSetContentHandlerFactorySecurityCheck() {
        // This test verifies that SecurityManager.checkSetFactory is called
        // We install a custom SecurityManager that records if checkSetFactory is called
        final boolean[] called = {false};
        SecurityManager original = System.getSecurityManager();
        SecurityManager testSM = new SecurityManager() {
            @Override
            public void checkSetFactory() {
                called[0] = true;
                // allow
            }

            @Override
            public void checkPermission(java.security.Permission perm) {
                // allow all other permissions
            }
        };

        try {
            System.setSecurityManager(testSM);
            ContentHandlerFactory fac = new ContentHandlerFactory() {
                public java.net.ContentHandler createContentHandler(String mimetype) {
                    return null;
                }
            };
            try {
                URLConnection.setContentHandlerFactory(fac);
            } catch (Error e) {
                if (!"factory already defined".equals(e.getMessage())) {
                    throw e;
                }
                // factory already set, test inconclusive
            }
            if (!called[0]) {
                throw new RuntimeException("SecurityManager.checkSetFactory was not called");
            }
        } finally {
            System.setSecurityManager(original);
        }
    }
}
