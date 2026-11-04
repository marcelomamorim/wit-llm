/*
 * @test
 * @summary Test for Inflater.setDictionary with various inputs
 * @run main InflaterWitupTest
 */

import java.util.zip.Inflater;

public class InflaterWitupTest {

    public static void main(String[] args) {
        testSetDictionaryValid();
        testSetDictionaryNegativeOffset();
        testSetDictionaryNegativeLength();
        testSetDictionaryOffsetExceedsArray();
    }

    static void testSetDictionaryValid() {
        try {
            Inflater inflater = new Inflater();
            byte[] dict = {1, 2, 3, 4, 5};
            inflater.setDictionary(dict, 0, dict.length);
            System.out.println("testSetDictionaryValid passed");
        } catch (Exception e) {
            System.err.println("testSetDictionaryValid failed: " + e);
        }
    }

    static void testSetDictionaryNegativeOffset() {
        try {
            Inflater inflater = new Inflater();
            byte[] dict = {1, 2, 3};
            inflater.setDictionary(dict, -1, 2);
            System.err.println("testSetDictionaryNegativeOffset failed: no exception thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("testSetDictionaryNegativeOffset passed");
        } catch (Exception e) {
            System.err.println("testSetDictionaryNegativeOffset failed: " + e);
        }
    }

    static void testSetDictionaryNegativeLength() {
        try {
            Inflater inflater = new Inflater();
            byte[] dict = {1, 2, 3};
            inflater.setDictionary(dict, 0, -1);
            System.err.println("testSetDictionaryNegativeLength failed: no exception thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("testSetDictionaryNegativeLength passed");
        } catch (Exception e) {
            System.err.println("testSetDictionaryNegativeLength failed: " + e);
        }
    }

    static void testSetDictionaryOffsetExceedsArray() {
        try {
            Inflater inflater = new Inflater();
            byte[] dict = {1, 2, 3};
            inflater.setDictionary(dict, 4, 1);
            System.err.println("testSetDictionaryOffsetExceedsArray failed: no exception thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("testSetDictionaryOffsetExceedsArray passed");
        } catch (Exception e) {
            System.err.println("testSetDictionaryOffsetExceedsArray failed: " + e);
        }
    }
}
