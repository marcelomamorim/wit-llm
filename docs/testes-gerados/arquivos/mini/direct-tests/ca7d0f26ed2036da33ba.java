/*
 * @test
 * @summary Test sun.net.sdp.SdpSupport.createSocket() behavior
 * @run main SdpSupportWitupTest
 */

import java.io.FileDescriptor;
import java.io.IOException;
import sun.net.sdp.SdpSupport;

public class SdpSupportWitupTest {
    public static void main(String[] args) throws Exception {
        try {
            FileDescriptor fd = SdpSupport.createSocket();
            if (fd == null) {
                throw new RuntimeException("createSocket() returned null FileDescriptor");
            }
            // The fd should have a valid int value set internally, but we cannot assert it directly
            // because fdAccess is not public. We just check that no exception was thrown and fd is not null.
        } catch (UnsupportedOperationException e) {
            String msg = e.getMessage();
            if (!"SDP not supported on this platform".equals(msg)) {
                throw new RuntimeException("Unexpected UnsupportedOperationException message: " + msg);
            }
            // Expected on unsupported platforms
        } catch (IOException e) {
            // IOException may be thrown by native create0(), accept as valid
        }
    }
}
