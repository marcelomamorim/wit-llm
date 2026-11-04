/*
 * @test
 * @summary Test Collections.SingletonList.replaceAll throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        // Create a SingletonList via Collections.singletonList
        List<String> singletonList = Collections.singletonList("test");

        // Define a UnaryOperator that would replace elements if allowed
        UnaryOperator<String> operator = new UnaryOperator<String>() {
            public String apply(String s) {
                return s.toUpperCase();
            }
        };

        // Test that replaceAll throws UnsupportedOperationException
        boolean threw = false;
        try {
            singletonList.replaceAll(operator);
        } catch (UnsupportedOperationException e) {
            threw = true;
        }

        if (!threw) {
            throw new RuntimeException("Expected UnsupportedOperationException was not thrown by replaceAll on SingletonList");
        }
    }
}
