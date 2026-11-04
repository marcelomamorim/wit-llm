/* @test    @summary Testa o construtor de BufferedWriter com tamanho inválido.    @run main BufferedWriterWitupTest */

import java.io.BufferedWriter;
import java.io.StringWriter;

public class BufferedWriterWitupTest {

    public static void main(String[] args) {
        testConstructorWithInvalidSize();
    }

    public static void testConstructorWithInvalidSize() {
        StringWriter sw = new StringWriter();
        boolean exceptionThrown = false;
        try {
            new BufferedWriter(sw, 0);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for size 0");
        }
        exceptionThrown = false;
        try {
            new BufferedWriter(sw, -1);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative size");
        }
    }
}
