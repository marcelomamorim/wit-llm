/*
 * @test
 * @summary Verifies PrintStream(File, Charset) charset validation, encoded output and failure on a missing directory
 * @run main PrintStreamWitupTest
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Arrays;

public class PrintStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNullCharsetRejected();
        testUtf8Output();
        testUtf16BeOutput();
        testMissingDirectoryFails();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static File tempFile() throws IOException {
        File f = File.createTempFile("PrintStreamWitupTest", ".txt");
        f.deleteOnExit();
        return f;
    }

    static byte[] readAll(File f) throws IOException {
        try (FileInputStream in = new FileInputStream(f)) {
            return in.readAllBytes();
        }
    }

    static void testNullCharsetRejected() throws IOException {
        File f = tempFile();
        try {
            new PrintStream(f, (Charset) null);
            check(false, "expected NullPointerException for null charset");
        } catch (NullPointerException e) {
            check("charset".equals(e.getMessage()), "unexpected NPE message: " + e.getMessage());
        }
    }

    static void testUtf8Output() throws IOException {
        Charset utf8 = Charset.forName("UTF-8");
        File f = tempFile();
        String text = "h" + (char) 0xE9;
        PrintStream ps = new PrintStream(f, utf8);
        ps.print(text);
        ps.close();
        byte[] expected = text.getBytes(utf8);
        check(Arrays.equals(expected, readAll(f)), "UTF-8 output bytes mismatch");
    }

    static void testUtf16BeOutput() throws IOException {
        Charset utf16be = Charset.forName("UTF-16BE");
        File f = tempFile();
        PrintStream ps = new PrintStream(f, utf16be);
        ps.print("A");
        check(!ps.checkError(), "unexpected error flag before close");
        ps.close();
        byte[] expected = new byte[] {0x00, 0x41};
        check(Arrays.equals(expected, readAll(f)), "UTF-16BE output bytes mismatch");
    }

    static void testMissingDirectoryFails() {
        Charset utf8 = Charset.forName("UTF-8");
        File f = new File("no_such_dir_witup_xyz/out.txt");
        try {
            new PrintStream(f, utf8);
            check(false, "expected FileNotFoundException for missing directory");
        } catch (FileNotFoundException e) {
            // expected
        }
    }
}
