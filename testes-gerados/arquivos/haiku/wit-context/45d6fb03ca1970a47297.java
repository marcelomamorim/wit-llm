/*
 * @test
 * @summary Verifies Arrays.asList(...).replaceAll(UnaryOperator) null-operator rejection and element replacement behavior
 * @run main ArraysWitupTest
 */
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testNullOperatorThrowsNPE();
        testNullOperatorLeavesListUnchanged();
        testReplaceAllAppliesOperator();
        testReplaceAllWritesThroughToBackingArray();
    }

    static void testNullOperatorThrowsNPE() {
        List<String> list = Arrays.asList("a", "b");
        boolean thrown = false;
        try {
            list.replaceAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null operator");
        }
    }

    static void testNullOperatorLeavesListUnchanged() {
        String[] arr = {"a", "b"};
        List<String> list = Arrays.asList(arr);
        try {
            list.replaceAll(null);
        } catch (NullPointerException e) {
            // expected
        }
        if (!"a".equals(list.get(0)) || !"b".equals(list.get(1))) {
            throw new RuntimeException("List was modified after NullPointerException");
        }
    }

    static void testReplaceAllAppliesOperator() {
        List<String> list = Arrays.asList("a", "b", "c");
        UnaryOperator<String> op = s -> s.toUpperCase();
        list.replaceAll(op);
        if (!"A".equals(list.get(0)) || !"B".equals(list.get(1)) || !"C".equals(list.get(2))) {
            throw new RuntimeException("replaceAll did not apply operator to every element: " + list);
        }
    }

    static void testReplaceAllWritesThroughToBackingArray() {
        String[] arr = {"x", "y"};
        List<String> list = Arrays.asList(arr);
        UnaryOperator<String> op = s -> s + "!";
        list.replaceAll(op);
        if (!"x!".equals(arr[0]) || !"y!".equals(arr[1])) {
            throw new RuntimeException("replaceAll did not write through to backing array");
        }
    }
}
