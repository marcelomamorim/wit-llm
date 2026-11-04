/*
 * @test
 * @summary Test for FileDescriptor.registerCleanup(FileDescriptor)
 * @run main FileDescriptorWitupTest
 */

import java.io.FileDescriptor;

public class FileDescriptorWitupTest {

    public static void main(String[] args) {
        testRegisterCleanupWithNull();
        testRegisterCleanupWithValidDescriptor();
    }

    private static void testRegisterCleanupWithNull() {
        try {
            FileDescriptor fd = new FileDescriptor();
            fd.registerCleanup(null);
            System.out.println("testRegisterCleanupWithNull: FAILED (no exception)");
        } catch (NullPointerException e) {
            System.out.println("testRegisterCleanupWithNull: PASSED");
        } catch (Throwable t) {
            System.out.println("testRegisterCleanupWithNull: FAILED (unexpected exception: " + t + ")");
        }
    }

    private static void testRegisterCleanupWithValidDescriptor() {
        try {
            FileDescriptor fd1 = new FileDescriptor();
            FileDescriptor fd2 = new FileDescriptor();
            fd1.registerCleanup(fd2);
            System.out.println("testRegisterCleanupWithValidDescriptor: PASSED");
        } catch (Throwable t) {
            System.out.println("testRegisterCleanupWithValidDescriptor: FAILED (exception: " + t + ")");
        }
    }
}
