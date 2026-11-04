/*
 * @test
 * @summary Testa o construtor PrintWriter(File, Charset) para comportamento esperado.
 * @run main PrintWriterWitupTest
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

public class PrintWriterWitupTest {

    public static void main(String[] args) {
        testNullCharsetThrows();
        testValidCharsetAndFile();
    }

    private static void testNullCharsetThrows() {
        try {
            new PrintWriter(new File("dummy.txt"), (Charset) null);
            System.err.println("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("NullPointerException correctly thrown for null charset");
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }

    private static void testValidCharsetAndFile() {
        try {
            File tempFile = File.createTempFile("test", ".txt");
            tempFile.deleteOnExit();
            Charset charset = Charset.forName("UTF-8");
            PrintWriter pw = new PrintWriter(tempFile, charset);
            pw.close();
            System.out.println("PrintWriter created successfully with valid charset and file");
        } catch (IOException e) {
            System.err.println("IOException during test: " + e);
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }
}
