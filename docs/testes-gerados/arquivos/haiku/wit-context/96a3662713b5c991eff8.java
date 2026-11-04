/*
 * @test
 * @summary Witup-derived checks for ArrayList.replaceAll(UnaryOperator): element replacement and NullPointerException on null operator
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testReplaceAllAppliesOperator();
        testReplaceAllOnEmptyListDoesNothing();
        testNullOperatorThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void testReplaceAllAppliesOperator() {
        ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
        UnaryOperator<Integer> doubler = x -> x * 2;
        list.replaceAll(doubler);
        List<Integer> expected = Arrays.asList(2, 4, 6);
        check(list.equals(expected), "replaceAll did not apply operator: " + list);
    }

    static void testReplaceAllOnEmptyListDoesNothing() {
        ArrayList<String> list = new ArrayList<String>();
        UnaryOperator<String> upper = s -> s.toUpperCase();
        list.replaceAll(upper);
        check(list.isEmpty(), "empty list changed after replaceAll");
    }

    static void testNullOperatorThrowsNPE() {
        ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
        boolean thrown = false;
        try {
            list.replaceAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "replaceAll(null) did not throw NullPointerException");
        check(list.equals(Arrays.asList(1, 2, 3)),
              "list modified after replaceAll(null) threw: " + list);
    }
}
