/*
 * @test
 * @summary Verifies that the BufferedWriter(Writer) constructor wraps the given writer and buffers output until flush
 * @run main BufferedWriterWitupTest
 */
import java.io.BufferedWriter;
import java.io.StringWriter;

public class BufferedWriterWitupTest {
    public static void main(String[] args) throws Exception {
        StringWriter sw = new StringWriter();
        BufferedWriter bw = new BufferedWriter(sw);
        bw.write("abc");
        if (sw.toString().length() != 0) {
            throw new RuntimeException("Output was not buffered before flush: " + sw.toString());
        }
        bw.flush();
        if (!"abc".equals(sw.toString())) {
            throw new RuntimeException("Unexpected output after flush: " + sw.toString());
        }
        bw.close();
    }
}
