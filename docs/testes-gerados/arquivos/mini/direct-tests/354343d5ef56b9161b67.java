/*
 * @test
 * @summary Test ImageReader.getResourceBuffer(ImageLocation) behavior
 * @run main ImageReaderWitupTest
 */

import java.nio.ByteBuffer;
import java.io.IOException;
import jdk.internal.jimage.ImageReader;
import jdk.internal.jimage.ImageLocation;

public class ImageReaderWitupTest {
    public static void main(String[] args) throws Exception {
        // Since ImageReader requires a SharedImageReader and ImageLocation is needed,
        // we test the behavior of getResourceBuffer with a dummy ImageLocation and
        // a dummy ImageReader that simulates the reader behavior.

        // Create a dummy ImageLocation (null or minimal stub) since no constructor details given
        ImageLocation dummyLoc = new ImageLocation() {
            // no methods to override as no details given
        };

        // Create a dummy SharedImageReader that returns a fixed ByteBuffer
        final ByteBuffer expectedBuffer = ByteBuffer.allocate(10);

        // Create a subclass of ImageReader to inject dummy SharedImageReader
        ImageReader reader = new ImageReader(new SharedImageReader() {
            @Override
            public ByteBuffer getResourceBuffer(ImageLocation loc) {
                if (loc == dummyLoc) {
                    return expectedBuffer;
                } else {
                    return null;
                }
            }

            @Override
            public void close(ImageReader ir) throws IOException {
                // no-op
            }

            @Override
            public Directory getRootDirectory() throws IOException {
                return null;
            }

            @Override
            public Node findNode(String name) throws IOException {
                return null;
            }

            @Override
            public byte[] getResource(Node node) throws IOException {
                return null;
            }

            @Override
            public byte[] getResource(Resource rs) throws IOException {
                return null;
            }

            @Override
            public ImageHeader getHeader() {
                return null;
            }
        });

        // Use reflection to set the private field 'closed' to false to allow requireOpen
        java.lang.reflect.Field closedField = ImageReader.class.getDeclaredField("closed");
        closedField.setAccessible(true);
        closedField.setBoolean(reader, false);

        // Call getResourceBuffer and verify it returns the expected ByteBuffer
        ByteBuffer result = reader.getResourceBuffer(dummyLoc);
        if (result != expectedBuffer) {
            throw new AssertionError("getResourceBuffer did not return expected ByteBuffer");
        }

        // Now set closed to true and verify that requireOpen throws IllegalStateException
        closedField.setBoolean(reader, true);
        boolean threw = false;
        try {
            reader.getResourceBuffer(dummyLoc);
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("getResourceBuffer did not throw IllegalStateException when closed");
        }

        System.out.println("Test passed");
    }

    // Dummy interfaces/classes to satisfy compilation
    interface SharedImageReader {
        ByteBuffer getResourceBuffer(ImageLocation loc);
        void close(ImageReader ir) throws IOException;
        Directory getRootDirectory() throws IOException;
        Node findNode(String name) throws IOException;
        byte[] getResource(Node node) throws IOException;
        byte[] getResource(Resource rs) throws IOException;
        ImageHeader getHeader();
    }

    interface ImageLocation {}
    interface Directory {}
    interface Node {}
    interface Resource {}
    interface ImageHeader {}
}
