/* @test    @summary Test constructor InflaterOutputStream(OutputStream, Inflater) for correct behavior and null handling    @run main InflaterOutputStreamWitupTest */

import java.io.OutputStream;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

public class InflaterOutputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidArguments();
        testConstructorWithNullOutputStream();
        testConstructorWithNullInflater();
        testConstructorWithInvalidBufferSize();
    }

    static void testConstructorWithValidArguments() {
        OutputStream dummyOut = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                // no-op
            }
        };
        Inflater inflater = new Inflater();
        try {
            InflaterOutputStream ios = new InflaterOutputStream(dummyOut, inflater);
            if (ios == null) {
                throw new AssertionError("InflaterOutputStream instance is null");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid constructor test: " + e);
        }
    }

    static void testConstructorWithNullOutputStream() {
        Inflater inflater = new Inflater();
        try {
            new InflaterOutputStream(null, inflater);
            throw new AssertionError("Expected NullPointerException for null OutputStream");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    static void testConstructorWithNullInflater() {
        OutputStream dummyOut = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                // no-op
            }
        };
        try {
            new InflaterOutputStream(dummyOut, null);
            throw new AssertionError("Expected NullPointerException for null Inflater");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    static void testConstructorWithInvalidBufferSize() {
        OutputStream dummyOut = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                // no-op
            }
        };
        Inflater inflater = new Inflater();
        try {
            new InflaterOutputStream(dummyOut, inflater, 0);
            throw new AssertionError("Expected IllegalArgumentException for buffer size 0");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
        try {
            new InflaterOutputStream(dummyOut, inflater, -1);
            throw new AssertionError("Expected IllegalArgumentException for negative buffer size");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }
}
