/*
 * @test
 * @summary WITUP-derived checks for the PipedWriter constructors and null reader handling
 * @run main PipedWriterWitupTest
 */

public class PipedWriterWitupTest {

    public static void main(String[] args) throws Exception {
        testNullReaderThrowsNullPointerException();
        testNoArgConstructorCreatesUnconnectedWriter();
    }

    static void testNullReaderThrowsNullPointerException() {
        boolean thrown = false;
        try {
            new PipedWriter(null);
        } catch (NullPointerException e) {
            thrown = true;
        } catch (java.io.IOException e) {
            throw new RuntimeException("Unexpected IOException for null reader", e);
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null PipedReader");
        }
    }

    static void testNoArgConstructorCreatesUnconnectedWriter() {
        PipedWriter w = new PipedWriter();
        if (w == null) {
            throw new RuntimeException("PipedWriter() returned null");
        }
    }
}
