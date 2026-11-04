/* @test    @summary Test registerCleanup method of FileDescriptor class    @run main FileDescriptorWitupTest */

import java.io.FileDescriptor;

public class FileDescriptorWitupTest {

    public static void main(String[] args) {
        testRegisterCleanupWithValidDescriptor();
        testRegisterCleanupWithNull();
    }

    private static void testRegisterCleanupWithValidDescriptor() {
        FileDescriptor fd = new FileDescriptor();
        try {
            fd.registerCleanup(fd);
            System.out.println("testRegisterCleanupWithValidDescriptor passed");
        } catch (Exception e) {
            System.err.println("testRegisterCleanupWithValidDescriptor failed: " + e);
        }
    }

    private static void testRegisterCleanupWithNull() {
        try {
            FileDescriptor fd = new FileDescriptor();
            fd.registerCleanup(null);
            System.out.println("testRegisterCleanupWithNull passed");
        } catch (NullPointerException e) {
            System.out.println("testRegisterCleanupWithNull passed (expected NullPointerException)");
        } catch (Exception e) {
            System.err.println("testRegisterCleanupWithNull failed: " + e);
        }
    }
}
