/*
 * @test
 * @summary Verifies get(int) of the one- and two-element immutable List returns the stored elements and rejects out-of-range indices
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.List;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        List<String> one = List.of("a");
        check("a".equals(one.get(0)), "one.get(0) should return the single element");
        expectOutOfBounds(one, 1);

        List<String> two = List.of("a", "b");
        check("a".equals(two.get(0)), "two.get(0) should return the first element");
        check("b".equals(two.get(1)), "two.get(1) should return the second element");
        expectOutOfBounds(two, 2);
        expectOutOfBounds(two, -1);
    }

    static void expectOutOfBounds(List<String> list, int index) {
        try {
            list.get(index);
            throw new RuntimeException("No exception thrown for index " + index);
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
