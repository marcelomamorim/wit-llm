/* @test @summary Test BufferedWriter constructor with valid and invalid buffer sizes @run main BufferedWriterWitupTest */

import java.io.Writer;
import java.io.IOException;
import java.io.StringWriter;

public class BufferedWriterWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidSize();
        testConstructorWithZeroSize();
        testConstructorWithNegativeSize();
    }

    static void testConstructorWithValidSize() {
        StringWriter sw = new StringWriter();
        int size = 1024;
        BufferedWriter bw = new BufferedWriter(sw, size);
        assert bw != null : "BufferedWriter should be created with valid size";
    }

    static void testConstructorWithZeroSize() {
        StringWriter sw = new StringWriter();
        try {
            new BufferedWriter(sw, 0);
            assert false : "Expected IllegalArgumentException for zero size";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Buffer size = 0") : "Exception message should mention buffer size";
        }
    }

    static void testConstructorWithNegativeSize() {
        StringWriter sw = new StringWriter();
        try {
            new BufferedWriter(sw, -10);
            assert false : "Expected IllegalArgumentException for negative size";
        } catch (IllegalArgumentException e) {
            assert e.getMessage().contains("Buffer size = 0") : "Exception message should mention buffer size";
        }
    }
}
