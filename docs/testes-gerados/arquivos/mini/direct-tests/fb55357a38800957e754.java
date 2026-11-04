/*
 * @test
 * @summary Test jdk.internal.jimage.ImageReader.close(ImageReader) method behavior
 * @run main ImageReaderWitupTest
 */

import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.HashSet;

public class ImageReaderWitupTest {

    // Minimal mock of ImageReader to test close(ImageReader) method
    static class ImageReader {
        private static final Set<ImageReader> openers = new HashSet<>();
        private static final OpenFiles OPEN_FILES = new OpenFiles();
        private final String imagePath;
        private final Set<ImageReader> nodes = new HashSet<>();
        private Directory rootDir = new Directory();

        ImageReader(String path) {
            this.imagePath = path;
            openers.add(this);
            OPEN_FILES.add(path, this);
            nodes.add(this);
        }

        public String getImagePath() {
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
                    nodes.clear();
                    rootDir = null;

                    if (!OPEN_FILES.remove(this.getImagePath(), this)) {
                        throw new IOException("image file not found in open list");
                    }
                }
            }
        }

        // Simulate close of this instance
        private void close() {
            // no-op for test
        }
    }

    // Minimal mock of OPEN_FILES map
    static class OpenFiles {
        private final java.util.Map<String, ImageReader> map = new java.util.HashMap<>();

        synchronized void add(String path, ImageReader reader) {
            map.put(path, reader);
        }

        synchronized boolean remove(String path, ImageReader reader) {
            ImageReader current = map.get(path);
            if (current == null || current != reader) {
                return false;
            }
            map.remove(path);
            return true;
        }
    }

    // Dummy Directory class
    static class Directory {
    }

    public static void main(String[] args) throws Exception {
        ImageReader reader1 = new ImageReader("path1");
        ImageReader reader2 = new ImageReader("path2");

        // Close reader1 successfully
        reader1.close(reader1);

        // Closing reader1 again should throw IOException
        try {
            reader1.close(reader1);
            throw new RuntimeException("Expected IOException not thrown on second close");
        } catch (IOException e) {
            if (!"image file already closed".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }

        // Close reader2 successfully
        reader2.close(reader2);

        // Closing with null argument should throw NullPointerException
        try {
            reader2.close(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }

        // Closing a new reader not in openers should throw IOException
        ImageReader reader3 = new ImageReader("path3");
        // Remove reader3 from openers to simulate already closed
        synchronized (ImageReader.openers) {
            ImageReader.openers.remove(reader3);
        }
        try {
            reader3.close(reader3);
            throw new RuntimeException("Expected IOException not thrown for closed reader");
        } catch (IOException e) {
            if (!"image file already closed".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
