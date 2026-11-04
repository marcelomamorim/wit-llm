/*
 * @test
 * @summary Checks Collections checked list replaceAll: applies operator, rejects null operator, and enforces element type
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UnaryOperator;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testReplacesAllElements();
        testNullOperatorThrowsNPE();
        testWrongTypeThrowsCCEAndLeavesListUnchanged();
        testNullResultIsAllowed();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testReplacesAllElements() {
        List<String> list = Collections.checkedList(
                new ArrayList<>(Arrays.asList("a", "b")), String.class);
        list.replaceAll(s -> s.toUpperCase());
        check(list.equals(Arrays.asList("A", "B")),
              "replaceAll should apply operator to every element, got " + list);
    }

    static void testNullOperatorThrowsNPE() {
        List<String> list = Collections.checkedList(
                new ArrayList<>(Arrays.asList("a")), String.class);
        boolean thrown = false;
        try {
            list.replaceAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "replaceAll(null) should throw NullPointerException");
        check(list.equals(Arrays.asList("a")), "list should be unchanged after null operator");
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    static void testWrongTypeThrowsCCEAndLeavesListUnchanged() {
        List raw = Collections.checkedList(
                new ArrayList<Object>(Arrays.asList("a", "b")), String.class);
        UnaryOperator op = s -> Integer.valueOf(1);
        boolean thrown = false;
        try {
            raw.replaceAll(op);
        } catch (ClassCastException e) {
            thrown = true;
        }
        check(thrown, "operator returning non-String should throw ClassCastException");
        check(raw.equals(Arrays.asList("a", "b")),
              "list should be unchanged when first replacement fails, got " + raw);
    }

    static void testNullResultIsAllowed() {
        List<String> list = Collections.checkedList(
                new ArrayList<>(Arrays.asList("a", "b")), String.class);
        list.replaceAll(s -> null);
        check(list.size() == 2 && list.get(0) == null && list.get(1) == null,
              "null results should be accepted by type check");
    }
}
