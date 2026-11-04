/*
 * @test
 * @summary Test PrintWriter(String fileName, Charset charset) constructor for null charset and normal usage
 * @run main PrintWriterWitupTest
 */

import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class PrintWriterWitupTest {
    public static void main(String[] args) throws Exception {
        testNullCharsetThrowsNPE();
        testValidCharsetCreatesPrintWriter();
    }

    private static void testNullCharsetThrowsNPE() {
        try {
            new PrintWriter("testfile.txt", (Charset) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected exception
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException", ioe);
        }
    }

    private static void testValidCharsetCreatesPrintWriter() throws IOException {
        Charset charset = StandardCharsets.UTF_8;
        String fileName = "testfile_utf8.txt";
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(fileName, charset);
            if (pw == null) {
                throw new RuntimeException("PrintWriter constructor returned null");
            }
            pw.print("test");
            pw.flush();
        } finally {
            if (pw != null) {
                pw.close();
            }
            // Delete the file after test to avoid side effects
            File f = new File(fileName);
            if (f.exists() && !f.delete()) {
                // best effort cleanup
            }
        }
    }
}
