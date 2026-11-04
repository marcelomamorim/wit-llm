/*
 * @test
 * @summary Test java.lang.ClassLoader.getResourceAsStream(String) for null input and normal behavior
 * @run main ClassLoaderWitupTest
 */

import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.lang.ClassLoader;
import java.util.Objects;

public class ClassLoaderWitupTest {
    public static void main(String[] args) throws Exception {
        testNullNameThrowsNPE();
        testNonexistentResourceReturnsNull();
        testExistingResourceReturnsStream();
    }

    static void testNullNameThrowsNPE() {
        ClassLoader cl = new ClassLoader() {};
        try {
            cl.getResourceAsStream(null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    static void testNonexistentResourceReturnsNull() {
        ClassLoader cl = new ClassLoader() {
            @Override
            public URL getResource(String name) {
                return null; // simulate resource not found
            }
        };
        InputStream is = cl.getResourceAsStream("nonexistent_resource");
        if (is != null) {
            throw new RuntimeException("Expected null InputStream for nonexistent resource");
        }
    }

    static void testExistingResourceReturnsStream() throws IOException {
        final byte[] dummyData = {1, 2, 3};
        ClassLoader cl = new ClassLoader() {
            @Override
            public URL getResource(String name) {
                if ("dummy_resource".equals(name)) {
                    try {
                        // Create a URL with a custom stream handler that returns dummyData
                        return new URL(null, "memory://dummy_resource", new java.net.URLStreamHandler() {
                            @Override
                            protected java.net.URLConnection openConnection(URL u) throws IOException {
                                return new java.net.URLConnection(u) {
                                    @Override
                                    public void connect() throws IOException {}
                                    @Override
                                    public InputStream getInputStream() throws IOException {
                                        return new java.io.ByteArrayInputStream(dummyData);
                                    }
                                };
                            }
                        });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                return null;
            }
        };
        InputStream is = cl.getResourceAsStream("dummy_resource");
        if (is == null) {
            throw new RuntimeException("Expected non-null InputStream for existing resource");
        }
        try {
            int firstByte = is.read();
            if (firstByte != 1) {
                throw new RuntimeException("Unexpected data read from InputStream");
            }
        } finally {
            is.close();
        }
    }
}
