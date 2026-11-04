/*
 * @test    @summary Test the close() method of JrtFileSystem.
 * @run main JrtFileSystemWitupTest
 */
import java.io.IOException;
import jdk.internal.jrtfs.JrtFileSystem;

public class JrtFileSystemWitupTest {
    public static void main(String[] args) throws IOException {
        testCloseMethodWithClosable();
        testCloseMethodWithNotClosable();
    }

    static void testCloseMethodWithClosable() throws IOException {
        JrtFileSystem fs = new JrtFileSystem(null, null);
        // Force isClosable to true for this test
        java.lang.reflect.Field isClosableField;
        try {
            isClosableField = JrtFileSystem.class.getDeclaredField("isClosable");
            isClosableField.setAccessible(true);
            isClosableField.setBoolean(fs, true);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        fs.close(); // should not throw
        // After close, isOpen should still be true, as no state change in this stub
    }

    static void testCloseMethodWithNotClosable() {
        JrtFileSystem fs = new JrtFileSystem(null, null);
        // Force isClosable to false for this test
        try {
            java.lang.reflect.Field isClosableField = JrtFileSystem.class.getDeclaredField("isClosable");
            isClosableField.setAccessible(true);
            isClosableField.setBoolean(fs, false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        boolean exceptionThrown = false;
        try {
            fs.close();
        } catch (UnsupportedOperationException e) {
            exceptionThrown = true;
        } catch (IOException e) {
            // Not expected
            throw new RuntimeException(e);
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected UnsupportedOperationException to be thrown");
        }
    }
}
