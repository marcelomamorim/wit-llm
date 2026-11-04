/*
 * @test
 * @summary Test Collections.EmptyList.get(int) throws IndexOutOfBoundsException
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testEmptyListGetThrows();
    }

    private static void testEmptyListGetThrows() {
        List<Object> emptyList = Collections.emptyList();
        try {
            emptyList.get(0);
            throw new RuntimeException("Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException expected) {
            // Expected exception
        }
        try {
            emptyList.get(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException not thrown for negative index");
        } catch (IndexOutOfBoundsException expected) {
            // Expected exception
        }
    }
}
