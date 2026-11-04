/*
 * @test
 * @summary Test for OutputStreamWriter constructor with OutputStream only
 * @run main OutputStreamWriterWitupTest
 */

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.ByteArrayOutputStream;

public class OutputStreamWriterWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithNullOutputStream();
        testConstructorWithValidOutputStream();
    }

    private static void testConstructorWithNullOutputStream() {
        try {
            new OutputStreamWriter((OutputStream) null);
            System.err.println("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("NullPointerException correctly thrown for null OutputStream");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
        }
    }

    private static void testConstructorWithValidOutputStream() {
        OutputStream out = new ByteArrayOutputStream();
        try {
            OutputStreamWriter osw = new OutputStreamWriter(out);
            if (osw != null) {
                System.out.println("OutputStreamWriter successfully created with valid OutputStream");
            } else {
                System.err.println("Failed to create OutputStreamWriter with valid OutputStream");
            }
        } catch (Throwable t) {
            System.err.println("Unexpected exception during valid construction: " + t);
        }
    }
}
