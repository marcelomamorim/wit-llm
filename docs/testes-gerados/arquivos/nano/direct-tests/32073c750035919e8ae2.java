/* @test    @summary Test for Vector's forEachRemaining method to ensure it applies the Consumer to all remaining elements and detects concurrent modifications.    @run main VectorWitupTest */

import java.util.Vector;
import java.util.function.Consumer;
import java.util.ConcurrentModificationException;

public class VectorWitupTest {

    public static void main(String[] args) {
        testForEachRemainingNormal();
        testForEachRemainingWithConcurrentModification();
    }

    static void testForEachRemainingNormal() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        StringBuilder result = new StringBuilder();
        vector.forEachRemaining(new Consumer<Integer>() {
            private boolean started = false;
            @Override
            public void accept(Integer e) {
                result.append(e);
            }
        });
        // Since forEachRemaining is called on the entire vector, it should process all elements
        // but in this test, we simulate calling it with an iterator starting at position 0
        // For simplicity, we call it directly on the vector, which is not typical, but acceptable for this test
        // as the method is public and can be invoked directly.
        // The expected result is "123"
        if (!"123".equals(result.toString())) {
            throw new AssertionError("Expected '123', but got: " + result.toString());
        }
    }

    static void testForEachRemainingWithConcurrentModification() {
        Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);
        try {
            // Create an iterator by calling forEachRemaining on a separate thread or simulate concurrent modification
            // Since forEachRemaining is a void method, we simulate concurrent modification by modifying the vector during iteration
            // but in this test, we directly modify the vector after starting iteration
            // For simplicity, we invoke forEachRemaining and modify the vector immediately after
            vector.forEachRemaining(new Consumer<Integer>() {
                @Override
                public void accept(Integer e) {
                    // do nothing
                }
            });
            // Modify the vector after calling forEachRemaining
            vector.add(4);
            // Call forEachRemaining again, expecting a ConcurrentModificationException
            vector.forEachRemaining(e -> {});
            throw new AssertionError("Expected ConcurrentModificationException was not thrown.");
        } catch (ConcurrentModificationException e) {
            // Expected exception
        }
    }
}
