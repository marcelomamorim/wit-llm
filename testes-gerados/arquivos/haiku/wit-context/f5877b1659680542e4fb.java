/*
 * @test
 * @summary Verify Collections.emptyList().replaceAll rejects a null operator and accepts a non-null operator
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        List<String> empty = Collections.emptyList();

        boolean thrown = false;
        try {
            empty.replaceAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected NullPointerException for null operator");
        }

        UnaryOperator<String> op = s -> s + "x";
        empty.replaceAll(op);
        if (!empty.isEmpty()) {
            throw new AssertionError("empty list must remain empty after replaceAll");
        }
    }
}
