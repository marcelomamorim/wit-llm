/*
 * @test
 * @summary Test BufferedOutputStream constructor with invalid size.
 * @run main BufferedOutputStreamWitupTest
 */

import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class BufferedOutputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithZeroSize();
        testConstructorWithNegativeSize();
        testConstructorWithValidSize();
    }

    private static void testConstructorWithZeroSize() {
        OutputStream dummyOut = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                // no-op
            }
        };
        try {
            new BufferedOutputStream(dummyOut, 0);
            System.err.println("Failed: Expected IllegalArgumentException for size=0");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testConstructorWithNegativeSize() {
        OutputStream dummyOut = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                // no-op
            }
        };
        try {
            new BufferedOutputStream(dummyOut, -10);
            System.err.println("Failed: Expected IllegalArgumentException for negative size");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testConstructorWithValidSize() {
        OutputStream dummyOut = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                // no-op
            }
        };
        try {
            BufferedOutputStream bos = new BufferedOutputStream(dummyOut, 1024);
            if (bos == null) {
                System.err.println("Failed: Constructor returned null");
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
            System.exit(1);
        }
    }
}
