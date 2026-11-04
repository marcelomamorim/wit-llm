/*
 * @test
 * @summary WITUP-derived checks for bounds handling of immutable one- and two-element lists
 * @run main ImmutableCollectionsWitupTest
 */
import java.util.List;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        List<String> one = List.of("a");
        check("a".equals(one.get(0)), "one.get(0) should return the element");
        expectIOOBE(one, 1);
        expectIOOBE(one, -1);

        List<String> two = List.of("a", "b");
        check("a".equals(two.get(0)), "two.get(0) should return the first element");
        check("b".equals(two.get(1)), "two.get(1) should return the second element");
        expectIOOBE(two, 2);
        expectIOOBE(two, -1);
    }

    static void expectIOOBE(List<String> list, int index) {
        try {
            list.get(index);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected IndexOutOfBoundsException for index " + index);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
