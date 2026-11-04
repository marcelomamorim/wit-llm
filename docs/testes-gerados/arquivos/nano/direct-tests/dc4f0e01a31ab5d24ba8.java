/* @test    @summary Test removeIf method of Vector class    @run main VectorWitupTest */

import java.util.Vector;
import java.util.function.Predicate;

public class VectorWitupTest {

    public static void main(String[] args) {
        testRemoveIfRemovesMatchingElements();
        testRemoveIfNoMatchingElements();
        testRemoveIfAllMatching();
        testRemoveIfEmptyVector();
    }

    static void testRemoveIfRemovesMatchingElements() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        vector.add(4);
        Predicate<Integer> isEven = x -> x % 2 == 0;
        boolean result = vector.removeIf(isEven);
        assert result : "Expected removeIf to return true";
        assert vector.size() == 2 : "Expected size 2 after removal";
        assert vector.contains(1) : "Vector should contain 1";
        assert vector.contains(3) : "Vector should contain 3";
        assert !vector.contains(2) : "Vector should not contain 2";
        assert !vector.contains(4) : "Vector should not contain 4";
    }

    static void testRemoveIfNoMatchingElements() {
        Vector<String> vector = new Vector<>();
        vector.add("a");
        vector.add("b");
        vector.add("c");
        Predicate<String> isDigit = s -> s.chars().allMatch(Character::isDigit);
        boolean result = vector.removeIf(isDigit);
        assert !result : "Expected removeIf to return false";
        assert vector.size() == 3 : "Expected size 3 when no elements removed";
    }

    static void testRemoveIfAllMatching() {
        Vector<Integer> vector = new Vector<>();
        vector.add(2);
        vector.add(4);
        vector.add(6);
        Predicate<Integer> isEven = x -> x % 2 == 0;
        boolean result = vector.removeIf(isEven);
        assert result : "Expected removeIf to return true";
        assert vector.isEmpty() : "Vector should be empty after removing all elements";
    }

    static void testRemoveIfEmptyVector() {
        Vector<Object> vector = new Vector<>();
        Predicate<Object> alwaysTrue = o -> true;
        boolean result = vector.removeIf(alwaysTrue);
        assert !result : "Expected removeIf to return false on empty vector";
        assert vector.isEmpty() : "Vector should remain empty";
    }
}
