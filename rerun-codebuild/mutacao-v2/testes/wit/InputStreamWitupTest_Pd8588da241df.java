/*
 * @test
 * @summary Test InputStream.reset() throws IOException as expected
 * @run main InputStreamWitupTest_Pd8588da241df
 */

import java.io.IOException;
import java.io.InputStream;

public class InputStreamWitupTest_Pd8588da241df {
    public static void main(String[] args) throws Exception {
        InputStream is = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };

        try {
            is.reset();
            throw new AssertionError("Expected IOException not thrown by reset()");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("mark/reset not supported")) {
                throw new AssertionError("IOException message unexpected: " + msg);
            }
        }
    }
}
