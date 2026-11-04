/*
 * @test
 * @summary Teste do construtor PushbackInputStream com tamanho inválido
 * @run main PushbackInputStreamWitupTest
 */

import java.io.InputStream;
import java.io.PushbackInputStream;

public class PushbackInputStreamWitupTest {

    public static void main(String[] args) {
        testConstructorWithInvalidSize();
        testConstructorWithValidSize();
    }

    private static void testConstructorWithInvalidSize() {
        InputStream dummyStream = new InputStream() {
            @Override
            public int read() {
                return -1;
            }
        };
        boolean exceptionThrown = false;
        try {
            new PushbackInputStream(dummyStream, 0);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for size=0");
        }
        exceptionThrown = false;
        try {
            new PushbackInputStream(dummyStream, -1);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for size=-1");
        }
    }

    private static void testConstructorWithValidSize() {
        InputStream dummyStream = new InputStream() {
            @Override
            public int read() {
                return -1;
            }
        };
        int validSize = 5;
        PushbackInputStream pbi = new PushbackInputStream(dummyStream, validSize);
        if (pbi == null) {
            throw new AssertionError("PushbackInputStream should not be null for valid size");
        }
        // Verifica se o buffer foi inicializado corretamente
        try {
            java.lang.reflect.Field bufField = PushbackInputStream.class.getDeclaredField("buf");
            bufField.setAccessible(true);
            byte[] buf = (byte[]) bufField.get(pbi);
            if (buf.length != validSize) {
                throw new AssertionError("Buffer size mismatch");
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new AssertionError("Reflection failed: " + e.getMessage());
        }
    }
}
