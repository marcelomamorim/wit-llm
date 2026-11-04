/*
 * @test
 * @summary Observable bounds-checking behavior of ArrayList.get(int)
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testEmptyListRejectsAllIndices();
        testPopulatedListBoundaries();
        testNullElementIsReturned();
        testExtremeIndicesRejected();
        testCopyConstructorPreservesElements();
        testCapacityConstructorDoesNotAffectSize();
    }

    static void testEmptyListRejectsAllIndices() {
        ArrayList<String> list = new ArrayList<>();
        expectIndexOutOfBounds(list, 0);
        expectIndexOutOfBounds(list, -1);
        expectIndexOutOfBounds(list, 1);
    }

    static void testPopulatedListBoundaries() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        check("a".equals(list.get(0)), "get(0) should return first element");
        check("c".equals(list.get(2)), "get(size-1) should return last element");
        expectIndexOutOfBounds(list, 3);
        expectIndexOutOfBounds(list, -1);
    }

    static void testNullElementIsReturned() {
        ArrayList<String> list = new ArrayList<>();
        list.add(null);
        check(list.get(0) == null, "get(0) should return null element");
        expectIndexOutOfBounds(list, 1);
    }

    static void testExtremeIndicesRejected() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        expectIndexOutOfBounds(list, Integer.MIN_VALUE);
        expectIndexOutOfBounds(list, Integer.MAX_VALUE);
    }

    static void testCopyConstructorPreservesElements() {
        Collection<String> source = Arrays.asList("x", "y");
        ArrayList<String> list = new ArrayList<>(source);
        check("x".equals(list.get(0)), "copied element at 0 mismatch");
        check("y".equals(list.get(1)), "copied element at 1 mismatch");
        expectIndexOutOfBounds(list, 2);
    }

    static void testCapacityConstructorDoesNotAffectSize() {
        ArrayList<String> list = new ArrayList<>(10);
        expectIndexOutOfBounds(list, 0);
        list.add("only");
        check("only".equals(list.get(0)), "get(0) after add mismatch");
        expectIndexOutOfBounds(list, 1);
    }

    static void expectIndexOutOfBounds(ArrayList<?> list, int index) {
        try {
            Object o = list.get(index);
            throw new AssertionError("expected IndexOutOfBoundsException for index " + index + " but got " + o);
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
