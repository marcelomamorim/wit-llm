/*
 * @test
 * @summary Tests for InflaterInputStream constructor behavior and exception paths
 * @run main InflaterInputStreamWitupTest
 */

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.io.IOException;

public class InflaterInputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorNullIn();
        testConstructorNullInflater();
        testConstructorZeroSize();
    }

    static void testConstructorNullIn() {
        try {
            new InflaterInputStream(null, new Inflater(), 512);
            throw new AssertionError("Expected NullPointerException for null InputStream");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testConstructorNullInflater() {
        InputStream dummyStream = new ByteArrayInputStream(new byte[0]);
        try {
            new InflaterInputStream(dummyStream, null, 512);
            throw new AssertionError("Expected NullPointerException for null Inflater");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testConstructorZeroSize() {
        InputStream dummyStream = new ByteArrayInputStream(new byte[0]);
        Inflater inflater = new Inflater();
        try {
            new InflaterInputStream(dummyStream, inflater, 0);
            throw new AssertionError("Expected IllegalArgumentException for size=0");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
