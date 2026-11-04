/*
 * @test
 * @summary Validates Inflater.setDictionary argument checks (off, len and dictionary bounds) against the current checkout.
 * @run main InflaterWitupTest
 */

import java.util.zip.Inflater;

public class InflaterWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] dict = new byte[8];

        Inflater inf = new Inflater(true);
        try {
            expectAioobe(inf, dict, -1, 2);
            expectAioobe(inf, dict, 0, -1);
            expectAioobe(inf, dict, 7, 2);
            expectAioobe(inf, dict, 9, 0);
            expectAioobe(inf, dict, 0, 9);

            inf.setDictionary(dict, 0, dict.length);
            inf.setDictionary(dict, dict.length, 0);
            inf.setDictionary(dict, 2, 6);
        } finally {
            inf.end();
        }
    }

    private static void expectAioobe(Inflater inf, byte[] dict, int off, int len) {
        try {
            inf.setDictionary(dict, off, len);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        throw new AssertionError("Expected ArrayIndexOutOfBoundsException for off=" + off + ", len=" + len);
    }
}
