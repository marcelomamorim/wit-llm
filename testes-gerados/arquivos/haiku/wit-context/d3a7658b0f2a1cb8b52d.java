/*
 * @test
 * @summary Behavioral checks for ArrayList.hashCode() following the List.hashCode contract
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testEmptyList();
        testMatchesListContract();
        testNullElements();
        testStableAcrossCalls();
        testChangesAfterModification();
        testLargeList();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static int expectedHash(List<?> list) {
        int h = 1;
        for (Object e : list) {
            h = 31 * h + (e == null ? 0 : e.hashCode());
        }
        return h;
    }

    static void testEmptyList() {
        List<String> list = new ArrayList<>();
        check(list.hashCode() == 1, "empty list hashCode must be 1");
    }

    static void testMatchesListContract() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        check(list.hashCode() == Arrays.asList(1, 2, 3).hashCode(),
              "hashCode must match Arrays.asList with same elements");
        check(list.hashCode() == new LinkedList<>(list).hashCode(),
              "hashCode must match LinkedList with same elements");
        check(list.hashCode() == expectedHash(list),
              "hashCode must match the List hashCode formula");
    }

    static void testNullElements() {
        List<String> list = new ArrayList<>();
        list.add(null);
        list.add("a");
        check(list.hashCode() == Arrays.asList(null, "a").hashCode(),
              "null elements must contribute 0 to hashCode");
        check(list.hashCode() == expectedHash(list),
              "hashCode with null must match formula");
    }

    static void testStableAcrossCalls() {
        List<String> list = new ArrayList<>();
        list.add("x");
        list.add("y");
        int first = list.hashCode();
        int second = list.hashCode();
        check(first == second, "repeated hashCode calls on unchanged list must agree");
    }

    static void testChangesAfterModification() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        int before = list.hashCode();
        list.add(4);
        check(list.hashCode() == expectedHash(list), "hashCode after add must match formula");
        check(list.hashCode() != before || expectedHash(list) == before,
              "hashCode after add must reflect new contents");
        list.remove(3);
        check(list.hashCode() == before, "hashCode after removing last element must restore earlier value");
    }

    static void testLargeList() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }
        check(list.hashCode() == expectedHash(list), "hashCode of large list must match formula");
        check(list.hashCode() == new LinkedList<>(list).hashCode(),
              "large list hashCode must match LinkedList");
    }
}
