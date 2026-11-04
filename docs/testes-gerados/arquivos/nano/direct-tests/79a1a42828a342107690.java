/* @test    @summary Test PrintWriter constructor with File and Charset parameters    @run main PrintWriterWitupTest */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

public class PrintWriterWitupTest {

    public static void main(String[] args) throws IOException {
        testConstructorWithFileAndCharset();
    }

    public static void testConstructorWithFileAndCharset() throws IOException {
        File tempFile = File.createTempFile("test", ".txt");
        tempFile.deleteOnExit();
        Charset charset = Charset.forName("UTF-8");
        try (PrintWriter pw = new PrintWriter(tempFile, charset)) {
            pw.println("Hello, world!");
        }
        // Read back the file content to verify
        String content = new String(java.nio.file.Files.readAllBytes(tempFile.toPath()), charset);
        if (!content.contains("Hello, world!")) {
            throw new AssertionError("Content does not contain expected text.");
        }
    }
}