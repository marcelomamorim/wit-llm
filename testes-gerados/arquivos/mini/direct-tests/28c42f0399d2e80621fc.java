/*
 * @test
 * @summary Test InputStream.reset() throws IOException as mark/reset not supported
 * @run main InputStreamWitupTest
 */

import java.io.IOException;
import java.io.InputStream;

public class InputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        InputStream is = new InputStream() {
            @Override
            public int read() {
                return -1;
            }
        };

        try {
            is.reset();
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IOException message: " + e.getMessage());
            }
        }
    }
}
