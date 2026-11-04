/*
 * @test
 * @summary Verifies OutputStreamWriter(OutputStream) encodes with the default charset and writes observable bytes
 * @run main OutputStreamWriterWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Arrays;

public class OutputStreamWriterWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws IOException {
        Charset cs = Charset.defaultCharset();

        // Basic write and flush: bytes must match default-charset encoding
        String text = "abc\u00e9";
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(bos);
        osw.write(text);
        osw.flush();
        check(Arrays.equals(text.getBytes(cs), bos.toByteArray()),
              "basic write mismatch");
        osw.close();

        // Nothing written: no bytes produced after flush
        ByteArrayOutputStream empty = new ByteArrayOutputStream();
        OutputStreamWriter emptyWriter = new OutputStreamWriter(empty);
        emptyWriter.flush();
        check(empty.size() == 0, "empty flush must produce no bytes");
        emptyWriter.close();

        // Chunked writes via char array produce same bytes as a single String
        ByteArrayOutputStream chunked = new ByteArrayOutputStream();
        OutputStreamWriter chunkWriter = new OutputStreamWriter(chunked);
        char[] chars = "hello world".toCharArray();
        chunkWriter.write(chars, 0, 5);
        chunkWriter.write(chars, 5, chars.length - 5);
        chunkWriter.flush();
        check(Arrays.equals("hello world".getBytes(cs), chunked.toByteArray()),
              "chunked char[] write mismatch");
        chunkWriter.close();

        // Single ASCII character write
        ByteArrayOutputStream single = new ByteArrayOutputStream();
        OutputStreamWriter singleWriter = new OutputStreamWriter(single);
        singleWriter.write('Z');
        singleWriter.flush();
        check(Arrays.equals("Z".getBytes(cs), single.toByteArray()),
              "single char write mismatch");
        singleWriter.close();

        // Surrogate pair must be encoded consistently with String.getBytes
        String emoji = "x\uD83D\uDE00y";
        ByteArrayOutputStream surrogate = new ByteArrayOutputStream();
        OutputStreamWriter surWriter = new OutputStreamWriter(surrogate);
        surWriter.write(emoji);
        surWriter.flush();
        check(Arrays.equals(emoji.getBytes(cs), surrogate.toByteArray()),
              "surrogate pair encoding mismatch");
        surWriter.close();
    }
}
