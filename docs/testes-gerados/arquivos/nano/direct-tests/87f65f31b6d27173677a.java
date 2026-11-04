/*
 * @test
 * @summary Test SecurityManager.checkRead(FileDescriptor) for null and valid FileDescriptor
 * @run main SecurityManagerWitupTest
 */

import java.lang.SecurityManager;
import java.io.FileDescriptor;

public class SecurityManagerWitupTest {

    public static void main(String[] args) {
        testCheckReadWithNull();
        testCheckReadWithValidFileDescriptor();
    }

    static void testCheckReadWithNull() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkRead((FileDescriptor) null);
            // Expecting NullPointerException
            System.out.println("FAIL: Expected NullPointerException for null FileDescriptor");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (SecurityException e) {
            System.out.println("FAIL: Unexpected SecurityException: " + e);
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    static void testCheckReadWithValidFileDescriptor() {
        SecurityManager sm = new SecurityManager();
        FileDescriptor fd = new FileDescriptor();
        try {
            sm.checkRead(fd);
            System.out.println("PASS: checkRead with valid FileDescriptor executed without exception");
        } catch (SecurityException e) {
            System.out.println("FAIL: SecurityException thrown: " + e);
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
