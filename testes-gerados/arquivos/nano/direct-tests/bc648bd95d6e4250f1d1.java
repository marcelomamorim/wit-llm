/* @test    @summary Test skip() method of InputStream for JDK 11+28    @run main InputStreamWitupTest */

import java.io.InputStream;
import java.io.IOException;

public class InputStreamWitupTest {

    public static void main(String[] args) {
        testSkipReturnsZero();
    }

    public static void testSkipReturnsZero() {
        InputStream stream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };
        try {
            long result = stream.skip(100L);
            if (result != 0L) {
                throw new AssertionError("Expected skip to return 0, but got " + result);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }
}
