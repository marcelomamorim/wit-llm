/*
 * @test
 * @summary Verify that replaceAll on the Collections.emptyMap() view rejects a null function and accepts a non-null one as a no-op
 * @run main CollectionsWitupTest
 */
import java.util.Collections;
import java.util.Map;
import java.util.function.BiFunction;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Map<String, String> empty = Collections.emptyMap();

        boolean thrown = false;
        try {
            empty.replaceAll(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected NullPointerException for null function");
        }

        BiFunction<String, String, String> identity = (k, v) -> v;
        empty.replaceAll(identity);
        if (!empty.isEmpty()) {
            throw new RuntimeException("emptyMap must remain empty after replaceAll");
        }
    }
}
