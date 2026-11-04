/*
 * @test
 * @summary Validates Deflater.setDictionary argument bounds checks and successful dictionary setting
 * @run main DeflaterWitupTest
 */

import java.nio.charset.StandardCharsets;
import java.util.zip.Deflater;

public class DeflaterWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] dict = "hello dictionary".getBytes(StandardCharsets.UTF_8);

        expectAioobe(dict, -1, 1);
        expectAioobe(dict, 0, -1);
        expectAioobe(dict, 0, dict.length + 1);
        expectAioobe(dict, 1, dict.length);
        expectAioobe(dict, dict.length + 1, 0);

        Deflater d = new Deflater();
        try {
            d.setDictionary(dict, 0, dict.length);
        } finally {
            d.end();
        }

        Deflater d2 = new Deflater();
        try {
            d2.setDictionary(dict, dict.length, 0);
            d2.setDictionary(dict, 0, 0);
            d2.setDictionary(new byte[0], 0, 0);

            d2.setDictionary(dict, 6, 10);

            byte[] input = "hello dictionary hello dictionary".getBytes(StandardCharsets.UTF_8);
            d2.setInput(input);
            d2.finish();
            byte[] out = new byte[256];
            int n = d2.deflate(out);
            if (n <= 0) {
                throw new RuntimeException("expected compressed output after setDictionary");
            }
        } finally {
            d2.end();
        }
    }

    private static void expectAioobe(byte[] b, int off, int len) {
        Deflater d = new Deflater();
        try {
            d.setDictionary(b, off, len);
            throw new RuntimeException("no exception for off=" + off + " len=" + len);
        } catch (ArrayIndexOutOfBoundsException expected) {
            // expected
        } finally {
            d.end();
        }
    }
}
