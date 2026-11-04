/*
 * @test
 * @summary PrintWriter(String fileName, Charset charset) writes encoded text to the file and rejects null charset or unopenable paths
 * @run main PrintWriterWitupTest
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class PrintWriterWitupTest {

    static void fail(String msg) {
        throw new RuntimeException(msg);
    }

    public static void main(String[] args) throws Exception {
        File tmp = File.createTempFile("pw_witup", ".txt");
        try {
            String text = "h\u00e9llo";

            try (PrintWriter pw = new PrintWriter(tmp.getPath(), StandardCharsets.UTF_8)) {
                pw.print(text);
            }
            byte[] utf8 = Files.readAllBytes(tmp.toPath());
            if (utf8.length != 6) {
                fail("UTF-8 length expected 6 but was " + utf8.length);
            }
            if (!new String(utf8, StandardCharsets.UTF_8).equals(text)) {
                fail("UTF-8 content mismatch");
            }

            try (PrintWriter pw = new PrintWriter(tmp.getPath(), StandardCharsets.ISO_8859_1)) {
                pw.print(text);
            }
            byte[] latin1 = Files.readAllBytes(tmp.toPath());
            if (latin1.length != 5) {
                fail("ISO-8859-1 length expected 5 but was " + latin1.length);
            }
            if (!new String(latin1, StandardCharsets.ISO_8859_1).equals(text)) {
                fail("ISO-8859-1 content mismatch");
            }

            try {
                new PrintWriter(tmp.getPath(), (Charset) null);
                fail("expected NullPointerException for null charset");
            } catch (NullPointerException expected) {
                // expected
            }

            String badPath = new File(tmp, "nope/x.txt").getPath();
            try {
                new PrintWriter(badPath, StandardCharsets.UTF_8);
                fail("expected FileNotFoundException for unopenable path");
            } catch (FileNotFoundException expected) {
                // expected
            }
        } finally {
            tmp.delete();
        }
    }
}
