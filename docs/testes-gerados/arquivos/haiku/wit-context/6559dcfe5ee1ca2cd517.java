/*
 * @test
 * @summary Witup-generated checks for PrintStream(String, Charset)
 * @run main PrintStreamWitupTest
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

public class PrintStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testWritesEncodedOutput();
        testNullCharsetThrowsNPE();
        testNullFileNameThrowsNPE();
    }

    static void testWritesEncodedOutput() throws IOException {
        File f = File.createTempFile("PrintStreamWitupTest", ".txt");
        try {
            String text = "h\u00e9llo";
            PrintStream ps = new PrintStream(f.getPath(), StandardCharsets.UTF_8);
            ps.print(text);
            ps.close();
            byte[] actual = Files.readAllBytes(f.toPath());
            byte[] expected = text.getBytes(StandardCharsets.UTF_8);
            if (!Arrays.equals(expected, actual)) {
                throw new RuntimeException("encoded bytes differ");
            }
        } finally {
            f.delete();
        }
    }

    static void testNullCharsetThrowsNPE() throws IOException {
        File f = File.createTempFile("PrintStreamWitupTest", ".txt");
        try {
            try {
                new PrintStream(f.getPath(), (Charset) null);
                throw new RuntimeException("expected NullPointerException");
            } catch (NullPointerException e) {
                // expected
            }
        } finally {
            f.delete();
        }
    }

    static void testNullFileNameThrowsNPE() throws IOException {
        try {
            new PrintStream((String) null, StandardCharsets.UTF_8);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
