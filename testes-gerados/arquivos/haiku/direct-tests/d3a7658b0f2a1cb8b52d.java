/*
 * @test
 * @summary Behavioral tests for ArrayList.hashCode() using only public API
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testEmptyListHashIsOne();
        testKnownValueForIntegers();
        testNullElementContributesZero();
        testEqualListsHaveEqualHash();
        testHashMatchesListContract();
        testHashChangesAfterSet();
        testHashIsStableAcrossCalls();
    }

    static void testEmptyListHashIsOne() {
        ArrayList<String> empty = new ArrayList<>();
        check(empty.hashCode() == 1,
              "empty list hash must be 1, got " + empty.hashCode());
    }

    static void testKnownValueForIntegers() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        int expected = 30817;
        check(list.hashCode() == expected,
              "hash of [1,2,3] must be " + expected + ", got " + list.hashCode());
        check(list.hashCode() == Arrays.asList(1, 2, 3).hashCode(),
              "hash must match List contract for [1,2,3]");
    }

    static void testNullElementContributesZero() {
        ArrayList<String> list = new ArrayList<>();
        list.add(null);
        check(list.hashCode() == 31,
              "hash of [null] must be 31, got " + list.hashCode());
    }

    static void testEqualListsHaveEqualHash() {
        ArrayList<Integer> a = new ArrayList<>();
        a.add(7);
        ArrayList<Integer> b = new ArrayList<>(10);
        b.add(7);
        check(a.equals(b), "lists must be equal");
        check(a.hashCode() == b.hashCode(),
              "equal lists must have equal hash codes");
    }

    static void testHashMatchesListContract() {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("a", "bb", null));
        List<String> reference = Arrays.asList("a", "bb", null);
        check(list.hashCode() == reference.hashCode(),
              "ArrayList hash must match List.hashCode() contract");
    }

    static void testHashChangesAfterSet() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
        int before = list.hashCode();
        list.set(0, 4);
        int after = list.hashCode();
        check(after != before, "hash must change after element change");
        check(after == Arrays.asList(4, 2, 3).hashCode(),
              "hash after set must match equivalent list");
    }

    static void testHashIsStableAcrossCalls() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(5, 6, 7));
        int h1 = list.hashCode();
        int h2 = list.hashCode();
        check(h1 == h2, "repeated hashCode() calls must agree");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
