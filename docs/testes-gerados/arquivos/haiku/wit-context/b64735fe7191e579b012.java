/*
 * @test
 * @summary Smoke test for public construction of FileDescriptor; registerCleanup(FileDescriptor) is not publicly reachable
 * @run main FileDescriptorWitupTest
 */

public class FileDescriptorWitupTest {

    public static void main(String[] args) {
        FileDescriptor fd = new FileDescriptor();
        if (fd == null) {
            throw new RuntimeException("FileDescriptor construction returned null");
        }
    }
}
