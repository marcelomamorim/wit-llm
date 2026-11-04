/*
 * @test
 * @summary Tests for InflaterOutputStream(OutputStream out, Inflater infl, int bufLen) constructor
 * @run main InflaterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;
import java.io.IOException;

public class InflaterOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testNullOutputStream();
        testNullInflater();
        testInvalidBufferLength();
        testValidConstruction();
    }

    private static void testNullOutputStream() {
        Inflater infl = new Inflater();
        try {
            new InflaterOutputStream(null, infl, 512);
            throw new RuntimeException("Expected NullPointerException for null OutputStream");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testNullInflater() {
        OutputStream out = new ByteArrayOutputStream();
        try {
            new InflaterOutputStream(out, null, 512);
            throw new RuntimeException("Expected NullPointerException for null Inflater");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testInvalidBufferLength() {
        OutputStream out = new ByteArrayOutputStream();
        Inflater infl = new Inflater();
        try {
            new InflaterOutputStream(out, infl, 0);
            throw new RuntimeException("Expected IllegalArgumentException for buffer length <= 0");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testValidConstruction() throws IOException {
        OutputStream out = new ByteArrayOutputStream();
        Inflater infl = new Inflater();
        InflaterOutputStream ios = new InflaterOutputStream(out, infl, 512);
        if (ios == null) {
            throw new RuntimeException("InflaterOutputStream constructor returned null");
        }
        ios.close(); // just to check no exception on close
    }
}
