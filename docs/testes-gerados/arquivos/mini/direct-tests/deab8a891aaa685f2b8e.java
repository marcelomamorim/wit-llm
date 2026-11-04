/*
 * @test
 * @summary Test PrintWriter(String fileName, Charset charset) constructor
 * @run main PrintWriterWitupTest
 */

import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class PrintWriterWitupTest {
    public static void main(String[] args) throws IOException {
        // Test with valid file name and charset
        String fileName = "PrintWriterWitupTest.tmp";
        Charset charset = StandardCharsets.UTF_8;

        // Create PrintWriter with valid parameters
        PrintWriter pw = new PrintWriter(fileName, charset);
        pw.print("test");
        pw.close();

        // Verify file was created and is writable by reopening
        PrintWriter pw2 = new PrintWriter(fileName, charset);
        pw2.println("line");
        pw2.close();

        // Test that passing null charset throws NullPointerException
        boolean npeThrown = false;
        try {
            new PrintWriter(fileName, null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException for null charset");
        }

        // Clean up test file
        File f = new File(fileName);
        if (f.exists() && !f.delete()) {
            System.err.println("Warning: could not delete test file " + fileName);
        }
    }
}
