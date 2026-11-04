/*
 * @test
 * @summary Tests the behavior of FilePermission constructor with various inputs.
 * @run main FilePermissionWitupTest
 */

import java.io.FilePermission;

public class FilePermissionWitupTest {

    public static void main(String[] args) {
        testValidActions();
        testNullActions();
        testInvalidActions();
    }

    static void testValidActions() {
        try {
            FilePermission fpRead = new FilePermission("/tmp/file", "read");
            FilePermission fpWrite = new FilePermission("/tmp/file", "write");
            FilePermission fpExec = new FilePermission("/tmp/file", "execute");
            FilePermission fpDelete = new FilePermission("/tmp/file", "delete");
            FilePermission fpReadLink = new FilePermission("/tmp/file", "readlink");
        } catch (Exception e) {
            throw new AssertionError("Valid actions should not throw", e);
        }
    }

    static void testNullActions() {
        try {
            new FilePermission("/tmp/file", null);
            throw new AssertionError("Null actions should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Null actions threw unexpected exception", e);
        }
    }

    static void testInvalidActions() {
        try {
            new FilePermission("/tmp/file", "invalidAction");
            // Since the constructor calls init(getMask(actions)), which may throw or set mask to 0,
            // we check if the object is created without exception.
            // If init throws, the test will fail.
        } catch (IllegalArgumentException e) {
            // expected if init throws
        } catch (Exception e) {
            throw new AssertionError("Invalid actions threw unexpected exception", e);
        }
    }
}
