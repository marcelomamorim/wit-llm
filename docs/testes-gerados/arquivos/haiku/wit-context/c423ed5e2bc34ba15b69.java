/*
 * @test
 * @summary Verifies that SdpSupport.convertSocket throws UnsupportedOperationException on platforms where SDP is not supported
 * @modules java.base/sun.net.sdp
 * @run main SdpSupportWitupTest
 */

import java.io.FileDescriptor;
import sun.net.sdp.SdpSupport;

public class SdpSupportWitupTest {

    public static void main(String[] args) throws Exception {
        String os = System.getProperty("os.name");
        boolean supported = os.equals("SunOS") || os.equals("Linux");
        if (!supported) {
            try {
                SdpSupport.convertSocket(new FileDescriptor());
                throw new AssertionError("Expected UnsupportedOperationException on " + os);
            } catch (UnsupportedOperationException e) {
                String msg = e.getMessage();
                if (msg == null || !msg.contains("SDP")) {
                    throw new AssertionError("Unexpected message: " + msg);
                }
            }
        }
    }
}
