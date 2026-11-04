/*
 * @test
 * @summary Test FileDescriptor.registerCleanup(FileDescriptor) method
 * @run main FileDescriptorWitupTest
 */

import java.io.FileDescriptor;
import java.io.IOException;

public class FileDescriptorWitupTest {
    public static void main(String[] args) throws IOException {
        FileDescriptor fd = new FileDescriptor();
        // The registerCleanup method calls FileCleanable.register(fd).
        // We test that calling registerCleanup does not throw and accepts a valid FileDescriptor.
        fd.registerCleanup(fd);
        // No exception means pass.
        System.out.println("PASS");
    }
}
