/*
 * @test
 * @summary Verify Deflater.setDictionary rejects invalid offset and length with ArrayIndexOutOfBoundsException
 * @run main DeflaterWitupTest
 */

public class DeflaterWitupTest {
    public static void main(String[] args) {
        byte[] dict = new byte[16];
        for (int i = 0; i < dict.length; i++) {
            dict[i] = (byte) i;
        }
        Deflater d = new Deflater();
        try {
            expectAioobe(d, dict, -1, 4);
            expectAioobe(d, dict, 0, -1);
            expectAioobe(d, dict, 13, 4);
            expectAioobe(d, dict, 17, 0);
            d.setDictionary(dict, 0, dict.length);
            d.setDictionary(dict, dict.length, 0);
        } finally {
            d.end();
        }
    }

    static void expectAioobe(Deflater d, byte[] b, int off, int len) {
        try {
            d.setDictionary(b, off, len);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected ArrayIndexOutOfBoundsException off=" + off + " len=" + len);
    }
}
