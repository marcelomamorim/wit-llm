/* @test    @summary Test createSocket method in SdpSupport for supported platform behavior    @run main SdpSupportWitupTest */

import java.io.FileDescriptor;
import java.io.IOException;

public class SdpSupportWitupTest {

    public static void main(String[] args) {
        testCreateSocketSupported();
        testCreateSocketUnsupported();
    }

    private static void testCreateSocketSupported() {
        // Simulate supported platform
        boolean originalIsSupported = getIsSupported();
        setIsSupported(true);
        try {
            FileDescriptor fd = sun.net.sdp.SdpSupport.createSocket();
            if (fd == null) {
                throw new AssertionError("Expected non-null FileDescriptor");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        } finally {
            setIsSupported(originalIsSupported);
        }
    }

    private static void testCreateSocketUnsupported() {
        // Simulate unsupported platform
        boolean originalIsSupported = getIsSupported();
        setIsSupported(false);
        try {
            try {
                sun.net.sdp.SdpSupport.createSocket();
                throw new AssertionError("Expected UnsupportedOperationException");
            } catch (UnsupportedOperationException e) {
                // Expected exception
            }
        } finally {
            setIsSupported(originalIsSupported);
        }
    }

    // Helper methods to manipulate isSupported flag for testing
    private static boolean getIsSupported() {
        try {
            java.lang.reflect.Field field = sun.net.sdp.SdpSupport.class.getDeclaredField("isSupported");
            field.setAccessible(true);
            return (Boolean) field.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void setIsSupported(boolean value) {
        try {
            java.lang.reflect.Field field = sun.net.sdp.SdpSupport.class.getDeclaredField("isSupported");
            field.setAccessible(true);
            field.set(null, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
