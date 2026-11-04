/*
 * @test
 * @summary Verifies that KeepAliveStream.reset() always throws IOException with the documented message
 * @modules java.base/sun.net.www.http
 * @run main KeepAliveStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import sun.net.www.http.KeepAliveStream;

public class KeepAliveStreamWitupTest {

    public static void main(String[] args) throws Exception {
        KeepAliveStream s = new KeepAliveStream(
                new ByteArrayInputStream(new byte[]{1, 2, 3}), null, 3L, null);
        expectResetFailure(s);
        expectResetFailure(s);
    }

    static void expectResetFailure(KeepAliveStream s) {
        try {
            s.reset();
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage(), e);
            }
            return;
        }
        throw new RuntimeException("reset() did not throw IOException");
    }
}
