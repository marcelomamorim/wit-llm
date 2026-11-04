/*
 * @test
 * @summary KeepAliveStream.reset() must throw IOException with the message "mark/reset not supported"
 * @modules java.base/sun.net.www.http
 * @run main KeepAliveStreamWitupTest
 */
import java.io.ByteArrayInputStream;
import java.io.IOException;
import sun.net.www.http.KeepAliveStream;

public class KeepAliveStreamWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] data = {1, 2, 3, 4};
        KeepAliveStream s = new KeepAliveStream(new ByteArrayInputStream(data), null, data.length, null);
        checkResetThrows(s);
        s.read();
        checkResetThrows(s);
    }

    static void checkResetThrows(KeepAliveStream s) {
        try {
            s.reset();
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("reset() did not throw IOException");
    }
}
