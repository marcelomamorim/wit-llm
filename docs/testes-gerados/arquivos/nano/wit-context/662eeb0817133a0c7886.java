/*
 * @test
 * @summary Test for ClassLoader.getResourceAsStream handling null input
 * @run main ClassLoaderWitupTest
 */

import java.lang.ClassLoader;
import java.io.InputStream;
import java.io.IOException;

public class ClassLoaderWitupTest {

    public static void main(String[] args) {
        testGetResourceAsStreamWithNullName();
        testGetResourceAsStreamWithValidName();
        testGetResourceAsStreamWithInvalidName();
    }

    static void testGetResourceAsStreamWithNullName() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        try {
            cl.getResourceAsStream(null);
            throw new AssertionError("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // Test passes
        }
    }

    static void testGetResourceAsStreamWithValidName() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        InputStream is = cl.getResourceAsStream("java/lang/Object.class");
        if (is == null) {
            throw new AssertionError("Expected non-null InputStream for valid resource");
        }
        try {
            is.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    static void testGetResourceAsStreamWithInvalidName() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        InputStream is = cl.getResourceAsStream("nonexistent/resource");
        if (is != null) {
            throw new AssertionError("Expected null InputStream for nonexistent resource");
        }
    }
}
