/* @test    @summary Test InflaterOutputStream constructor with valid parameters    @run main InflaterOutputStreamWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

public class InflaterOutputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidParameters();
        testConstructorWithNullOutputStream();
        testConstructorWithNullInflater();
        testConstructorWithInvalidBufferSize();
    }

    static void testConstructorWithValidParameters() {
        OutputStream out = new ByteArrayOutputStream();
        Inflater inflater = new Inflater();
        int bufSize = 1024;
        InflaterOutputStream ios = new InflaterOutputStream(out, inflater, bufSize);
        assert ios != null : "InflaterOutputStream should be instantiated with valid parameters";
    }

    static void testConstructorWithNullOutputStream() {
        Inflater inflater = new Inflater();
        try {
            new InflaterOutputStream(null, inflater, 1024);
            assert false : "Expected NullPointerException for null OutputStream";
        } catch (NullPointerException e) {
            assert e.getMessage().equals("Null output") : "Unexpected message: " + e.getMessage();
        }
    }

    static void testConstructorWithNullInflater() {
        OutputStream out = new ByteArrayOutputStream();
        try {
            new InflaterOutputStream(out, null, 1024);
            assert false : "Expected NullPointerException for null Inflater";
        } catch (NullPointerException e) {
            assert e.getMessage().equals("Null inflater") : "Unexpected message: " + e.getMessage();
        }
    }

    static void testConstructorWithInvalidBufferSize() {
        OutputStream out = new ByteArrayOutputStream();
        Inflater inflater = new Inflater();
        try {
            new InflaterOutputStream(out, inflater, 0);
            assert false : "Expected IllegalArgumentException for buffer size <= 0";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().equals("Buffer size  1") : "Unexpected message: " + e.getMessage();
        }
    }
}
