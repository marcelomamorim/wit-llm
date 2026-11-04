/*
 * @test
 * @summary Verify contains on immutable lists from List.of returns membership results and rejects null with NullPointerException
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.List;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        List<String> two = List.of("a", "b");
        check(two.contains("a"), "contains existing element");
        check(two.contains("b"), "contains second existing element");
        check(!two.contains("z"), "does not contain absent element");

        List<Integer> one = List.of(1);
        check(one.contains(1), "single element list contains its element");
        check(!one.contains(2), "single element list does not contain other element");

        expectNpe(two, "two-element list contains(null)");
        expectNpe(one, "single-element list contains(null)");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void expectNpe(List<?> list, String message) {
        try {
            list.contains(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("Expected NullPointerException: " + message);
    }
}
