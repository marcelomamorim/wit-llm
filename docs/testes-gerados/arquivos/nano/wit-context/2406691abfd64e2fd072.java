/*
 * @test
 * @summary Test getResource with null argument to verify NullPointerException
 * @run main ClassLoaderWitupTest
 */

import java.lang.ClassLoader;
import java.net.URL;

public class ClassLoaderWitupTest {

    public static void main(String[] args) {
        testGetResourceNull();
    }

    public static void testGetResourceNull() {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        try {
            cl.getResource(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        }
    }
}
