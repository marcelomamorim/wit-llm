/* @test
   @summary Tests the reset method of InflaterInputStream.
   @run main InflaterInputStreamWitupTest_P40ea45389f67
 */
import java.io.InputStream;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest_P40ea45389f67 {
    public static void main(String[] args) {
        testResetThrowsIOException();
    }

    public static void testResetThrowsIOException() {
        InputStream dummyInput = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };
        Inflater inflater = new Inflater();
        InflaterInputStream iis = new InflaterInputStream(dummyInput, inflater);
        try {
            iis.reset();
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
            // expected exception
        }
    }
}
