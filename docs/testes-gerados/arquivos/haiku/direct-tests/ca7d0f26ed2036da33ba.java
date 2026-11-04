/*
 * @test
 * @summary Verifies SdpSupport.createSocket() behavior on supported and unsupported platforms
 * @modules java.base/sun.net.sdp
 * @run main SdpSupportWitupTest
 */

import java.io.FileDescriptor;
import java.io.IOException;
import sun.net.sdp.SdpSupport;

public class SdpSupportWitupTest {
    public static void main(String[] args) throws Exception {
        String os = System.getProperty("os.name");
        boolean supported = os.equals("SunOS") || os.equals("Linux");
        if (supported) {
            try {
                FileDescriptor fd = SdpSupport.createSocket();
                if (fd == null) {
                    throw new RuntimeException("createSocket returned null");
                }
            } catch (IOException e) {
                // Native SDP may be unavailable in this environment; acceptable.
            }
        } else {
            try {
                SdpSupport.createSocket();
                throw new RuntimeException("Expected UnsupportedOperationException on " + os);
            } catch (UnsupportedOperationException e) {
                if (!"SDP not supported on this platform".equals(e.getMessage())) {
                    throw new RuntimeException("Unexpected message: " + e.getMessage());
                }
            }
        }
    }
}
