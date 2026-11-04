/*
 * @test
 * @summary Verify null operator rejection and element type checks in Collections.checkedList replaceAll
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testNullOperatorThrowsNpe();
        testTypeCheckFailureThrowsClassCastException();
        testValidReplaceAll();
    }

    static void testNullOperatorThrowsNpe() {
        List<String> list = Collections.checkedList(
                new ArrayList<>(Arrays.asList("a", "b")), String.class);
        try {
            list.replaceAll(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("Expected NullPointerException for null operator");
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    static void testTypeCheckFailureThrowsClassCastException() {
        List<String> list = Collections.checkedList(
                new ArrayList<>(Arrays.asList("a", "b")), String.class);
        UnaryOperator op = s -> Integer.valueOf(1);
        try {
            list.replaceAll((UnaryOperator) op);
        } catch (ClassCastException e) {
            return;
        }
        throw new AssertionError("Expected ClassCastException for wrong element type");
    }

    static void testValidReplaceAll() {
        List<String> list = Collections.checkedList(
                new ArrayList<>(Arrays.asList("a", "b")), String.class);
        list.replaceAll(s -> s.toUpperCase());
        if (!list.equals(Arrays.asList("A", "B"))) {
            throw new AssertionError("Unexpected list content: " + list);
        }
    }
}
