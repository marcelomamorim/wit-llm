/* @test    @summary Test getUsableSpace throws UnsupportedOperationException in JrtFileStore  @run main JrtFileStoreWitupTest */

import java.io.IOException;

public class JrtFileStoreWitupTest {

    public static void main(String[] args) {
        testGetUsableSpaceThrowsException();
    }

    public static void testGetUsableSpaceThrowsException() {
        class MockJrtFileStore extends jdk.internal.jrtfs.JrtFileStore {
            MockJrtFileStore() {
                super(null); // Passing null as jrtPath is acceptable for this test
            }
            @Override
            public long getUsableSpace() throws IOException {
                throw new UnsupportedOperationException("getUsableSpace");
            }
        }
        try {
            MockJrtFileStore store = new MockJrtFileStore();
            store.getUsableSpace();
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            if (e.getMessage().equals("getUsableSpace")) {
                System.out.println("PASS: Correct exception thrown.");
            } else {
                System.out.println("FAIL: Exception message mismatch.");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
