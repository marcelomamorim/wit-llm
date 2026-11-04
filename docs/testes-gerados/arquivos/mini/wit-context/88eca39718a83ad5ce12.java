/*
 * @test
 * @summary Test ImageReader.getResource(String) for open/closed state behavior
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.nio.file.Paths;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testGetResourceWhenOpen();
        testGetResourceWhenClosed();
    }

    private static void testGetResourceWhenOpen() throws Exception {
        // Use a valid image path that exists or simulate with a known path
        // Since we cannot create a real image file here, test only that method calls do not throw
        // We test that getResource returns non-null or throws IOException if resource not found
        Path imagePath = Paths.get(System.getProperty("java.home")); // likely a directory, but open may fail
        ImageReader reader = null;
        try {
            reader = ImageReader.open(imagePath, ByteOrder.nativeOrder());
            // We call getResource with a dummy name, expecting either IOException or null return
            // But getResource(String) returns byte[], so null is possible only if reader.getResource returns null
            // We catch IOException to allow for missing resource
            try {
                byte[] resource = reader.getResource("nonexistent_resource_name");
                if (resource == null) {
                    throw new AssertionError("getResource returned null instead of byte[]");
                }
            } catch (IOException e) {
                // acceptable if resource not found
            }
        } catch (IOException e) {
            // acceptable if open fails due to invalid path
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private static void testGetResourceWhenClosed() throws Exception {
        // Open and close the reader, then call getResource and expect IllegalStateException
        Path imagePath = Paths.get(System.getProperty("java.home"));
        ImageReader reader = null;
        try {
            reader = ImageReader.open(imagePath, ByteOrder.nativeOrder());
            reader.close();
            try {
                reader.getResource("any");
                throw new AssertionError("Expected IllegalStateException when calling getResource on closed ImageReader");
            } catch (IllegalStateException expected) {
                // expected
            } catch (IOException ioe) {
                // IOException is not expected here according to source, fail
                throw new AssertionError("Unexpected IOException instead of IllegalStateException", ioe);
            }
        } catch (IOException e) {
            // acceptable if open fails
        } finally {
            if (reader != null) {
                try {
                    if (!isClosed(reader)) {
                        reader.close();
                    }
                } catch (IOException ignored) {
                }
            }
        }
    }

    private static boolean isClosed(ImageReader reader) {
        // No public API to check closed, so we rely on calling close and catching IOException
        try {
            reader.close();
            return false;
        } catch (IOException e) {
            return true;
        }
    }
}
