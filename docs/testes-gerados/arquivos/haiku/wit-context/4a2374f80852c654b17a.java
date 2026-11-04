/*
 * @test
 * @summary Verifies that PipedOutputStream.connect(null) and the constructor with a null sink throw NullPointerException
 * @run main PipedOutputStreamWitupTest
 */

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipedOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testConnectNullThrowsNPE();
        testConstructorNullThrowsNPE();
        System.out.println("PASS");
    }

    static void testConnectNullThrowsNPE() {
        PipedOutputStream pos = new PipedOutputStream();
        try {
            pos.connect(null);
            throw new RuntimeException("Expected NullPointerException");
        } catch (NullPointerException expected) {
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    static void testConstructorNullThrowsNPE() {
        try {
            new PipedOutputStream((PipedInputStream) null);
            throw new RuntimeException("Expected NullPointerException");
        } catch (NullPointerException expected) {
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }
}
