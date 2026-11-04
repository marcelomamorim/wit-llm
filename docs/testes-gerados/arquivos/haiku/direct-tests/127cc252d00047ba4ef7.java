/*
 * @test
 * @summary ImmutableCollections.add(int, E) must throw UnsupportedOperationException through public immutable lists
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.List;

public class ImmutableCollectionsWitupTest {
    public static void main(String[] args) {
        List<String> two = List.of("a", "b");
        expectUOE(two, 0, "x");
        expectUOE(two, 2, "x");
        if (!two.equals(List.of("a", "b"))) {
            throw new RuntimeException("immutable list was modified");
        }
        expectUOE(List.of(), 0, "x");
        expectUOE(List.of("a"), 5, "x");
        expectUOE(List.of("a"), 0, null);
        System.out.println("PASS");
    }

    static void expectUOE(List<String> list, int index, String value) {
        try {
            list.add(index, value);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException for index " + index);
    }
}
