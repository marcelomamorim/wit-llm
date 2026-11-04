/*
 * @test
 * @summary Verifies argument validation of Inflater.setDictionary before the native call
 * @run main InflaterWitupTest
 */

import java.util.zip.Inflater;

public class InflaterWitupTest {
    public static void main(String[] args) throws Exception {
        Inflater inf = new Inflater();
        try {
            expectAioobe(inf, new byte[4], -1, 2);
            expectAioobe(inf, new byte[4], 0, -1);
            expectAioobe(inf, new byte[4], 2, 3);
            expectAioobe(inf, new byte[4], 1, 4);
            expectAioobe(inf, new byte[4], Integer.MAX_VALUE, 1);
            expectAioobe(inf, new byte[0], 1, 0);
        } finally {
            inf.end();
        }

        Inflater closed = new Inflater();
        closed.end();
        expectAioobe(closed, new byte[4], 0, -1);
        expectAioobe(closed, new byte[4], 3, 2);
    }

    static void expectAioobe(Inflater inf, byte[] dict, int off, int len) {
        try {
            inf.setDictionary(dict, off, len);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for off=" + off + " len=" + len);
    }
}
