/*
 * @test
 * @summary Test sun.net.sdp.SdpSupport.convertSocket(FileDescriptor) behavior
 * @run main SdpSupportWitupTest
 */

import java.io.FileDescriptor;
import java.io.IOException;
import sun.net.sdp.SdpSupport;

public class SdpSupportWitupTest {
    public static void main(String[] args) throws Exception {
        testConvertSocketUnsupportedPlatform();
        testConvertSocketValidCall();
    }

    private static void testConvertSocketUnsupportedPlatform() {
        // This test verifies that convertSocket throws UnsupportedOperationException
        // when SDP is not supported on the platform (isSupported == false).
        // Since isSupported is private static final, we cannot change it directly.
        // Instead, we check if the platform is supported; if supported, we skip this test.

        String osName = System.getProperty("os.name");
        boolean isSupported = "SunOS".equals(osName) || "Linux".equals(osName);

        if (!isSupported) {
            FileDescriptor fd = new FileDescriptor();
            try {
                SdpSupport.convertSocket(fd);
                throw new RuntimeException("Expected UnsupportedOperationException not thrown");
            } catch (UnsupportedOperationException e) {
                // Expected exception
            } catch (IOException e) {
                throw new RuntimeException("Unexpected IOException thrown", e);
            }
        } else {
            // Platform supported, skipping this test
        }
    }

    private static void testConvertSocketValidCall() throws IOException {
        // This test attempts to call convertSocket on a valid FileDescriptor.
        // We cannot create a real TCP socket fd here, so we test that no exception
        // is thrown from fdAccess.get(fd) and convert0(fdVal) call.
        // Since convert0 is native and may throw IOException, we catch it.

        // We create a new FileDescriptor (fdVal likely 0 or invalid),
        // but we expect convertSocket to attempt conversion.

        FileDescriptor fd = new FileDescriptor();
        try {
            SdpSupport.convertSocket(fd);
        } catch (UnsupportedOperationException e) {
            // If platform unsupported, this is expected, ignore
        } catch (IOException e) {
            // IOException may be thrown by native convert0, acceptable
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception thrown", e);
        }
    }
}
