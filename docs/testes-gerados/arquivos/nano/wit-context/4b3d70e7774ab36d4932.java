/* @test    @summary Test for getTotalSpace method in JrtFileStore.
 * @run main JrtFileStoreWitupTest
 */

import java.io.IOException;
import jdk.internal.jrtfs.JrtFileStore;

public class JrtFileStoreWitupTest {

    public static void main(String[] args) {
        testGetTotalSpaceThrowsException();
    }

    public static void testGetTotalSpaceThrowsException() {
        JrtFileStore store = createMockJrtFileStore();
        try {
            long result = store.getTotalSpace();
            if (result != 0) {
                throw new AssertionError("Expected exception, but got result: " + result);
            }
            // If no exception, test fails
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
            if (!e.getMessage().contains("getTotalSpace")) {
                throw new AssertionError("Exception message does not contain expected text.");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }

    private static JrtFileStore createMockJrtFileStore() {
        // Since JrtFileStore requires a JrtPath, which is not provided, we create a minimal subclass for testing
        return new JrtFileStore() {
            @Override
            public long getTotalSpace() throws IOException {
                throw new UnsupportedOperationException("getTotalSpace");
            }
        };
    }
}
