/*
 * @test
 * @summary Verifies that TimestampToken rejects a null timestampTokenInfo with an IOException
 * @modules java.base/sun.security.timestamp
 * @run main TimestampTokenWitupTest
 */

import java.io.IOException;
import sun.security.timestamp.TimestampToken;

public class TimestampTokenWitupTest {

    public static void main(String[] args) throws Exception {
        try {
            new TimestampToken(null);
            throw new RuntimeException("IOException expected for null input");
        } catch (IOException e) {
            if (!"No timestamp token info".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
    }
}
