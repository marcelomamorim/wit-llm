/* @test
   @summary Testa o método reset() de InputStream que lança IOException.
   @run main InputStreamWitupTest_P10d7a18c868f
 */
import java.io.IOException;
import java.io.InputStream;
public class InputStreamWitupTest_P10d7a18c868f {
    public static void main(String[] args) {
        InputStream stream = new InputStream() {
            @Override
            public int read() throws IOException {
                return -1;
            }
        };
        try {
            stream.reset();
            throw new AssertionError("Expected IOException not thrown");
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage(), e);
            }
        }
    }
}
