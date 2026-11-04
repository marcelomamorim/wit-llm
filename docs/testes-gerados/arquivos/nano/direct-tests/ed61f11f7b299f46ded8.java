/*
 * @test
 * @summary Test PrintStream constructor with File and Charset
 * @run main PrintStreamWitupTest
 */

import java.io.File;
import java.io.PrintStream;
import java.nio.charset.Charset;

public class PrintStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithFileAndCharset();
        testConstructorWithNullFile();
        testConstructorWithNullCharset();
    }

    static void testConstructorWithFileAndCharset() {
        try {
            File file = new File("testfile.txt");
            Charset charset = Charset.forName("UTF-8");
            PrintStream ps = new PrintStream(file, charset);
            ps.println("Hello, world!");
            ps.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create PrintStream with File and Charset", e);
        }
    }

    static void testConstructorWithNullFile() {
        try {
            Charset charset = Charset.forName("UTF-8");
            new PrintStream((File) null, charset);
            throw new AssertionError("Expected NullPointerException for null File");
        } catch (NullPointerException expected) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for null File", e);
        }
    }

    static void testConstructorWithNullCharset() {
        try {
            File file = new File("testfile.txt");
            new PrintStream(file, (Charset) null);
            throw new AssertionError("Expected NullPointerException for null Charset");
        } catch (NullPointerException expected) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for null Charset", e);
        }
    }
}
