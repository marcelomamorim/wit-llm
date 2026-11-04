/*
 * @test
 * @summary Verifies that available() on a Base64 decoding InputStream delegates to the wrapped stream
 * @run main Base64WitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64WitupTest {

    public static void main(String[] args) throws IOException {
        byte[] encoded = "SGVsbG8gV29ybGQ=".getBytes(StandardCharsets.US_ASCII);
        ByteArrayInputStream raw = new ByteArrayInputStream(encoded);
        InputStream in = Base64.getDecoder().wrap(raw);
        int expected = raw.available();
        int actual = in.available();
        if (actual != expected) {
            throw new AssertionError("available() expected " + expected + " but was " + actual);
        }
        if (actual < 0) {
            throw new AssertionError("available() must not be negative: " + actual);
        }

        byte[] emptyEncoded = new byte[0];
        InputStream emptyIn = Base64.getUrlDecoder().wrap(new ByteArrayInputStream(emptyEncoded));
        if (emptyIn.available() != 0) {
            throw new AssertionError("available() on empty input expected 0 but was "
                    + emptyIn.available());
        }

        byte[] mimeEncoded = "AQID\r\nBA==".getBytes(StandardCharsets.US_ASCII);
        ByteArrayInputStream mimeRaw = new ByteArrayInputStream(mimeEncoded);
        InputStream mimeIn = Base64.getMimeDecoder().wrap(mimeRaw);
        if (mimeIn.available() != mimeRaw.available()) {
            throw new AssertionError("MIME available() expected " + mimeRaw.available()
                    + " but was " + mimeIn.available());
        }
    }
}
