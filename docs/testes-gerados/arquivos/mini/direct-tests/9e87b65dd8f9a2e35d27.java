/*
 * @test
 * @summary Test UnsupportedOperationException from replaceAll method
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.function.UnaryOperator;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        List<String> list = Collections.unmodifiableList(new ArrayList<>());
        try {
            list.replaceAll(new UnaryOperator<String>() {
                public String apply(String s) {
                    return s.toUpperCase();
                }
            });
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
    }
}
