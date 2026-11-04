/* @test    @summary Test for Comparator.comparingInt method    @run main ComparatorWitupTest */

import java.util.Comparator;
import java.util.Objects;
import java.util.function.ToIntFunction;

public class ComparatorWitupTest {

    public static void main(String[] args) {
        testComparingIntWithPositiveNumbers();
        testComparingIntWithNegativeNumbers();
        testComparingIntWithZero();
        testComparingIntWithNullKeyExtractor();
    }

    static void testComparingIntWithPositiveNumbers() {
        ToIntFunction<String> lengthExtractor = String::length;
        Comparator<String> comparator = Comparator.comparingInt(lengthExtractor);
        String s1 = "abc";
        String s2 = "abcd";
        int result = comparator.compare(s1, s2);
        assert result < 0 : "Expected s1 < s2 based on length";
        int reverseResult = comparator.reversed().compare(s2, s1);
        assert reverseResult > 0 : "Expected reversed comparator to give s2 > s1";
    }

    static void testComparingIntWithNegativeNumbers() {
        ToIntFunction<Integer> negate = x -> -x;
        Comparator<Integer> comparator = Comparator.comparingInt(negate);
        Integer a = 1;
        Integer b = 2;
        int result = comparator.compare(a, b);
        assert result > 0 : "Expected a > b when comparing with negation";
    }

    static void testComparingIntWithZero() {
        ToIntFunction<Object> alwaysZero = obj -> 0;
        Comparator<Object> comparator = Comparator.comparingInt(alwaysZero);
        Object obj1 = new Object();
        Object obj2 = new Object();
        int result = comparator.compare(obj1, obj2);
        assert result == 0 : "Expected all objects to be equal when key extractor returns zero";
    }

    static void testComparingIntWithNullKeyExtractor() {
        try {
            Comparator.comparingInt(null);
            assert false : "Expected NullPointerException when keyExtractor is null";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
