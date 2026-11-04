/* @test    @summary Test for Comparator.comparingLong with null keyExtractor    @run main ComparatorWitupTest */

import java.util.Comparator;
import java.util.function.ToLongFunction;

public class ComparatorWitupTest {

    public static void main(String[] args) {
        testComparingLongWithNull();
        testComparingLongWithValidFunction();
    }

    private static void testComparingLongWithNull() {
        try {
            Comparator<Object> comp = Comparator.comparingLong(null);
            // Expecting NullPointerException
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // Test passes
        }
    }

    private static void testComparingLongWithValidFunction() {
        ToLongFunction<String> lengthFunction = String::length;
        Comparator<String> comp = Comparator.comparingLong(lengthFunction);
        String s1 = "abc";
        String s2 = "abcd";
        int result = comp.compare(s1, s2);
        assert result < 0 : "Expected s1 < s2";
        String s3 = "abc";
        int resultEqual = comp.compare(s1, s3);
        assert resultEqual == 0 : "Expected s1 == s3";
    }
}
