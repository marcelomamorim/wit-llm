/*
 * @test
 * @summary Test toFile() method of JrtPath throws UnsupportedOperationException
 * @run main JrtPathWitupTest
 */

import jdk.internal.jrtfs.JrtPath;
import java.io.File;

public class JrtPathWitupTest {
    public static void main(String[] args) {
        // Create a minimal JrtPath instance using a dummy JrtFileSystem
        // Since JrtFileSystem constructor or factory is not visible, use null and empty path
        // to test toFile() which does not depend on internal state
        JrtPath path = new JrtPath(null, "");
        try {
            path.toFile();
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // Test passed
        }
    }
}
