/* @test    @summary Test for Deflater.setDictionary with valid and invalid inputs    @run main DeflaterWitupTest */

import java.util.zip.Deflater;

public class DeflaterWitupTest {

    public static void main(String[] args) {
        testSetDictionaryValid();
        testSetDictionaryNull();
        testSetDictionaryOutOfBounds();
    }

    private static void testSetDictionaryValid() {
        try {
            Deflater deflater = new Deflater();
            byte[] dict = {1, 2, 3, 4, 5};
            deflater.setDictionary(dict, 0, dict.length);
            deflater.end();
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in valid setDictionary", e);
        }
    }

    private static void testSetDictionaryNull() {
        try {
            Deflater deflater = new Deflater();
            deflater.setDictionary(null, 0, 0);
            deflater.end();
            // Expect no exception, as null dictionary with zero length is valid
        } catch (NullPointerException e) {
            // Acceptable if null is not handled gracefully
            throw new RuntimeException("Null dictionary should be handled gracefully", e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in null dictionary", e);
        }
    }

    private static void testSetDictionaryOutOfBounds() {
        try {
            Deflater deflater = new Deflater();
            byte[] dict = {1, 2, 3};
            // off + len exceeds array length
            deflater.setDictionary(dict, 2, 2);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException not thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception in out of bounds test", e);
        } finally {
            // Ensure resources are released
            try {
                deflater.end();
            } catch (Exception e) {
                // ignore
            }
        }
    }
}
