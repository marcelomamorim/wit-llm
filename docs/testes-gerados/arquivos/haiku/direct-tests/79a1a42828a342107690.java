/*
 * @test
 * @summary Verify PrintWriter(File, Charset) rejects a null charset and encodes output using the given charset
 * @run main PrintWriterWitupTest
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

public class PrintWriterWitupTest {

    public static void main(String[] args) throws Exception {
        testNullCharsetRejected();
        writeAndCheck(StandardCharsets.UTF_8, "A" + (char) 0xE9 + (char) 0x20AC + "B");
        writeAndCheck(StandardCharsets.ISO_8859_1, "A" + (char) 0xE9 + (char) 0x20AC + "B");
    }

    static void testNullCharsetRejected() throws IOException {
        File f = File.createTempFile("pwwitup", ".txt");
        try {
            new PrintWriter(f, (Charset) null);
            throw new RuntimeException("NullPointerException expected for null charset");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            f.delete();
        }
    }

    static void writeAndCheck(Charset cs, String text) throws IOException {
        File f = File.createTempFile("pwwitup", ".txt");
        try {
            PrintWriter pw = new PrintWriter(f, cs);
            pw.print(text);
            pw.close();
            byte[] actual = Files.readAllBytes(f.toPath());
            byte[] expected = text.getBytes(cs);
            if (!Arrays.equals(actual, expected)) {
                throw new RuntimeException("Unexpected bytes for charset " + cs.name());
            }
        } finally {
            f.delete();
        }
    }
}
