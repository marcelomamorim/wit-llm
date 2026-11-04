/* @test    @summary Test Collections.replaceAll with a simple replacement operation    @run main CollectionsWitupTest */

import java.util.Collections;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testReplaceAllWithIncrement();
        testReplaceAllWithDouble();
    }

    static void testReplaceAllWithIncrement() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        // Wrap list in a modifiable list
        List<Integer> modifiableList = new java.util.ArrayList<>(list);
        UnaryOperator<Integer> increment = new UnaryOperator<Integer>() {
            public Integer apply(Integer x) {
                return x + 1;
            }
        };
        Collections.replaceAll(modifiableList, increment);
        // Verify that each element has been incremented by 1
        for (int i = 0; i < list.size(); i++) {
            int expected = list.get(i) + 1;
            if (modifiableList.get(i) != expected) {
                throw new RuntimeException("Test failed: element at index " + i + " was not incremented correctly.");
            }
        }
    }

    static void testReplaceAllWithDouble() {
        List<Integer> list = Arrays.asList(10, 20, 30);
        List<Integer> modifiableList = new java.util.ArrayList<>(list);
        UnaryOperator<Integer> doubleOp = new UnaryOperator<Integer>() {
            public Integer apply(Integer x) {
                return x * 2;
            }
        };
        Collections.replaceAll(modifiableList, doubleOp);
        for (int i = 0; i < list.size(); i++) {
            int expected = list.get(i) * 2;
            if (modifiableList.get(i) != expected) {
                throw new RuntimeException("Test failed: element at index " + i + " was not doubled correctly.");
            }
        }
    }
}
