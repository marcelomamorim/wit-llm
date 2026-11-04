/*
 * @test
 * @summary Verify that replaceAll on a singletonList list always throws UnsupportedOperationException and leaves the element unchanged
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        testReplaceAllThrowsUnsupportedOperation();
        testReplaceAllWithNullOperatorThrowsUnsupportedOperation();
        testElementUnchangedAfterReplaceAllAttempt();
    }

    static void testReplaceAllThrowsUnsupportedOperation() {
        List<String> list = Collections.singletonList("a");
        UnaryOperator<String> op = s -> s + "x";
        boolean thrown = false;
        try {
            list.replaceAll(op);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from singletonList.replaceAll");
        }
    }

    static void testReplaceAllWithNullOperatorThrowsUnsupportedOperation() {
        List<String> list = Collections.singletonList("a");
        boolean thrown = false;
        try {
            list.replaceAll(null);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException for null operator");
        }
    }

    static void testElementUnchangedAfterReplaceAllAttempt() {
        List<String> list = Collections.singletonList("a");
        UnaryOperator<String> op = s -> s + "x";
        try {
            list.replaceAll(op);
        } catch (UnsupportedOperationException e) {
            // expected
        }
        if (list.size() != 1 || !"a".equals(list.get(0))) {
            throw new RuntimeException("singletonList content changed after failed replaceAll");
        }
    }
}
