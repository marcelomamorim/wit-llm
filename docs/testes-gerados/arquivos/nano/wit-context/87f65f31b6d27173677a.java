/*
 * @test
 * @summary Test for SecurityManager.checkRead(FileDescriptor)
 * @run main SecurityManagerWitupTest
 */

import java.lang.SecurityManager;
import java.io.FileDescriptor;

public class SecurityManagerWitupTest {

    public static void main(String[] args) {
        testCheckReadWithNull();
        testCheckReadWithValidDescriptor();
    }

    private static void testCheckReadWithNull() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkRead(null);
            System.out.println("testCheckReadWithNull: FAILED (no exception)");
        } catch (NullPointerException e) {
            System.out.println("testCheckReadWithNull: PASSED");
        } catch (Throwable t) {
            System.out.println("testCheckReadWithNull: FAILED (unexpected exception: " + t + ")");
        }
    }

    private static void testCheckReadWithValidDescriptor() {
        SecurityManager sm = new SecurityManager();
        try {
            FileDescriptor fd = new FileDescriptor();
            sm.checkRead(fd);
            System.out.println("testCheckReadWithValidDescriptor: PASSED");
        } catch (Throwable t) {
            System.out.println("testCheckReadWithValidDescriptor: FAILED (unexpected exception: " + t + ")");
        }
    }
}
