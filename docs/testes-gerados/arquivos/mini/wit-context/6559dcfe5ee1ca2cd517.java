/*
 * @test
 * @summary Test PrintStream(String fileName, Charset charset) constructor for null charset and normal usage
 * @run main PrintStreamWitupTest
 */

import java.io.PrintStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class PrintStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testNullCharsetThrowsNPE();
        testValidCharsetCreatesPrintStream();
    }

    private static void testNullCharsetThrowsNPE() {
        String fileName = "testfile.txt";
        try {
            new PrintStream(fileName, (Charset) null);
            throw new RuntimeException("Expected NullPointerException for null charset");
        } catch (NullPointerException npe) {
            // expected
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException", ioe);
        }
    }

    private static void testValidCharsetCreatesPrintStream() throws IOException {
        String fileName = "testfile_valid.txt";
        Charset charset = StandardCharsets.UTF_8;
        PrintStream ps = null;
        try {
            ps = new PrintStream(fileName, charset);
            if (ps.checkError()) {
                throw new RuntimeException("PrintStream has error after creation");
            }
            ps.println("Hello, world!");
            ps.flush();
        } finally {
            if (ps != null) {
                ps.close();
            }
            // Clean up file
            File f = new File(fileName);
            if (f.exists()) {
                f.delete();
            }
        }
    }
}
