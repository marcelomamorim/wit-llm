/* @test    @summary Test OutputStreamWriter constructor with OutputStream only    @run main OutputStreamWriterWitupTest */

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.ByteArrayOutputStream;

public class OutputStreamWriterWitupTest {

    public static void main(String[] args) {
        testConstructorWithOutputStream();
    }

    static void testConstructorWithOutputStream() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStream out = baos;
        OutputStreamWriter writer = new OutputStreamWriter(out);
        if (writer == null) {
            throw new AssertionError("OutputStreamWriter instance is null");
        }
        // Write some characters and verify bytes are written
        try {
            writer.write('A');
            writer.flush();
            byte[] resultBytes = baos.toByteArray();
            if (resultBytes.length == 0) {
                throw new AssertionError("No bytes written to OutputStream");
            }
            // For JDK 11, default charset is platform dependent; check that bytes are present
            if (resultBytes[0] == 0) {
                throw new AssertionError("First byte is zero, unexpected encoding");
            }
        } catch (Exception e) {
            throw new AssertionError("Exception during write: " + e);
        }
    }
}
