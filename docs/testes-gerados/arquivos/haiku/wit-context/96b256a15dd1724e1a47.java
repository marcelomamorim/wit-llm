/*
 * @test
 * @summary WITUP-derived test for OutputStreamWriter(OutputStream, CharsetEncoder): null encoder must throw NPE, valid encoder must produce encoded output
 * @run main OutputStreamWriterWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

public class OutputStreamWriterWitupTest {

    public static void main(String[] args) throws Exception {
        testNullEncoderThrowsNpe();
        testValidEncoderWritesBytes();
    }

    static void testNullEncoderThrowsNpe() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            new OutputStreamWriter(out, (CharsetEncoder) null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException e) {
            if (!"charset encoder".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
    }

    static void testValidEncoderWritesBytes() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        CharsetEncoder enc = StandardCharsets.UTF_8.newEncoder();
        OutputStreamWriter w = new OutputStreamWriter(out, enc);
        w.write("abc");
        w.flush();
        String result = new String(out.toByteArray(), StandardCharsets.US_ASCII);
        if (!"abc".equals(result)) {
            throw new RuntimeException("unexpected output: " + result);
        }
        w.close();
    }
}
