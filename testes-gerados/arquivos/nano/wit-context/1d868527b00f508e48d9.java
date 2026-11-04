/* @test    @summary Test for RandomAccessFile.getFD() method to verify exception throwing when fd is null    @run main RandomAccessFileWitupTest */

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileWitupTest {

    public static void main(String[] args) {
        testGetFDThrowsIOExceptionWhenFdIsNull();
    }

    private static void testGetFDThrowsIOExceptionWhenFdIsNull() {
        RandomAccessFile raf = null;
        try {
            // Create an anonymous subclass to override fd to null
            raf = new RandomAccessFile("dummy", "r") {
                @Override
                public final FileDescriptor getFD() throws IOException {
                    // Force fd to null to trigger exception
                    java.lang.reflect.Field fdField;
                    try {
                        fdField = RandomAccessFile.class.getDeclaredField("fd");
                        fdField.setAccessible(true);
                        fdField.set(this, null);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new IOException(e);
                    }
                    return super.getFD();
                }
            };
            // Invoke getFD to verify exception is thrown
            try {
                raf.getFD();
                // Fail if no exception
                System.err.println("Expected IOException was not thrown.");
                System.exit(1);
            } catch (IOException e) {
                // Expected exception
                System.out.println("IOException correctly thrown when fd is null.");
            }
        } catch (IOException e) {
            // Handle creation exceptions
            System.err.println("Failed to create RandomAccessFile: " + e);
            System.exit(1);
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    // Ignore
                }
            }
        }
    }
}
