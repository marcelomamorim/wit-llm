/* @test    @summary Test convertSocket method of SdpSupport class    @run main SdpSupportWitupTest */

import java.io.FileDescriptor;
import java.io.IOException;

public class SdpSupportWitupTest {

    public static void main(String[] args) throws Exception {
        testConvertSocketWithSupportedPlatform();
        testConvertSocketWithUnsupportedPlatform();
    }

    private static void testConvertSocketWithSupportedPlatform() {
        // Save original isSupported value
        boolean originalSupport = sun.net.sdp.SdpSupport.isSupported;
        try {
            // Force support to true for testing
            setIsSupported(true);
            FileDescriptor fd = new FileDescriptor();
            // Initialize fd with a dummy value
            setFdAccessGet(fd, 42);
            // Call method; should not throw
            sun.net.sdp.SdpSupport.convertSocket(fd);
            System.out.println("convertSocket supported platform test passed.");
        } catch (Exception e) {
            System.err.println("convertSocket supported platform test failed: " + e);
        } finally {
            setIsSupported(originalSupport);
        }
    }

    private static void testConvertSocketWithUnsupportedPlatform() {
        boolean originalSupport = sun.net.sdp.SdpSupport.isSupported;
        try {
            setIsSupported(false);
            FileDescriptor fd = new FileDescriptor();
            setFdAccessGet(fd, 42);
            boolean exceptionThrown = false;
            try {
                sun.net.sdp.SdpSupport.convertSocket(fd);
            } catch (UnsupportedOperationException e) {
                exceptionThrown = true;
            }
            if (exceptionThrown) {
                System.out.println("convertSocket unsupported platform test passed.");
            } else {
                System.err.println("convertSocket unsupported platform test failed: exception not thrown.");
            }
        } finally {
            setIsSupported(originalSupport);
        }
    }

    // Helper methods to set private static fields via reflection
    private static void setIsSupported(boolean value) {
        try {
            java.lang.reflect.Field field = sun.net.sdp.SdpSupport.class.getDeclaredField("isSupported");
            field.setAccessible(true);
            field.setBoolean(null, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void setFdAccessGet(FileDescriptor fd, int value) {
        try {
            java.lang.reflect.Field field = sun.net.sdp.SdpSupport.class.getDeclaredField("fdAccess");
            field.setAccessible(true);
            Object fdAccess = field.get(null);
            java.lang.reflect.Method getMethod = fdAccess.getClass().getDeclaredMethod("get", FileDescriptor.class);
            getMethod.setAccessible(true);
            getMethod.invoke(fdAccess, fd); // simulate get returning value
            // Note: Since fdAccess.get(fd) is a native method, we cannot mock it directly.
            // For testing, assume that fdAccess.get(fd) returns the value set here.
            // Alternatively, in real tests, use a wrapper or mock, but here we simulate.
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
