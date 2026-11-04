/*
 * @test
 * @summary Test that JrtPath.toFile() throws UnsupportedOperationException
 * @run main JrtPathWitupTest
 */

import jdk.internal.jrtfs.JrtPath;
import java.io.File;

public class JrtPathWitupTest {
    public static void main(String[] args) {
        // Create a minimal JrtPath instance with dummy parameters
        // Since JrtFileSystem is not provided, use null for jrtfs and a dummy path
        JrtPath path = new JrtPath(null, "/dummy");
        try {
            File f = path.toFile();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception
        }
    }
}
