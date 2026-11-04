/*
 * @test
 * @summary Checks that PipedWriter.connect and the PipedWriter(PipedReader) constructor reject a null reader with NullPointerException
 * @run main PipedWriterWitupTest
 */
import java.io.IOException;
import java.io.PipedWriter;

public class PipedWriterWitupTest {

    public static void main(String[] args) throws Exception {
        testConnectNullThrowsNPE();
        testConstructorNullThrowsNPE();
    }

    static void testConnectNullThrowsNPE() {
        PipedWriter w = new PipedWriter();
        try {
            w.connect(null);
        } catch (NullPointerException e) {
            return;
        } catch (IOException e) {
            throw new RuntimeException("expected NullPointerException but got IOException", e);
        }
        throw new RuntimeException("expected NullPointerException was not thrown");
    }

    static void testConstructorNullThrowsNPE() {
        try {
            new PipedWriter(null);
        } catch (NullPointerException e) {
            return;
        } catch (IOException e) {
            throw new RuntimeException("expected NullPointerException but got IOException", e);
        }
        throw new RuntimeException("expected NullPointerException was not thrown");
    }
}
