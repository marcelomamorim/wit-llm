/* @test
   @summary Verifies that calling reset() on InputStream throws IOException with the correct message.
   @run main InputStreamWitupTest_P10d7a18c868f
 */
import java.io.InputStream;
import java.io.IOException;
public class InputStreamWitupTest_P10d7a18c868f {
    public static void main(String[] args) {
        InputStream is = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };
        try {
            is.reset();
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
