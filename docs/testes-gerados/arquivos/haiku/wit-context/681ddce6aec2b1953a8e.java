/*
 * @test
 * @summary Immutable map merge (obtained via Map.of) must throw UnsupportedOperationException and keep contents unchanged
 * @run main ImmutableCollectionsWitupTest
 */
import java.util.Map;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        Map<String, Integer> one = Map.of("a", 1);
        expectUOE(() -> one.merge("a", 2, Integer::sum));
        expectUOE(() -> one.merge("b", 2, Integer::sum));
        expectUOE(() -> Map.<String, Integer>of().merge("a", 1, Integer::sum));
        expectUOE(() -> one.merge("a", 2, null));
        if (one.get("a") != 1 || one.size() != 1 || one.containsKey("b")) {
            throw new RuntimeException("map contents changed");
        }
    }

    static void expectUOE(Runnable r) {
        try {
            r.run();
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException");
    }
}
