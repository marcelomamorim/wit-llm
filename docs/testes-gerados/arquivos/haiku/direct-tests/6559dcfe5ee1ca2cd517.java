/*
 * @test
 * @summary Tests PrintStream(String fileName, Charset charset): null charset rejection, UTF-8 file output and missing directory failure
 * @run main PrintStreamWitupTest
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class PrintStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNullCharsetRejected();
        testWritesUtf8File();
        testMissingDirectoryFails();
    }

    static void testNullCharsetRejected() {
        boolean thrown = false;
        try {
            new PrintStream("witup_null_charset_unused.txt", (Charset) null);
        } catch (NullPointerException e) {
            thrown = true;
        } catch (IOException e) {
            throw new RuntimeException("unexpected IOException", e);
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null charset");
        }
        File f = new File("witup_null_charset_unused.txt");
        if (f.exists()) {
            f.delete();
            throw new RuntimeException("file must not be created when charset is null");
        }
    }

    static void testWritesUtf8File() throws IOException {
        File f = File.createTempFile("witup", ".txt");
        try {
            PrintStream ps = new PrintStream(f.getPath(), StandardCharsets.UTF_8);
            String text = "h\u00e9llo";
            ps.print(text);
            if (ps.checkError()) {
                throw new RuntimeException("unexpected error flag after print");
            }
            ps.close();

            byte[] expected = text.getBytes(StandardCharsets.UTF_8);
            byte[] actual = readAll(f);
            if (actual.length != expected.length) {
                throw new RuntimeException("length mismatch: " + actual.length + " vs " + expected.length);
            }
            for (int i = 0; i < expected.length; i++) {
                if (expected[i] != actual[i]) {
                    throw new RuntimeException("byte mismatch at index " + i);
                }
            }
        } finally {
            f.delete();
        }
    }

    static void testMissingDirectoryFails() {
        boolean thrown = false;
        try {
            PrintStream ps = new PrintStream("witup_missing_dir_xyz/out.txt", StandardCharsets.UTF_8);
            ps.close();
        } catch (FileNotFoundException e) {
            thrown = true;
        } catch (IOException e) {
            throw new RuntimeException("unexpected IOException type", e);
        }
        if (!thrown) {
            throw new RuntimeException("expected FileNotFoundException for missing directory");
        }
    }

    static byte[] readAll(File f) throws IOException {
        FileInputStream in = new FileInputStream(f);
        try {
            byte[] buf = new byte[1024];
            java.io.ByteArrayOutputStreamHolder holder = null;
            int total = 0;
            byte[] result = new byte[0];
            int n;
            while ((n = in.read(buf)) != -1) {
                byte[] grown = new byte[total + n];
                System.arraycopy(result, 0, grown, 0, total);
                System.arraycopy(buf, 0, grown, total, n);
                result = grown;
                total += n;
            }
            return result;
        } finally {
            in.close();
        }
    }
}
