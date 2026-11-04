/*
 * @test
 * @summary Verifies StringReader.mark(int) rejects negative read-ahead limits and accepts non-negative ones
 * @run main StringReaderWitupTest
 */

import java.io.IOException;
import java.io.StringReader;

public class StringReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testNegativeReadAheadThrowsAndKeepsState();
        testZeroAndPositiveReadAheadAllowed();
    }

    static void testNegativeReadAheadThrowsAndKeepsState() throws IOException {
        StringReader r = new StringReader("abc");
        boolean thrown = false;
        try {
            r.mark(-1);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("mark(-1) должен lançar IllegalArgumentException");
        }
        if (r.read() != 'a') {
            throw new AssertionError("Leitura deve iniciar em 'a' após mark inválido");
        }
    }

    static void testZeroAndPositiveReadAheadAllowed() throws IOException {
        StringReader r = new StringReader("xy");
        r.mark(0);
        if (r.read() != 'x') {
            throw new AssertionError("Primeiro caractere esperado: 'x'");
        }
        r.mark(Integer.MAX_VALUE);
        if (r.read() != 'y') {
            throw new AssertionError("Segundo caractere esperado: 'y'");
        }
        if (r.read() != -1) {
            throw new AssertionError("Fim de stream esperado (-1)");
        }
    }
}
