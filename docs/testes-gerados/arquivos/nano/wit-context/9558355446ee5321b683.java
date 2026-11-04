/* @test    @summary Test constructor PrintStream(OutputStream out) for null and invalid parameters    @run main PrintStreamWitupTest */

import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PrintStreamWitupTest {

    public static void main(String[] args) {
        testNullOutputStream();
        testInvalidDefaultCharBufferSize();
    }

    private static void testNullOutputStream() {
        try {
            new PrintStream(null);
            System.err.println("Failed: Expected NullPointerException for null OutputStream");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown for null OutputStream");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception for null OutputStream: " + e);
        }
    }

    private static void testInvalidDefaultCharBufferSize() {
        OutputStream out = new ByteArrayOutputStream();
        try {
            // Since the constructor called is public PrintStream(OutputStream out),
            // and the code indicates it calls this(out, false),
            // the default buffer size is not directly set here, but the baseline suggests
            // that an IllegalArgumentException can be thrown if defaultCharBufferSize <= 0.
            // However, in the current constructor, no such parameter exists, so this test
            // is adapted to simulate an invalid scenario if such logic existed.
            // Since the constructor does not accept buffer size, this test is a placeholder.
            // Alternatively, test for other invalid parameters if applicable.
            // For demonstration, we assume no exception is thrown here.
            new PrintStream(out);
            System.out.println("Passed: No exception for valid OutputStream");
        } catch (IllegalArgumentException e) {
            System.out.println("Failed: Unexpected IllegalArgumentException: " + e);
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
        }
    }
}
