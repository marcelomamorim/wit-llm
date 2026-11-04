/*
 * @test
 * @summary Tests PipedOutputStream.connect(PipedInputStream) observable behavior
 * @run main PipedOutputStreamWitupTest
 */

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipedOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testConnectNullThrowsNullPointerException();
        testConnectWhenAlreadyConnectedByConstructorThrows();
        testConnectSecondTimeOnSameOutputThrows();
        testConnectToAlreadyConnectedInputThrows();
        testFailedConnectKeepsOriginalConnection();
        testConnectedPipeTransfersData();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testConnectNullThrowsNullPointerException() {
        PipedOutputStream os = new PipedOutputStream();
        boolean thrown = false;
        try {
            os.connect(null);
        } catch (NullPointerException e) {
            thrown = true;
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
        check(thrown, "connect(null) must throw NullPointerException");
    }

    static void testConnectWhenAlreadyConnectedByConstructorThrows() throws IOException {
        PipedInputStream in1 = new PipedInputStream();
        PipedOutputStream os = new PipedOutputStream(in1);
        PipedInputStream in2 = new PipedInputStream();
        boolean thrown = false;
        try {
            os.connect(in2);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "connect on already connected stream must throw IOException");
    }

    static void testConnectSecondTimeOnSameOutputThrows() throws IOException {
        PipedOutputStream os = new PipedOutputStream();
        PipedInputStream in1 = new PipedInputStream();
        PipedInputStream in2 = new PipedInputStream();
        os.connect(in1);
        boolean thrown = false;
        try {
            os.connect(in2);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "second connect of same PipedOutputStream must throw IOException");
    }

    static void testConnectToAlreadyConnectedInputThrows() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream first = new PipedOutputStream();
        first.connect(in);
        PipedOutputStream second = new PipedOutputStream();
        boolean thrown = false;
        try {
            second.connect(in);
        } catch (IOException e) {
            thrown = true;
        }
        check(thrown, "connecting to an already connected PipedInputStream must throw IOException");
    }

    static void testFailedConnectKeepsOriginalConnection() throws IOException {
        PipedInputStream in1 = new PipedInputStream();
        PipedInputStream in2 = new PipedInputStream();
        PipedOutputStream os = new PipedOutputStream(in1);
        try {
            os.connect(in2);
        } catch (IOException e) {
            // expected
        }
        os.write(9);
        check(in1.read() == 9, "data must still flow to the original sink after a failed connect");
    }

    static void testConnectedPipeTransfersData() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream os = new PipedOutputStream();
        os.connect(in);
        os.write(65);
        os.write(66);
        os.write(67);
        check(in.read() == 65, "first byte mismatch");
        check(in.read() == 66, "second byte mismatch");
        check(in.read() == 67, "third byte mismatch");
    }
}
