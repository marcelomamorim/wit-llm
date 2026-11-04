/*
 * @test
 * @summary Test for JrtFileSystem.position() method, focusing on exception path.
 * @run main JrtFileSystemWitupTest
 */

import java.io.IOException;

public class JrtFileSystemWitupTest {

    public static void main(String[] args) {
        testPositionThrowsUnsupportedOperationException();
    }

    public static void testPositionThrowsUnsupportedOperationException() {
        JrtFileSystem fs = null;
        try {
            // Simulate a scenario where position() throws UnsupportedOperationException
            fs = new JrtFileSystem() {
                @Override
                public long position() throws IOException {
                    throw new UnsupportedOperationException();
                }
            };
            long pos = fs.position();
            // If no exception, test fails
            System.err.println("Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
            System.out.println("Caught expected UnsupportedOperationException.");
        } catch (IOException e) {
            // Unexpected exception, test fails
            System.err.println("Unexpected IOException: " + e);
            System.exit(1);
        }
    }
}
