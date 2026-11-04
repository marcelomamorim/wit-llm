/* @test    @summary Test createSocket method of SdpSupport class    @run main SdpSupportWitupTest */

import java.io.FileDescriptor;
import java.io.IOException;

public class SdpSupportWitupTest {

    public static void main(String[] args) throws Exception {
        testCreateSocketSupportedPlatform();
        testCreateSocketUnsupportedPlatform();
    }

    private static void testCreateSocketSupportedPlatform() {
        // Simulate supported platform
        try {
            // Save original isSupported value
            boolean originalSupport = getIsSupported();
            setIsSupported(true);
            FileDescriptor fd = null;
            try {
                fd = sun.net.sdp.SdpSupport.createSocket();
                assert fd != null : "FileDescriptor should not be null";
            } catch (IOException e) {
                throw new AssertionError("Unexpected IOException: " + e.getMessage());
            } finally {
                setIsSupported(originalSupport);
            }
        } catch (Exception e) {
            throw new AssertionError("Failed in supported platform test: " + e.getMessage(), e);
        }
    }

    private static void testCreateSocketUnsupportedPlatform() {
        // Simulate unsupported platform
        try {
            boolean originalSupport = getIsSupported();
            setIsSupported(false);
            try {
                sun.net.sdp.SdpSupport.createSocket();
                throw new AssertionError("Expected UnsupportedOperationException was not thrown");
            } catch (UnsupportedOperationException e) {
                // Expected exception
            } finally {
                setIsSupported(originalSupport);
            }
        } catch (Exception e) {
            throw new AssertionError("Failed in unsupported platform test: " + e.getMessage(), e);
        }
    }

    // Reflection helpers to manipulate isSupported static field
    private static boolean getIsSupported() {
        try {
            java.lang.reflect.Field field = sun.net.sdp.SdpSupport.class.getDeclaredField("isSupported");
            field.setAccessible(true);
            return field.getBoolean(null);
        } catch (Exception e) {
            throw new RuntimeException("Unable to access isSupported field", e);
        }
    }

    private static void setIsSupported(boolean value) {
        try {
            java.lang.reflect.Field field = sun.net.sdp.SdpSupport.class.getDeclaredField("isSupported");
            field.setAccessible(true);
            field.setBoolean(null, value);
        } catch (Exception e) {
            throw new RuntimeException("Unable to set isSupported field", e);
        }
    }
}
