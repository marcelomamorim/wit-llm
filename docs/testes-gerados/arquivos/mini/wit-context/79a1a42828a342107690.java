/*
 * @test
 * @summary Test PrintWriter(File file, Charset charset) constructor for null charset and normal usage
 * @run main PrintWriterWitupTest
 */

import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class PrintWriterWitupTest {
    public static void main(String[] args) throws Exception {
        testNullCharsetThrowsNPE();
        testValidCharsetCreatesPrintWriter();
    }

    private static void testNullCharsetThrowsNPE() {
        File tmpFile = null;
        try {
            tmpFile = File.createTempFile("PrintWriterWitupTest", ".txt");
            tmpFile.deleteOnExit();
            try {
                new PrintWriter(tmpFile, (Charset) null);
                throw new RuntimeException("Expected NullPointerException not thrown");
            } catch (NullPointerException npe) {
                // Expected exception
            }
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException during test setup", ioe);
        } finally {
            if (tmpFile != null && tmpFile.exists()) {
                tmpFile.delete();
            }
        }
    }

    private static void testValidCharsetCreatesPrintWriter() throws IOException {
        File tmpFile = File.createTempFile("PrintWriterWitupTest", ".txt");
        tmpFile.deleteOnExit();
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(tmpFile, StandardCharsets.UTF_8);
            if (pw == null) {
                throw new RuntimeException("PrintWriter constructor returned null");
            }
            pw.print("test");
            pw.flush();
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (tmpFile.exists()) {
                tmpFile.delete();
            }
        }
    }
}
