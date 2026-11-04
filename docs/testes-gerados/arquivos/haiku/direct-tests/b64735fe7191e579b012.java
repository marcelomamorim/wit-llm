/*
 * @test
 * @summary Smoke test that FileDescriptor cleanup registration via SharedSecrets accepts a new descriptor and can be unregistered
 * @modules java.base/jdk.internal.misc
 * @run main FileDescriptorWitupTest
 */

import java.io.FileDescriptor;
import jdk.internal.misc.JavaIOFileDescriptorAccess;
import jdk.internal.misc.SharedSecrets;

public class FileDescriptorWitupTest {
    public static void main(String[] args) {
        JavaIOFileDescriptorAccess access = SharedSecrets.getJavaIOFileDescriptorAccess();
        if (access == null) {
            throw new RuntimeException("JavaIOFileDescriptorAccess is null");
        }
        FileDescriptor fd = new FileDescriptor();
        access.registerCleanup(fd);
        access.unregisterCleanup(fd);
        access.registerCleanup(fd);
        access.unregisterCleanup(fd);
    }
}
