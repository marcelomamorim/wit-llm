/*
 * @test
 * @summary Test java.lang.ClassLoader.getResourceAsStream(String) behavior
 * @run main ClassLoaderWitupTest
 */

import java.io.InputStream;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;
import java.lang.ClassLoader;
import java.util.Objects;

public class ClassLoaderWitupTest {

    // Minimal subclass to override getResource for controlled testing
    static class TestClassLoader extends ClassLoader {
        private final URL urlToReturn;

        TestClassLoader(URL url) {
            this.urlToReturn = url;
        }

        @Override
        public URL getResource(String name) {
            // Return the preset URL regardless of name
            return urlToReturn;
        }
    }

    // URL subclass that throws IOException on openStream
    static class IOExceptionURL extends URL {
        IOExceptionURL() throws MalformedURLException {
            super("file", "localhost", "/nonexistent");
        }

        @Override
        public InputStream openStream() throws IOException {
            throw new IOException("Simulated IOException");
        }
    }

    public static void main(String[] args) throws Exception {
        testNullNameThrowsNPE();
        testResourceNotFoundReturnsNull();
        testResourceFoundReturnsStream();
        testIOExceptionReturnsNull();
    }

    private static void testNullNameThrowsNPE() {
        ClassLoader cl = new ClassLoader() {};
        try {
            cl.getResourceAsStream(null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException npe) {
            // Expected
        }
    }

    private static void testResourceNotFoundReturnsNull() {
        ClassLoader cl = new TestClassLoader(null);
        InputStream is = cl.getResourceAsStream("anyname");
        if (is != null) {
            throw new RuntimeException("Expected null InputStream when resource not found");
        }
    }

    private static void testResourceFoundReturnsStream() throws Exception {
        // Use a known resource URL: the class file of this test class
        String resourceName = ClassLoaderWitupTest.class.getName().replace('.', '/') + ".class";
        URL resourceUrl = ClassLoaderWitupTest.class.getClassLoader().getResource(resourceName);
        if (resourceUrl == null) {
            throw new RuntimeException("Test resource not found: " + resourceName);
        }
        ClassLoader cl = new TestClassLoader(resourceUrl);
        InputStream is = cl.getResourceAsStream(resourceName);
        if (is == null) {
            throw new RuntimeException("Expected non-null InputStream for existing resource");
        }
        try {
            int firstByte = is.read();
            if (firstByte == -1) {
                throw new RuntimeException("InputStream unexpectedly empty");
            }
        } finally {
            is.close();
        }
    }

    private static void testIOExceptionReturnsNull() throws Exception {
        URL ioExceptionUrl = new IOExceptionURL();
        ClassLoader cl = new TestClassLoader(ioExceptionUrl);
        InputStream is = cl.getResourceAsStream("anyname");
        if (is != null) {
            throw new RuntimeException("Expected null InputStream when IOException occurs");
        }
    }
}
