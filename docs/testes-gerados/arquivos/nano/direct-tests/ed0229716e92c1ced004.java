/*
 * @test
 * @summary Test BufferedOutputStream constructor with valid and invalid sizes
 * @run main BufferedOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.BufferedOutputStream;
import java.io.OutputStream;

public class BufferedOutputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidSize();
        testConstructorWithZeroSize();
        testConstructorWithNegativeSize();
    }

    static void testConstructorWithValidSize() {
        OutputStream underlying = new ByteArrayOutputStream();
        int size = 1024;
        BufferedOutputStream bos = new BufferedOutputStream(underlying, size);
        assert bos != null : "BufferedOutputStream should be created with valid size";
    }

    static void testConstructorWithZeroSize() {
        OutputStream underlying = new ByteArrayOutputStream();
        try {
            new BufferedOutputStream(underlying, 0);
            assert false : "Expected IllegalArgumentException for zero size";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Buffer size") : "Exception message should mention buffer size";
        }
    }

    static void testConstructorWithNegativeSize() {
        OutputStream underlying = new ByteArrayOutputStream();
        try {
            new BufferedOutputStream(underlying, -10);
            assert false : "Expected IllegalArgumentException for negative size";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Buffer size") : "Exception message should mention buffer size";
        }
    }
}
