/*
 * @test
 * @summary Test for PrintStream constructor with filename and Charset, focusing on null charset.
 * @run main PrintStreamWitupTest
 */

import java.io.PrintStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class PrintStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullCharset();
        testConstructorWithValidCharset();
    }

    private static void testConstructorWithNullCharset() {
        try {
            new PrintStream("dummy.txt", (Charset) null);
            System.err.println("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("NullPointerException correctly thrown for null charset");
        } catch (IOException e) {
            System.err.println("Unexpected IOException: " + e);
        }
    }

    private static void testConstructorWithValidCharset() {
        try {
            Charset utf8 = Charset.forName("UTF-8");
            PrintStream ps = new PrintStream("dummy.txt", utf8);
            ps.close();
            System.out.println("PrintStream created successfully with valid charset");
        } catch (IOException e) {
            System.err.println("IOException during creation with valid charset: " + e);
        }
    }
}
