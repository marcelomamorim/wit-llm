/*
 * @test
 * @summary Verifies that the TimestampToken(byte[]) constructor rejects a null buffer with an IOException carrying the documented message.
 * @modules java.base/sun.security.timestamp
 * @run main TimestampTokenWitupTest
 */
import java.io.IOException;
import sun.security.timestamp.TimestampToken;

public class TimestampTokenWitupTest {
    public static void main(String[] args) throws Exception {
        testNullInputThrowsIOException();
    }

    static void testNullInputThrowsIOException() {
        try {
            new TimestampToken(null);
        } catch (IOException e) {
            if (!"No timestamp token info".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("Expected IOException for null input");
    }
}
