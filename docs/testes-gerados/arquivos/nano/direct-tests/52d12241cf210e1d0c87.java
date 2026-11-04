/* @test    @summary Test SecurityManager.checkWrite(FileDescriptor) for null and valid input    @run main SecurityManagerWitupTest */

import java.io.FileDescriptor;
import java.lang.SecurityManager;

public class SecurityManagerWitupTest {

    public static void main(String[] args) {
        testCheckWriteWithNull();
        testCheckWriteWithValid();
    }

    static void testCheckWriteWithNull() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkWrite(null);
            System.out.println("FAIL: Expected NullPointerException for null FileDescriptor");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    static void testCheckWriteWithValid() {
        SecurityManager sm = new SecurityManager();
        FileDescriptor fd = new FileDescriptor();
        try {
            sm.checkWrite(fd);
            System.out.println("PASS: checkWrite with valid FileDescriptor completed without exception");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
