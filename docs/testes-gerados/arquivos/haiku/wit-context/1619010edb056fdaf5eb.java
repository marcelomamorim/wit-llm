/*
 * @test
 * @summary Behavioral tests for ArrayList.indexOf and SubList.indexOf, including comodification detection
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class ArrayListWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void testIndexOfNullAndNonNull() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add(null);
        list.add("b");
        check(list.indexOf(null) == 1, "indexOf(null) should return index of first null");
        check(list.indexOf("b") == 2, "indexOf(\"b\") should return 2");
        check(list.indexOf("a") == 0, "indexOf(\"a\") should return 0");
        check(list.indexOf("zzz") == -1, "indexOf of absent element should return -1");
    }

    static void testIndexOfEmptyList() {
        ArrayList<String> list = new ArrayList<>();
        check(list.indexOf(null) == -1, "indexOf(null) on empty list should be -1");
        check(list.indexOf("a") == -1, "indexOf on empty list should be -1");
    }

    static void testIndexOfReturnsFirstOccurrence() {
        ArrayList<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        list.add("x");
        check(list.indexOf("x") == 0, "indexOf should return first occurrence");
        ArrayList<String> nulls = new ArrayList<>();
        nulls.add("p");
        nulls.add(null);
        nulls.add(null);
        check(nulls.indexOf(null) == 1, "indexOf(null) should return first null occurrence");
    }

    static void testIndexOfUsesEquals() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        check(list.indexOf(new String("b")) == 1, "indexOf should use equals semantics");
    }

    static void testSubListIndexOfRelativeIndex() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("b");
        List<String> sub = list.subList(1, 4);
        check(sub.indexOf("b") == 0, "subList indexOf should be relative to sublist offset");
        check(sub.indexOf("c") == 1, "subList indexOf of c should be 1");
        check(sub.indexOf("a") == -1, "subList indexOf should not find elements outside range");
    }

    static void testSubListIndexOfNull() {
        ArrayList<String> list = new ArrayList<>();
        list.add(null);
        list.add("x");
        List<String> sub = list.subList(0, 1);
        check(sub.indexOf(null) == 0, "subList indexOf(null) should be 0");
        check(sub.indexOf("x") == -1, "subList indexOf should not see element outside range");
    }

    static void testSubListComodificationDetected() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        List<String> sub = list.subList(0, 1);
        list.add("c");
        boolean thrown = false;
        try {
            sub.indexOf("a");
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "subList indexOf after root structural modification should throw CME");
    }

    static void testSubListComodificationDetectedEmptyRangeNull() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        List<String> sub = list.subList(0, 0);
        list.add("b");
        boolean thrown = false;
        try {
            sub.indexOf(null);
        } catch (ConcurrentModificationException e) {
            thrown = true;
        }
        check(thrown, "empty subList indexOf(null) after root modification should throw CME");
    }

    public static void main(String[] args) {
        testIndexOfNullAndNonNull();
        testIndexOfEmptyList();
        testIndexOfReturnsFirstOccurrence();
        testIndexOfUsesEquals();
        testSubListIndexOfRelativeIndex();
        testSubListIndexOfNull();
        testSubListComodificationDetected();
        testSubListComodificationDetectedEmptyRangeNull();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
