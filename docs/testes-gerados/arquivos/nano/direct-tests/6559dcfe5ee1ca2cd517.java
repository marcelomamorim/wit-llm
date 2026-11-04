/* @test    @summary Test constructor PrintStream(String, Charset) with valid filename and charset    @run main PrintStreamWitupTest */

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.io.PrintStream;

public class PrintStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testValidConstructor();
        testNullCharset();
        testInvalidCharset();
    }

    static void testValidConstructor() throws IOException {
        String filename = "test_output.txt";
        Charset charset = Charset.forName("UTF-8");
        PrintStream ps = null;
        try {
            ps = new PrintStream(filename, charset);
            ps.println("Test message");
            ps.flush();
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
        File file = new File(filename);
        assert file.exists() : "File should exist after constructor call";
        assert file.length() > 0 : "File should contain data";
        file.delete();
    }

    static void testNullCharset() {
        String filename = "test_null_charset.txt";
        try {
            new PrintStream(filename, (Charset) null);
            assert false : "Expected NullPointerException for null charset";
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            assert false : "Unexpected IOException";
        }
        File file = new File(filename);
        assert !file.exists() : "File should not be created on exception";
    }

    static void testInvalidCharset() {
        String filename = "test_invalid_charset.txt";
        try {
            Charset invalidCharset = Charset.forName("Invalid-Charset-Name");
            new PrintStream(filename, invalidCharset);
            assert false : "Expected UnsupportedCharsetException for invalid charset";
        } catch (UnsupportedCharsetException e) {
            // expected
        } catch (IOException e) {
            assert false : "Unexpected IOException";
        }
        File file = new File(filename);
        assert !file.exists() : "File should not be created on exception";
    }
}
