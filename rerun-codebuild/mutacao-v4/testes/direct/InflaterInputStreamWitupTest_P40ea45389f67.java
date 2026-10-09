/* @test
   @summary Verifies that calling reset() on InflaterInputStream throws IOException as specified.
   @run main InflaterInputStreamWitupTest_P40ea45389f67
 */
import java.util.zip.InflaterInputStream;
import java.io.IOException;

public class InflaterInputStreamWitupTest_P40ea45389f67 {
    public static void main(String[] args) {
        InflaterInputStream stream = null;
        try {
            stream = new InflaterInputStream(System.in);
            try {
                stream.reset();
                throw new AssertionError("Expected IOException not thrown");
            } catch (IOException e) {
                if (!"mark/reset not supported".equals(e.getMessage())) {
                    throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
                }
                // Expected exception, test passes
            }
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }
}
