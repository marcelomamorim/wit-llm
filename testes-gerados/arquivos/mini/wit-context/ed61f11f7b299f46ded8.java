/*
 * @test
 * @summary Test PrintStream(File file, Charset charset) constructor behavior
 * @run main PrintStreamWitupTest
 */

import java.io.File;
import java.io.PrintStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class PrintStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testNullCharsetThrowsNPE();
        testValidCharsetCreatesPrintStream();
    }

    private static void testNullCharsetThrowsNPE() {
        File tempFile = null;
        try {
            tempFile = File.createTempFile("PrintStreamWitupTest", ".tmp");
            tempFile.deleteOnExit();
            try {
                new PrintStream(tempFile, (Charset) null);
                throw new RuntimeException("Expected NullPointerException not thrown");
            } catch (NullPointerException npe) {
                // expected
            }
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException during test setup", ioe);
        } finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    private static void testValidCharsetCreatesPrintStream() throws IOException {
        File tempFile = File.createTempFile("PrintStreamWitupTest", ".tmp");
        tempFile.deleteOnExit();
        Charset charset = Charset.forName("UTF-8");
        PrintStream ps = null;
        try {
            ps = new PrintStream(tempFile, charset);
            if (ps == null) {
                throw new RuntimeException("PrintStream constructor returned null");
            }
            // Write something and flush to verify basic functionality
            ps.println("test");
            ps.flush();
            if (ps.checkError()) {
                throw new RuntimeException("PrintStream reports error after write");
            }
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }
}
