/* @test    @summary Test getSystemResourceAsStream returns stream for existing resource and null for missing resource    @run main ClassLoaderWitupTest */

import java.io.InputStream;
import java.io.IOException;
import java.net.URL;

public class ClassLoaderWitupTest {

    public static void main(String[] args) {
        testExistingResource();
        testMissingResource();
    }

    private static void testExistingResource() {
        String resourceName = "java/lang/Object.class";
        InputStream stream = ClassLoader.getSystemResourceAsStream(resourceName);
        if (stream == null) {
            throw new AssertionError("Expected resource stream for existing resource, but got null");
        }
        try {
            int firstByte = stream.read();
            if (firstByte == -1) {
                throw new AssertionError("Stream for existing resource is empty");
            }
        } catch (IOException e) {
            throw new AssertionError("IOException while reading existing resource stream", e);
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

    private static void testMissingResource() {
        String resourceName = "non/existent/resource.txt";
        InputStream stream = ClassLoader.getSystemResourceAsStream(resourceName);
        if (stream != null) {
            throw new AssertionError("Expected null for missing resource, but got a stream");
        }
    }
}
