/*
 * @test
 * @summary Verify index handling of the list returned by Collections.singletonList, which delegates to SingletonList.get(int)
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {
    private static void expectIndexOutOfBounds(List<String> list, int index) {
        try {
            list.get(index);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new AssertionError("expected IndexOutOfBoundsException for index " + index);
    }

    public static void main(String[] args) {
        List<String> single = Collections.singletonList("x");
        if (single.size() != 1) {
            throw new AssertionError("unexpected size: " + single.size());
        }
        if (!"x".equals(single.get(0))) {
            throw new AssertionError("get(0) did not return the element");
        }
        expectIndexOutOfBounds(single, 1);
        expectIndexOutOfBounds(single, -1);
        expectIndexOutOfBounds(single, Integer.MIN_VALUE);
        expectIndexOutOfBounds(single, Integer.MAX_VALUE);

        List<String> nullable = Collections.singletonList(null);
        if (nullable.get(0) != null) {
            throw new AssertionError("get(0) should return null element");
        }
        expectIndexOutOfBounds(nullable, 1);
    }
}
