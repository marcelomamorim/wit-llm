/*
 * @test
 * @summary Verifies that SecurityManager.checkWrite(FileDescriptor) rejects a null file descriptor with a NullPointerException and the documented message
 * @run main SecurityManagerWitupTest
 */

import java.io.FileDescriptor;

public class SecurityManagerWitupTest {
    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkWrite((FileDescriptor) null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException e) {
            String msg = e.getMessage();
            if (!"file descriptor can't be null".equals(msg)) {
                throw new RuntimeException("unexpected message: " + msg);
            }
        }
    }
}
