/*
 * @test
 * @summary Test sun.text.RuleBasedBreakIterator.setIndex(int) for valid and invalid indices
 * @run main RuleBasedBreakIteratorWitupTest
 */

public class RuleBasedBreakIteratorWitupTest {

    public static void main(String[] args) {
        testSetIndexValid();
        testSetIndexInvalidLow();
        testSetIndexInvalidHigh();
    }

    private static void testSetIndexValid() {
        // Create a minimal subclass to instantiate abstract RuleBasedBreakIterator
        RuleBasedBreakIterator rbi = new RuleBasedBreakIterator("dummy", new byte[0]) {
            // Override abstract methods if any (none visible in excerpt)
            public char current() {
                return 'a'; // dummy return
            }
        };

        // Set rangeStart and rangeLimit via reflection is not allowed, so test only with i in [0,0]
        // Because rangeStart and rangeLimit are not accessible, we rely on default values
        // The method throws if i < rangeStart or i > rangeLimit
        // We test with i = 0 assuming rangeStart <= 0 <= rangeLimit

        try {
            char c = rbi.setIndex(0);
            if (c != 'a') {
                throw new RuntimeException("Unexpected return from setIndex(0): " + c);
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("setIndex(0) should not throw IllegalArgumentException", e);
        }
    }

    private static void testSetIndexInvalidLow() {
        RuleBasedBreakIterator rbi = new RuleBasedBreakIterator("dummy", new byte[0]) {
            public char current() {
                return 'a';
            }
        };

        // We test with i = Integer.MIN_VALUE to trigger i < rangeStart
        // According to source, this should throw IllegalArgumentException
        try {
            rbi.setIndex(Integer.MIN_VALUE);
            throw new RuntimeException("Expected IllegalArgumentException for setIndex(Integer.MIN_VALUE) not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testSetIndexInvalidHigh() {
        RuleBasedBreakIterator rbi = new RuleBasedBreakIterator("dummy", new byte[0]) {
            public char current() {
                return 'a';
            }
        };

        // We test with i = Integer.MAX_VALUE to trigger i > rangeLimit
        // According to source, this should throw IllegalArgumentException
        try {
            rbi.setIndex(Integer.MAX_VALUE);
            throw new RuntimeException("Expected IllegalArgumentException for setIndex(Integer.MAX_VALUE) not thrown");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
