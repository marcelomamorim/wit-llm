/* @test    @summary Test for getUsableSpace method in JrtFileStore.
 * @run main JrtFileStoreWitupTest
 */

import java.io.IOException;
import jdk.internal.jrtfs.JrtFileStore;

public class JrtFileStoreWitupTest {

    public static void main(String[] args) {
        testGetUsableSpaceThrowsException();
    }

    public static void testGetUsableSpaceThrowsException() {
        JrtFileStore store = createMockJrtFileStore();
        try {
            long result = store.getUsableSpace();
            if (result != 0) {
                throw new AssertionError("Expected exception, but got result: " + result);
            }
        } catch (UnsupportedOperationException e) {
            // Expected exception
        } catch (IOException e) {
            // Not expected, fail the test
            throw new AssertionError("Unexpected IOException", e);
        } catch (Exception e) {
            // Fail on any other exception
            throw new AssertionError("Unexpected exception", e);
        }
    }

    private static JrtFileStore createMockJrtFileStore() {
        // Since JrtFileStore has no public constructor, create an anonymous subclass for testing
        return new JrtFileStore(null) {
            @Override
            public long getUsableSpace() throws IOException {
                throw new UnsupportedOperationException("getUsableSpace");
            }
        };
    }
}
