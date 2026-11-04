/*
 * @test
 * @summary Behavioral checks for OutputStreamWriter(OutputStream) constructor: null stream rejection and default-charset encoding of written output
 * @run main OutputStreamWriterWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Arrays;

public class OutputStreamWriterWitupTest {

    public static void main(String[] args) throws Exception {
        testNullOutputStreamThrowsNPE();
        testAsciiWriteUsesDefaultCharset();
        testEmptyWriteProducesNoBytes();
        testNonAsciiWriteUsesDefaultCharset();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullOutputStreamThrowsNPE() {
        boolean thrown = false;
        try {
            new OutputStreamWriter((ByteArrayOutputStream) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null OutputStream must raise NullPointerException");
    }

    static void testAsciiWriteUsesDefaultCharset() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(bos);
        osw.write("abc");
        osw.flush();
        byte[] expected = "abc".getBytes(Charset.defaultCharset());
        check(Arrays.equals(expected, bos.toByteArray()),
              "ASCII output must match default charset encoding");
        osw.close();
    }

    static void testEmptyWriteProducesNoBytes() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(bos);
        osw.write("");
        osw.flush();
        check(bos.size() == 0, "empty write must produce no bytes");
        osw.close();
    }

    static void testNonAsciiWriteUsesDefaultCharset() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(bos);
        String s = "\u00e9\u00e0";
        osw.write(s);
        osw.flush();
        byte[] expected = s.getBytes(Charset.defaultCharset());
        check(Arrays.equals(expected, bos.toByteArray()),
              "non-ASCII output must match default charset encoding");
        osw.close();
    }
}
