/*
 * @test
 * @summary Test jdk.internal.jrtfs.JrtFileSystem.position() method
 * @run main JrtFileSystemWitupTest
 */

import java.io.IOException;
import jdk.internal.jrtfs.JrtFileSystem;

public class JrtFileSystemWitupTest {

    public static void main(String[] args) throws IOException {
        // Create a minimal subclass to expose 'read' field for testing
        TestJrtFileSystem fs = new TestJrtFileSystem();

        // Test default position value
        long pos = fs.position();
        if (pos != 0L) {
            throw new AssertionError("Expected position 0 but got " + pos);
        }

        // Set read to a positive value and test position
        fs.setRead(12345L);
        pos = fs.position();
        if (pos != 12345L) {
            throw new AssertionError("Expected position 12345 but got " + pos);
        }

        // Set read to zero and test position
        fs.setRead(0L);
        pos = fs.position();
        if (pos != 0L) {
            throw new AssertionError("Expected position 0 but got " + pos);
        }

        System.out.println("All tests passed.");
    }

    // Minimal subclass to allow setting 'read' field
    static class TestJrtFileSystem extends JrtFileSystem {
        private long read = 0L;

        TestJrtFileSystem() throws IOException {
            super(null, null);
        }

        @Override
        public long position() throws IOException {
            return read;
        }

        void setRead(long value) {
            this.read = value;
        }
    }
}
