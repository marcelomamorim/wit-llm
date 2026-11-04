/*
 * @test
 * @summary Verifies SdpSupport.convertSocket behavior: UnsupportedOperationException on unsupported platforms and NullPointerException for a null FileDescriptor on supported platforms
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

        if (!supported) {
            try {
                SdpSupport.convertSocket(null);
                throw new RuntimeException("Expected UnsupportedOperationException on " + os);
            } catch (UnsupportedOperationException expected) {
                // expected: platform check precedes any access to fd
            }
        } else {
            try {
                SdpSupport.convertSocket(null);
                throw new RuntimeException("Expected NullPointerException for null fd on " + os);
            } catch (NullPointerException expected) {
                // expected: fdAccess.get(null) dereferences the descriptor
            }
        }
    }
}
