/*
 * @test
 * @summary Black-box tests for ArrayList.lastIndexOf and the SubList.lastIndexOf path reached via subList
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.List;

public class ArrayListWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void checkEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new RuntimeException(message + ": expected " + expected + " but was " + actual);
        }
    }

    static void testEmptyList() {
        ArrayList<String> list = new ArrayList<>();
        checkEquals(-1, list.lastIndexOf("a"), "empty list lastIndexOf");
        checkEquals(-1, list.lastIndexOf(null), "empty list lastIndexOf(null)");
    }

    static void testNotFound() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        checkEquals(-1, list.lastIndexOf("z"), "absent element");
    }

    static void testDuplicatesReturnLast() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("a");
        checkEquals(2, list.lastIndexOf("a"), "last of duplicates");
        checkEquals(1, list.lastIndexOf("b"), "single occurrence");
    }

    static void testNullElement() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add(null);
        list.add("y");
        list.add(null);
        checkEquals(3, list.lastIndexOf(null), "last null");
    }

    static void testEqualsSemantics() {
        ArrayList<String> list = new ArrayList<>();
        list.add(new String("key"));
        checkEquals(0, list.lastIndexOf("key"), "equals-based match");
    }

    static void testSubListRelativeIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("a");
        list.add("c");
        List<String> sub = list.subList(1, 4);
        checkEquals(1, sub.lastIndexOf("a"), "sublist relative index");
        checkEquals(-1, list.subList(1, 2).lastIndexOf("a"), "sublist without match");
        checkEquals(2, list.subList(0, 3).lastIndexOf("a"), "sublist prefix");
        checkEquals(-1, list.subList(0, 3).lastIndexOf("c"), "sublist excludes tail");
    }

    static void testSubListComodificationDetected() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        List<String> sub = list.subList(0, 2);
        list.add("c");
        boolean thrown = false;
        try {
            sub.lastIndexOf("a");
        } catch (java.util.ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "sublist lastIndexOf after structural change must throw ConcurrentModificationException");
    }

    public static void main(String[] args) {
        testEmptyList();
        testNotFound();
        testDuplicatesReturnLast();
        testNullElement();
        testEqualsSemantics();
        testSubListRelativeIndex();
        testSubListComodificationDetected();
    }
}
