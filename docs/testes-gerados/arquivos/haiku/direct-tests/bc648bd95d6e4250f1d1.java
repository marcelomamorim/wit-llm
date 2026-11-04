/*
 * @test
 * @summary Verifies skip(long) of InputStream.nullInputStream() returns 0 while open and throws IOException after close
 * @run main InputStreamWitupTest
 */

import java.io.IOException;
import java.io.InputStream;

public class InputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        InputStream in = InputStream.nullInputStream();
        if (in.skip(10L) != 0L) {
            throw new AssertionError("skip(10) expected 0");
        }
        if (in.skip(0L) != 0L) {
            throw new AssertionError("skip(0) expected 0");
        }
        if (in.skip(Long.MAX_VALUE) != 0L) {
            throw new AssertionError("skip(Long.MAX_VALUE) expected 0");
        }
        in.close();
        in.close();
        try {
            in.skip(5L);
            throw new AssertionError("skip after close expected IOException");
        } catch (IOException expected) {
            // expected
        }
    }
}
