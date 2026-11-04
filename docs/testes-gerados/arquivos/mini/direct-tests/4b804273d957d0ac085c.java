/*
 * @test
 * @summary Test ImageReader.getResource(Node) method behavior
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import jdk.internal.jimage.ImageReader;

public class ImageReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testGetResourceWithNullNode();
    }

    private static void testGetResourceWithNullNode() {
        try {
            // Create a dummy ImageReader instance with a minimal SharedImageReader stub
            ImageReader reader = new ImageReader(new SharedImageReaderStub());
            // Call getResource with null Node, expecting NullPointerException or IOException
            byte[] result = reader.getResource(null);
            if (result == null) {
                throw new AssertionError("getResource(null) returned null array, expected non-null or exception");
            }
        } catch (NullPointerException | IOException e) {
            // Expected exception path for null argument
            return;
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
        throw new AssertionError("getResource(null) did not throw expected exception");
    }

    // Minimal stub to allow ImageReader instantiation and delegate getResource call
    private static class SharedImageReaderStub extends jdk.internal.jimage.SharedImageReader {
        SharedImageReaderStub() {
            super(null, null);
        }

        @Override
        public byte[] getResource(jdk.internal.jimage.Node node) throws IOException {
            if (node == null) {
                throw new NullPointerException("node is null");
            }
            return new byte[] {1, 2, 3};
        }

        @Override
        public void close(ImageReader ir) throws IOException {
            // no-op
        }

        @Override
        public jdk.internal.jimage.Directory getRootDirectory() throws IOException {
            return null;
        }

        @Override
        public jdk.internal.jimage.Node findNode(String name) throws IOException {
            return null;
        }
    }
}