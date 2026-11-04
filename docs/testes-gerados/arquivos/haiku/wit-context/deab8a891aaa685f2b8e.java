/*
 * @test
 * @summary Checks PrintWriter(String fileName, Charset charset) null handling and charset-encoded file output
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

    public static void main(String[] args) throws IOException {
        testNullCharsetThrowsNpe();
        testNullFileNameThrowsNpe();
        testWritesEncodedContent();
    }

    static void testNullCharsetThrowsNpe() throws IOException {
        File f = File.createTempFile("pwwitup", ".txt");
        try {
            Charset cs = null;
            try {
                new PrintWriter(f.getPath(), cs);
                throw new RuntimeException("expected NullPointerException for null charset");
            } catch (NullPointerException expected) {
            }
        } finally {
            f.delete();
        }
    }

    static void testNullFileNameThrowsNpe() {
        try {
            String name = null;
            new PrintWriter(name, StandardCharsets.UTF_8);
            throw new RuntimeException("expected NullPointerException for null file name");
        } catch (NullPointerException expected) {
        } catch (IOException e) {
            throw new RuntimeException("unexpected IOException", e);
        }
    }

    static void testWritesEncodedContent() throws IOException {
        File f = File.createTempFile("pwwitup", ".txt");
        try {
            PrintWriter pw = new PrintWriter(f.getPath(), StandardCharsets.UTF_8);
            pw.print("a\u00e9");
            pw.flush();
            if (pw.checkError()) {
                throw new RuntimeException("unexpected checkError() == true");
            }
            pw.close();
            byte[] actual = Files.readAllBytes(f.toPath());
            byte[] expected = "a\u00e9".getBytes(StandardCharsets.UTF_8);
            if (!Arrays.equals(expected, actual)) {
                throw new RuntimeException("encoded content mismatch");
            }
        } finally {
            f.delete();
        }
    }
}
