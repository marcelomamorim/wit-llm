/* @test    @summary Test constructor PrintWriter(String, Charset) with valid inputs    @run main PrintWriterWitupTest */

import java.io.PrintWriter;
import java.io.File;
import java.nio.charset.Charset;
import java.io.IOException;

public class PrintWriterWitupTest {

    public static void main(String[] args) throws IOException {
        testValidConstructor();
        testNullCharset();
        testInvalidCharsetName();
    }

    static void testValidConstructor() throws IOException {
        String filename = "testfile.txt";
        Charset charset = Charset.forName("UTF-8");
        PrintWriter pw = new PrintWriter(filename, charset);
        pw.println("Test line");
        pw.close();
        File file = new File(filename);
        assert file.exists() : "File should exist after constructor call";
        assert file.length() > 0 : "File should contain data";
        file.delete();
    }

    static void testNullCharset() {
        String filename = "testfile.txt";
        try {
            new PrintWriter(filename, (Charset) null);
            assert false : "Expected NullPointerException for null charset";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testInvalidCharsetName() {
        String filename = "testfile.txt";
        try {
            Charset.forName("InvalidCharsetName");
            assert false : "Expected UnsupportedCharsetException for invalid charset name";
        } catch (IllegalArgumentException | UnsupportedCharsetException e) {
            // expected
        }
    }
}
