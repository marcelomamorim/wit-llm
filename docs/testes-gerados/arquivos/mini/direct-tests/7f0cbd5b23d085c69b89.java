/*
 * @test
 * @summary Test java.lang.ClassLoader.getResources(String) method behavior
 * @run main ClassLoaderWitupTest
 */

import java.lang.ClassLoader;
import java.net.URL;
import java.util.Enumeration;
import java.io.IOException;

public class ClassLoaderWitupTest {
    public static void main(String[] args) throws IOException {
        testGetResourcesWithNullName();
        testGetResourcesReturnsNonNullEnumeration();
        testGetResourcesContainsExpectedElements();
    }

    private static void testGetResourcesWithNullName() {
        ClassLoader cl = new ClassLoader() {
            protected Enumeration<URL> findResources(String name) {
                return new Enumeration<URL>() {
                    public boolean hasMoreElements() { return false; }
                    public URL nextElement() { throw new java.util.NoSuchElementException(); }
                };
            }
        };

        try {
            cl.getResources(null);
            throw new AssertionError("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }

    private static void testGetResourcesReturnsNonNullEnumeration() throws IOException {
        ClassLoader cl = new ClassLoader() {
            protected Enumeration<URL> findResources(String name) {
                return new Enumeration<URL>() {
                    public boolean hasMoreElements() { return false; }
                    public URL nextElement() { throw new java.util.NoSuchElementException(); }
                };
            }
        };

        Enumeration<URL> resources = cl.getResources("");
        if (resources == null) {
            throw new AssertionError("getResources returned null Enumeration");
        }
    }

    private static void testGetResourcesContainsExpectedElements() throws IOException {
        final URL url1 = new URL("file:/tmp/resource1");
        final URL url2 = new URL("file:/tmp/resource2");

        ClassLoader cl = new ClassLoader() {
            protected Enumeration<URL> findResources(String name) {
                return new Enumeration<URL>() {
                    private int count = 0;
                    public boolean hasMoreElements() {
                        return count < 1;
                    }
                    public URL nextElement() {
                        if (count++ == 0) {
                            return url2;
                        } else {
                            throw new java.util.NoSuchElementException();
                        }
                    }
                };
            }

            public Enumeration<URL> getResources(String name) throws IOException {
                // override to simulate parent
                return new Enumeration<URL>() {
                    private int count = 0;
                    public boolean hasMoreElements() {
                        return count < 1;
                    }
                    public URL nextElement() {
                        if (count++ == 0) {
                            return url1;
                        } else {
                            throw new java.util.NoSuchElementException();
                        }
                    }
                };
            }
        };

        Enumeration<URL> resources = cl.getResources("test");

        boolean foundUrl1 = false;
        boolean foundUrl2 = false;

        while (resources.hasMoreElements()) {
            URL u = resources.nextElement();
            if (u.equals(url1)) {
                foundUrl1 = true;
            } else if (u.equals(url2)) {
                foundUrl2 = true;
            }
        }

        if (!foundUrl1) {
            throw new AssertionError("Expected URL from parent not found");
        }
        if (!foundUrl2) {
            throw new AssertionError("Expected URL from findResources not found");
        }
    }
}
