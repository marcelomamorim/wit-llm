/*
 * @test
 * @summary Test InflaterInputStream constructor with Inflater parameter
 * @run main InflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testConstructorWithInflater();
        testConstructorWithNullInputStream();
        testConstructorWithNullInflater();
        testConstructorWithZeroBufferSize();
    }

    static void testConstructorWithInflater() {
        Inflater inflater = new Inflater();
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[] {1, 2, 3});
        InflaterInputStream iis = new InflaterInputStream(bais, inflater);
        assert iis != null : "InflaterInputStream should be created with valid inflater";
    }

    static void testConstructorWithNullInputStream() {
        Inflater inflater = new Inflater();
        try {
            new InflaterInputStream(null, inflater);
            assert false : "Expected NullPointerException for null InputStream";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testConstructorWithNullInflater() {
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[] {1, 2, 3});
        try {
            new InflaterInputStream(bais, null);
            assert false : "Expected NullPointerException for null Inflater";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testConstructorWithZeroBufferSize() {
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[] {1, 2, 3});
        Inflater inflater = new Inflater();
        try {
            new InflaterInputStream(bais, inflater, 0);
            assert false : "Expected IllegalArgumentException for zero buffer size";
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
