/*
 * @test
 * @summary Test PrintStream(File, Charset) constructor behavior
 * @run main java.io.PrintStreamWitupTest
 */

import java.io.File;
import java.io.PrintStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class PrintStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testValidFileAndCharset();
        testNullCharsetThrowsNPE();
    }

    private static void testValidFileAndCharset() throws IOException {
        File tempFile = File.createTempFile("PrintStreamTest", ".txt");
        tempFile.deleteOnExit();
        Charset charset = StandardCharsets.UTF_8;
        PrintStream ps = new PrintStream(tempFile, charset);
        try {
            ps.println("test");
            ps.flush();
            if (ps.checkError()) {
                throw new RuntimeException("PrintStream reports error after write");
            }
        } finally {
            ps.close();
        }
    }

    private static void testNullCharsetThrowsNPE() {
        File tempFile = new File("dummy");
        try {
            new PrintStream(tempFile, null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
            if (!"charset".equals(npe.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch", npe);
            }
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException", ioe);
        }
    }
}
