/*
 * @test
 * @summary Test sun.text.normalizer.NormalizerBase.setIndex(int) for valid and invalid indices
 * @run main NormalizerBaseWitupTest
 */

import sun.text.normalizer.NormalizerBase;
import java.text.StringCharacterIterator;

public class NormalizerBaseWitupTest {

    public static void main(String[] args) {
        testSetIndexValid();
        testSetIndexInvalidNegative();
        testSetIndexInvalidTooLarge();
    }

    private static void testSetIndexValid() {
        // Create NormalizerBase with a simple string and Mode.COMPOSE
        NormalizerBase nb = new NormalizerBase("abc", NormalizerBase.Mode.COMPOSE);
        // Set index to 0, expect current() to return first char code point
        int result = nb.setIndex(0);
        if (result != 'a') {
            throw new RuntimeException("Expected current() to return 'a' code point, got: " + result);
        }
        // Set index to 2, expect current() to return 'c'
        result = nb.setIndex(2);
        if (result != 'c') {
            throw new RuntimeException("Expected current() to return 'c' code point, got: " + result);
        }
    }

    private static void testSetIndexInvalidNegative() {
        NormalizerBase nb = new NormalizerBase("abc", NormalizerBase.Mode.COMPOSE);
        // Setting index to negative value should throw IllegalArgumentException
        try {
            nb.setIndex(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative index");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (StringIndexOutOfBoundsException e) {
            // Acceptable alternative if implementation throws this
        }
    }

    private static void testSetIndexInvalidTooLarge() {
        NormalizerBase nb = new NormalizerBase("abc", NormalizerBase.Mode.COMPOSE);
        // Setting index beyond length should throw IllegalArgumentException or StringIndexOutOfBoundsException
        try {
            nb.setIndex(4); // length is 3
            throw new RuntimeException("Expected IllegalArgumentException or StringIndexOutOfBoundsException for too large index");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (StringIndexOutOfBoundsException e) {
            // Expected
        }
    }
}
