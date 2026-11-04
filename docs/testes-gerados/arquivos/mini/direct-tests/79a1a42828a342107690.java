/*
 * @test
 * @summary Test PrintWriter(File file, Charset charset) constructor
 * @run main PrintWriterWitupTest
 */

import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.nio.charset.Charset;

public class PrintWriterWitupTest {
    public static void main(String[] args) throws Exception {
        testValidCharset();
        testNullCharset();
    }

    private static void testValidCharset() throws IOException {
        File tempFile = File.createTempFile("PrintWriterTest", ".txt");
        tempFile.deleteOnExit();
        Charset charset = Charset.forName("UTF-8");
        PrintWriter pw = new PrintWriter(tempFile, charset);
        pw.print("test");
        pw.close();
        if (pw.checkError()) {
            throw new RuntimeException("PrintWriter reported error after writing");
        }
    }

    private static void testNullCharset() {
        File tempFile = new File("dummy");
        try {
            new PrintWriter(tempFile, null);
            throw new RuntimeException("Expected NullPointerException for null charset");
        } catch (NullPointerException npe) {
            // expected
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException instead of NullPointerException", ioe);
        }
    }
}
