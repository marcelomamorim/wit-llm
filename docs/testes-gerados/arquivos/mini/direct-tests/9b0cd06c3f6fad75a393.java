/*
 * @test
 * @summary Test FileInputStream(FileDescriptor) constructor behavior
 * @run main FileInputStreamWitupTest
 */

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.lang.SecurityManager;

public class FileInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testNullFileDescriptor();
        testValidFileDescriptor();
        testSecurityManagerCheckReadCalled();
    }

    private static void testNullFileDescriptor() {
        try {
            new FileInputStream(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        } catch (Throwable t) {
            throw new RuntimeException("Unexpected exception", t);
        }
    }

    private static void testValidFileDescriptor() throws Exception {
        FileDescriptor fd = new FileDescriptor();
        FileInputStream fis = new FileInputStream(fd);
        if (fis == null) {
            throw new RuntimeException("FileInputStream constructor returned null");
        }
        // The path field is private and null, no public getter, so just test no exception and non-null instance
        fis.close();
    }

    private static void testSecurityManagerCheckReadCalled() throws Exception {
        final boolean[] checkReadCalled = {false};
        SecurityManager original = System.getSecurityManager();
        SecurityManager testSM = new SecurityManager() {
            @Override
            public void checkRead(FileDescriptor fd) {
                checkReadCalled[0] = true;
                // allow
            }
            @Override
            public void checkPermission(java.security.Permission perm) {
                // allow all other permissions
            }
        };
        System.setSecurityManager(testSM);
        try {
            FileDescriptor fd = new FileDescriptor();
            new FileInputStream(fd);
            if (!checkReadCalled[0]) {
                throw new RuntimeException("SecurityManager.checkRead(FileDescriptor) was not called");
            }
        } finally {
            System.setSecurityManager(original);
        }
    }
}
