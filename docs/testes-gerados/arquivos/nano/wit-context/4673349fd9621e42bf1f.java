/*
 * @test
 * @summary Tests for java.util.zip.InflaterOutputStream constructors and exception paths
 * @run main InflaterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

public class InflaterOutputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorNullOut();
        testConstructorNullInflater();
        testConstructorInvalidBufferSize();
    }

    private static void testConstructorNullOut() {
        try {
            new InflaterOutputStream(null, new Inflater(), 512);
            throw new AssertionError("Expected NullPointerException for null OutputStream");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testConstructorNullInflater() {
        OutputStream os = new ByteArrayOutputStream();
        try {
            new InflaterOutputStream(os, null, 512);
            throw new AssertionError("Expected NullPointerException for null Inflater");
        } catch (NullPointerException e) {
            // Expected
        }
    }

    private static void testConstructorInvalidBufferSize() {
        OutputStream os = new ByteArrayOutputStream();
        Inflater inflater = new Inflater();
        try {
            new InflaterOutputStream(os, inflater, 0);
            throw new AssertionError("Expected IllegalArgumentException for bufLen <= 0");
        } catch (IllegalArgumentException e) {
            // Expected
        }
        try {
            new InflaterOutputStream(os, inflater, -10);
            throw new AssertionError("Expected IllegalArgumentException for bufLen <= 0");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}
