/*
 * @test
 * @summary Test FileDescriptor.registerCleanup(FileDescriptor) for NPE on null argument
 * @run main FileDescriptorWitupTest
 */

import java.io.FileDescriptor;
import java.io.IOException;

public class FileDescriptorWitupTest {
    public static void main(String[] args) throws IOException {
        FileDescriptor fd = new FileDescriptor();

        // Test normal usage: registerCleanup should not throw when fd is non-null
        fd.registerCleanup(fd);

        // Test that registerCleanup throws NullPointerException when argument is null
        boolean npeThrown = false;
        try {
            fd.registerCleanup(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }

        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when calling registerCleanup with null argument");
        }

        System.out.println("FileDescriptor.registerCleanup test passed.");
    }
}
