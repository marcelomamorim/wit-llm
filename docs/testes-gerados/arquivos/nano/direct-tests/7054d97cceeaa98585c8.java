/*
 * @test
 * @summary Test InflaterInputStream constructor with valid parameters
 * @run main InflaterInputStreamWitupTest
 */

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithValidParameters();
        testConstructorWithNullInputStream();
        testConstructorWithNullInflater();
        testConstructorWithInvalidSize();
    }

    static void testConstructorWithValidParameters() {
        InputStream dummyStream = new ByteArrayInputStream(new byte[0]);
        Inflater inflater = new Inflater();
        int bufferSize = 256;
        InflaterInputStream iis = new InflaterInputStream(dummyStream, inflater, bufferSize);
        assert iis != null : "InflaterInputStream should be created successfully";
    }

    static void testConstructorWithNullInputStream() {
        Inflater inflater = new Inflater();
        try {
            new InflaterInputStream(null, inflater, 256);
            assert false : "Expected NullPointerException for null InputStream";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testConstructorWithNullInflater() {
        InputStream dummyStream = new ByteArrayInputStream(new byte[0]);
        try {
            new InflaterInputStream(dummyStream, null, 256);
            assert false : "Expected NullPointerException for null Inflater";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testConstructorWithInvalidSize() {
        InputStream dummyStream = new ByteArrayInputStream(new byte[0]);
        Inflater inflater = new Inflater();
        try {
            new InflaterInputStream(dummyStream, inflater, 0);
            assert false : "Expected IllegalArgumentException for size <= 0";
        } catch (IllegalArgumentException e) {
            // expected
        }
        try {
            new InflaterInputStream(dummyStream, inflater, -10);
            assert false : "Expected IllegalArgumentException for negative size";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
