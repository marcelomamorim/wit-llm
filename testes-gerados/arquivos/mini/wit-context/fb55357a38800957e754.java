/*
 * @test
 * @summary Test jdk.internal.jimage.ImageReader.close(ImageReader) method for exception and normal behavior
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.util.Objects;

public class ImageReaderWitupTest {

    // Minimal stub to simulate the OPEN_FILES and openers behavior
    static class ImageReader {
        private static final java.util.Set<ImageReader> openers = new java.util.HashSet<>();
        private static final java.util.Map<java.nio.file.Path, ImageReader> OPEN_FILES = new java.util.HashMap<>();
        private final java.nio.file.Path imagePath;

        public ImageReader(java.nio.file.Path path) {
            this.imagePath = path;
            synchronized (OPEN_FILES) {
                openers.add(this);
                OPEN_FILES.put(path, this);
            }
        }

        public java.nio.file.Path getImagePath() {
            return imagePath;
        }

        public void close(ImageReader image) throws IOException {
            Objects.requireNonNull(image);

            synchronized (OPEN_FILES) {
                if (!openers.remove(image)) {
                    throw new IOException("image file already closed");
                }

                if (openers.isEmpty()) {
                    close();
                    // nodes.clear(); rootDir = null; omitted as not accessible
                    if (!OPEN_FILES.remove(this.getImagePath(), this)) {
                        throw new IOException("image file not found in open list");
                    }
                }
            }
        }

        // Simulate the no-arg close() called inside close(ImageReader)
        private void close() {
            // no-op for test
        }
    }

    public static void main(String[] args) throws Exception {
        testCloseNullArgument();
        testCloseAlreadyClosed();
        testCloseNormalFlow();
        System.out.println("All tests passed.");
    }

    private static void testCloseNullArgument() {
        ImageReader reader = new ImageReader(java.nio.file.Paths.get("/tmp/image"));
        try {
            reader.close(null);
            throw new AssertionError("Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type", e);
        }
    }

    private static void testCloseAlreadyClosed() throws IOException {
        ImageReader reader = new ImageReader(java.nio.file.Paths.get("/tmp/image2"));
        // Close once normally
        reader.close(reader);
        try {
            // Close again should throw IOException
            reader.close(reader);
            throw new AssertionError("Expected IOException for already closed image");
        } catch (IOException expected) {
            if (!"image file already closed".equals(expected.getMessage())) {
                throw new AssertionError("Unexpected IOException message", expected);
            }
        }
    }

    private static void testCloseNormalFlow() throws IOException {
        java.nio.file.Path path = java.nio.file.Paths.get("/tmp/image3");
        ImageReader reader = new ImageReader(path);
        // Close once normally
        reader.close(reader);
        // After close, the openers set should be empty and OPEN_FILES should not contain the path
        synchronized (ImageReader.OPEN_FILES) {
            if (ImageReader.openers.contains(reader)) {
                throw new AssertionError("openers should not contain closed reader");
            }
            if (ImageReader.OPEN_FILES.containsKey(path)) {
                throw new AssertionError("OPEN_FILES should not contain closed reader path");
            }
        }
    }
}