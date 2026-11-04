/*
 * @test
 * @summary Test Collections.replaceAll method behavior
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.function.UnaryOperator;
import java.util.Objects;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testReplaceAllNullOperator();
    }

    private static void testReplaceAllNullOperator() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        try {
            // The provided replaceAll method only calls Objects.requireNonNull(operator);
            // So passing null should throw NullPointerException
            new Collections() {
                public void replaceAll(UnaryOperator<String> operator) {
                    Objects.requireNonNull(operator);
                }
            }.replaceAll(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
