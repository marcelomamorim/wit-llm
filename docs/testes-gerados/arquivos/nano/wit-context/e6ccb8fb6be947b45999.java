/*
 * @test
 * @summary Test PipedOutputStream constructor with PipedInputStream argument for null and connected cases.
 * @run main PipedOutputStreamWitupTest
 */

import java.io.*;

public class PipedOutputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithNull();
        testConstructorWithConnectedStream();
    }

    private static void testConstructorWithNull() {
        try {
            new PipedOutputStream((PipedInputStream) null);
            System.err.println("Failed: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (IOException e) {
            System.err.println("Failed: Unexpected IOException: " + e);
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }

    private static void testConstructorWithConnectedStream() {
        PipedInputStream pis = new PipedInputStream();
        try {
            PipedOutputStream pos = new PipedOutputStream(pis);
            if (pos == null) {
                System.err.println("Failed: Constructor returned null");
            } else {
                System.out.println("Passed: Constructor with connected stream");
            }
        } catch (IOException e) {
            System.err.println("Failed: IOException during construction: " + e);
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }
}
