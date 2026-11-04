/*
 * @test
 * @summary Test sun.text.normalizer.NormalizerBase.setIndex(int) method
 * @run main NormalizerBaseWitupTest
 */

import sun.text.normalizer.NormalizerBase;
import sun.text.normalizer.NormalizerBase.Mode;
import java.text.StringCharacterIterator;

public class NormalizerBaseWitupTest {
    public static void main(String[] args) {
        testSetIndexWithValidIndex();
        testSetIndexWithZeroIndex();
        testSetIndexWithNegativeIndex();
    }

    private static void testSetIndexWithValidIndex() {
        String testString = "abc";
        NormalizerBase nb = new NormalizerBase(testString, Mode.COMPOSE);
        int result = nb.setIndex(1);
        int expected = testString.charAt(1);
        if (result != expected) {
            throw new RuntimeException("testSetIndexWithValidIndex failed: expected " + expected + " but got " + result);
        }
    }

    private static void testSetIndexWithZeroIndex() {
        String testString = "abc";
        NormalizerBase nb = new NormalizerBase(testString, Mode.COMPOSE);
        int result = nb.setIndex(0);
        int expected = testString.charAt(0);
        if (result != expected) {
            throw new RuntimeException("testSetIndexWithZeroIndex failed: expected " + expected + " but got " + result);
        }
    }

    private static void testSetIndexWithNegativeIndex() {
        String testString = "abc";
        NormalizerBase nb = new NormalizerBase(testString, Mode.COMPOSE);
        int result = nb.setIndex(-1);
        // The behavior for negative index depends on setIndexOnly and current implementation.
        // We check that the method returns an int without throwing exceptions.
        if (!(result >= Character.MIN_VALUE && result <= Character.MAX_VALUE)) {
            throw new RuntimeException("testSetIndexWithNegativeIndex failed: result out of char range: " + result);
        }
    }
}
