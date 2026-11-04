/* @test    @summary Testa o construtor BufferedReader com tamanho inválido para lançar IllegalArgumentException    @run main BufferedReaderWitupTest */

import java.io.BufferedReader;
import java.io.StringReader;

public class BufferedReaderWitupTest {

    public static void main(String[] args) {
        testConstructorWithInvalidSize();
        testConstructorWithValidSize();
    }

    private static void testConstructorWithInvalidSize() {
        boolean exceptionThrown = false;
        try {
            new BufferedReader(new StringReader("test"), 0);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for size 0");
        }
        exceptionThrown = false;
        try {
            new BufferedReader(new StringReader("test"), -1);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative size");
        }
    }

    private static void testConstructorWithValidSize() {
        BufferedReader br = new BufferedReader(new StringReader("abc"), 10);
        if (br == null) {
            throw new AssertionError("BufferedReader should be instantiated successfully with valid size");
        }
    }
}
