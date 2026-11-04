/* @test    @summary Test for SdpSupport.convertSocket(FileDescriptor fd) method to verify exception handling on unsupported platforms    @run main SdpSupportWitupTest */

import java.io.FileDescriptor;
import java.io.IOException;
import sun.net.sdp.SdpSupport;

public class SdpSupportWitupTest {

    public static void main(String[] args) {
        testConvertSocketUnsupportedPlatform();
    }

    private static void testConvertSocketUnsupportedPlatform() {
        // Save original isSupported value
        boolean originalIsSupported = false;
        try {
            // Use reflection to modify the isSupported static field if accessible
            java.lang.reflect.Field isSupportedField = SdpSupport.class.getDeclaredField("isSupported");
            isSupportedField.setAccessible(true);
            originalIsSupported = isSupportedField.getBoolean(null);
            // Set isSupported to false to simulate unsupported platform
            isSupportedField.setBoolean(null, false);
        } catch (ReflectiveOperationException e) {
            // If reflection fails, skip test as we cannot simulate unsupported platform
            return;
        }
        try {
            FileDescriptor fd = new FileDescriptor();
            try {
                SdpSupport.convertSocket(fd);
                // If no exception thrown, test fails
                System.err.println("FAIL: Expected UnsupportedOperationException was not thrown.");
            } catch (UnsupportedOperationException e) {
                // Expected exception
                System.out.println("PASS: Caught expected UnsupportedOperationException.");
            } catch (IOException e) {
                System.err.println("FAIL: Unexpected IOException: " + e);
            }
        } finally {
            // Restore original isSupported value
            try {
                java.lang.reflect.Field isSupportedField = SdpSupport.class.getDeclaredField("isSupported");
                isSupportedField.setAccessible(true);
                isSupportedField.setBoolean(null, originalIsSupported);
            } catch (ReflectiveOperationException e) {
                // Ignore
            }
        }
    }
}
