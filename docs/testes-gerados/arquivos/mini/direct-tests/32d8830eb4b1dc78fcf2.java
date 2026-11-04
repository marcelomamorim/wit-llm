/*
 * @test
 * @summary Test Comparator.comparingInt with various key extractors
 * @run main java.util.ComparatorWitupTest
 */

import java.util.Comparator;
import java.util.function.ToIntFunction;
import java.util.Objects;

public class ComparatorWitupTest {
    public static void main(String[] args) {
        testNullKeyExtractor();
        testComparatorOrdering();
        testComparatorWithEqualKeys();
        testComparatorWithNegativeAndPositiveKeys();
    }

    static void testNullKeyExtractor() {
        try {
            Comparator.comparingInt(null);
            throw new RuntimeException("Expected NullPointerException for null keyExtractor");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testComparatorOrdering() {
        ToIntFunction<String> lengthExtractor = new ToIntFunction<String>() {
            public int applyAsInt(String s) {
                return s.length();
            }
        };
        Comparator<String> comp = Comparator.comparingInt(lengthExtractor);

        String a = "a";      // length 1
        String bb = "bb";   // length 2
        String ccc = "ccc"; // length 3

        if (comp.compare(a, bb) >= 0) {
            throw new RuntimeException("Comparator should consider 'a' < 'bb'");
        }
        if (comp.compare(bb, a) <= 0) {
            throw new RuntimeException("Comparator should consider 'bb' > 'a'");
        }
        if (comp.compare(bb, ccc) >= 0) {
            throw new RuntimeException("Comparator should consider 'bb' < 'ccc'");
        }
        if (comp.compare(ccc, bb) <= 0) {
            throw new RuntimeException("Comparator should consider 'ccc' > 'bb'");
        }
    }

    static void testComparatorWithEqualKeys() {
        ToIntFunction<String> extractor = new ToIntFunction<String>() {
            public int applyAsInt(String s) {
                return s.length();
            }
        };
        Comparator<String> comp = Comparator.comparingInt(extractor);

        String s1 = "foo";
        String s2 = "bar";

        if (comp.compare(s1, s2) != 0) {
            throw new RuntimeException("Comparator should consider strings of equal length as equal");
        }
    }

    static void testComparatorWithNegativeAndPositiveKeys() {
        ToIntFunction<Integer> extractor = new ToIntFunction<Integer>() {
            public int applyAsInt(Integer i) {
                return i.intValue();
            }
        };
        Comparator<Integer> comp = Comparator.comparingInt(extractor);

        Integer neg = Integer.valueOf(-10);
        Integer zero = Integer.valueOf(0);
        Integer pos = Integer.valueOf(10);

        if (comp.compare(neg, zero) >= 0) {
            throw new RuntimeException("Comparator should consider negative < zero");
        }
        if (comp.compare(zero, pos) >= 0) {
            throw new RuntimeException("Comparator should consider zero < positive");
        }
        if (comp.compare(pos, neg) <= 0) {
            throw new RuntimeException("Comparator should consider positive > negative");
        }
    }
}
