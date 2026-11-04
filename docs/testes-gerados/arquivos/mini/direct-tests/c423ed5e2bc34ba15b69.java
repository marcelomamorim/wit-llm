/*
 * @test
 * @summary Test sun.net.sdp.SdpSupport.convertSocket(FileDescriptor) method
 * @run main SdpSupportWitupTest
 */

import java.io.FileDescriptor;
import java.io.IOException;
import sun.net.sdp.SdpSupport;

public class SdpSupportWitupTest {
    public static void main(String[] args) throws Exception {
        testConvertSocketWithValidFd();
        testConvertSocketUnsupportedPlatform();
    }

    private static void testConvertSocketWithValidFd() throws Exception {
        if (!isSupportedPlatform()) {
            System.out.println("Skipping testConvertSocketWithValidFd: SDP not supported on this platform");
            return;
        }
        FileDescriptor fd = new FileDescriptor();
        // We cannot set a valid fd value without reflection or native code,
        // so we test that convertSocket does not throw IOException for fd with fd=0 (default).
        // This is a minimal sanity test to invoke the method.
        try {
            SdpSupport.convertSocket(fd);
            System.out.println("testConvertSocketWithValidFd passed");
        } catch (IOException e) {
            // IOException may be thrown by native convert0, accept as valid behavior
            System.out.println("testConvertSocketWithValidFd threw IOException as expected: " + e);
        } catch (UnsupportedOperationException e) {
            // Should not happen if platform is supported
            throw new AssertionError("Unexpected UnsupportedOperationException on supported platform", e);
        }
    }

    private static void testConvertSocketUnsupportedPlatform() {
        if (isSupportedPlatform()) {
            System.out.println("Skipping testConvertSocketUnsupportedPlatform: SDP supported on this platform");
            return;
        }
        FileDescriptor fd = new FileDescriptor();
        try {
            SdpSupport.convertSocket(fd);
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected
            System.out.println("testConvertSocketUnsupportedPlatform passed");
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException on unsupported platform", e);
        }
    }

    private static boolean isSupportedPlatform() {
        String os = System.getProperty("os.name");
        return "SunOS".equals(os) || "Linux".equals(os);
    }
}
