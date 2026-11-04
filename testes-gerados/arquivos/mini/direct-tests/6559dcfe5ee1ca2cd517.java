/*
 * @test
 * @summary Test PrintStream(String fileName, Charset charset) constructor
 * @run main PrintStreamWitupTest
 */

import java.io.PrintStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class PrintStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testValidCharset();
        testNullCharset();
        testInvalidFile();
    }

    private static void testValidCharset() throws IOException {
        File tempFile = File.createTempFile("PrintStreamWitupTest", ".txt");
        tempFile.deleteOnExit();
        Charset charset = Charset.forName("UTF-8");
        PrintStream ps = new PrintStream(tempFile.getPath(), charset);
        ps.print("test");
        ps.close();
    }

    private static void testNullCharset() {
        try {
            new PrintStream("dummy.txt", null);
            throw new AssertionError("Expected NullPointerException for null charset");
        } catch (NullPointerException e) {
            if (!"charset".equals(e.getMessage())) {
                throw new AssertionError("NullPointerException message mismatch", e);
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }

    private static void testInvalidFile() {
        try {
            // Using an invalid file path to provoke IOException
            new PrintStream("/invalid_path/invalid_file.txt", Charset.forName("UTF-8"));
            throw new AssertionError("Expected IOException for invalid file path");
        } catch (IOException e) {
            // Expected IOException
        }
    }
}
