/*
 * @test
 * @summary Test java.util.zip.Inflater.setDictionary(byte[], int, int) for bounds checking and normal usage
 * @run main InflaterWitupTest
 */

import java.util.zip.Inflater;

public class InflaterWitupTest {
    public static void main(String[] args) throws Exception {
        testSetDictionaryBoundsChecks();
        testSetDictionaryNormal();
    }

    private static void testSetDictionaryBoundsChecks() {
        Inflater inflater = new Inflater();
        byte[] dict = new byte[10];

        // off < 0
        try {
            inflater.setDictionary(dict, -1, 5);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for off < 0");
        } catch (ArrayIndexOutOfBoundsException expected) {
        }

        // len < 0
        try {
            inflater.setDictionary(dict, 0, -1);
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for len < 0");
        } catch (ArrayIndexOutOfBoundsException expected) {
        }

        // off + len > dict.length
        try {
            inflater.setDictionary(dict, 6, 5); // 6 + 5 = 11 > 10
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for off + len > dict.length");
        } catch (ArrayIndexOutOfBoundsException expected) {
        }
    }

    private static void testSetDictionaryNormal() {
        Inflater inflater = new Inflater();
        byte[] dict = new byte[10];
        for (int i = 0; i < dict.length; i++) {
            dict[i] = (byte) i;
        }

        // Use valid offset and length
        inflater.setDictionary(dict, 0, dict.length);

        // No exception expected, test passes if no exception thrown
    }
}
