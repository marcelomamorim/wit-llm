/*
 * @test
 * @summary Test for SecurityManager.checkWrite(FileDescriptor)
 * @run main SecurityManagerWitupTest
 */

import java.lang.SecurityManager;
import java.io.FileDescriptor;

public class SecurityManagerWitupTest {

    public static void main(String[] args) {
        testCheckWriteWithNull();
        testCheckWriteWithValidDescriptor();
    }

    private static void testCheckWriteWithNull() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkWrite(null);
            System.out.println("testCheckWriteWithNull: FAILED (no exception)");
        } catch (NullPointerException e) {
            System.out.println("testCheckWriteWithNull: PASSED");
        } catch (Throwable t) {
            System.out.println("testCheckWriteWithNull: FAILED (unexpected exception: " + t + ")");
        }
    }

    private static void testCheckWriteWithValidDescriptor() {
        SecurityManager sm = new SecurityManager();
        FileDescriptor fd = new FileDescriptor();
        try {
            sm.checkWrite(fd);
            System.out.println("testCheckWriteWithValidDescriptor: PASSED");
        } catch (Throwable t) {
            System.out.println("testCheckWriteWithValidDescriptor: FAILED (unexpected exception: " + t + ")");
        }
    }
}
