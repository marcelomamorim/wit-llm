/*
 * @test
 * @summary Test for JrtFileSystem.close() method, focusing on exception path when isClosable is false.
 * @run main JrtFileSystemWitupTest
 */

import java.io.IOException;
import jdk.internal.jrtfs.JrtFileSystem;

public class JrtFileSystemWitupTest {

    public static void main(String[] args) throws Exception {
        testCloseThrowsWhenNotClosable();
        testCloseSucceedsWhenClosable();
    }

    private static void testCloseThrowsWhenNotClosable() {
        JrtFileSystem fs = new JrtFileSystem(null, null) {
            @Override
            public boolean isOpen() { return true; }
            @Override
            public void cleanup() { /* no-op */ }
        };
        // Force isClosable to false
        try {
            java.lang.reflect.Field isClosableField = JrtFileSystem.class.getDeclaredField("isClosable");
            isClosableField.setAccessible(true);
            isClosableField.setBoolean(fs, false);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        try {
            fs.close();
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }

    private static void testCloseSucceedsWhenClosable() throws IOException {
        JrtFileSystem fs = new JrtFileSystem(null, null) {
            @Override
            public boolean isOpen() { return true; }
            @Override
            public void cleanup() { /* no-op */ }
        };
        // Force isClosable to true
        try {
            java.lang.reflect.Field isClosableField = JrtFileSystem.class.getDeclaredField("isClosable");
            isClosableField.setAccessible(true);
            isClosableField.setBoolean(fs, true);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        // Should not throw
        fs.close();
    }
}
