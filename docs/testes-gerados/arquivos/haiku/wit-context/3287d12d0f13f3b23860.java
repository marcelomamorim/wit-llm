/*
 * @test
 * @summary Checks that HttpTimestamper.generateTimestamp reports an IOException when the TSA cannot be contacted
 * @modules java.base/sun.security.timestamp
 * @run main HttpTimestamperWitupTest
 */

import java.io.IOException;
import java.net.URI;
import sun.security.timestamp.HttpTimestamper;

public class HttpTimestamperWitupTest {

    public static void main(String[] args) throws Exception {
        URI uri = URI.create("http://127.0.0.1:1/tsa");
        HttpTimestamper timestamper = new HttpTimestamper(uri);
        boolean ioException = false;
        try {
            timestamper.generateTimestamp(null);
        } catch (IOException e) {
            ioException = true;
        }
        if (!ioException) {
            throw new RuntimeException("Expected IOException when the TSA is unreachable");
        }
    }
}
