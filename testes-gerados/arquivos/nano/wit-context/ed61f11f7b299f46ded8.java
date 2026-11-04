/* @test    @summary Test for PrintStream constructor with File and Charset parameters.
 * @run main PrintStreamWitupTest
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;

public class PrintStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullCharset();
        testConstructorWithValidFileAndCharset();
    }

    private static void testConstructorWithNullCharset() {
        try {
            File file = new File("dummy.txt");
            new PrintStream(file, (Charset) null);
            System.err.println("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("NullPointerException correctly thrown for null charset");
        } catch (IOException e) {
            System.err.println("Unexpected IOException: " + e);
        }
    }

    private static void testConstructorWithValidFileAndCharset() {
        try {
            File file = new File("dummy.txt");
            Charset charset = Charset.forName("UTF-8");
            PrintStream ps = new PrintStream(file, charset);
            if (ps != null) {
                System.out.println("PrintStream successfully created with valid file and charset");
                ps.close();
            } else {
                System.err.println("PrintStream is null");
            }
        } catch (IOException e) {
            System.err.println("IOException during test: " + e);
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e);
        }
    }
}
