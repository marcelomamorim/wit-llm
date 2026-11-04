/*
 * @test
 * @summary Verifies that TreeMap.firstKey() throws InternalError as implemented in the current source excerpt, for empty and non-empty maps
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        checkFirstKeyThrowsInternalError(new TreeMap<String, Integer>());

        TreeMap<String, Integer> nonEmpty = new TreeMap<>();
        nonEmpty.put("a", 1);
        nonEmpty.put("b", 2);
        checkFirstKeyThrowsInternalError(nonEmpty);
    }

    private static void checkFirstKeyThrowsInternalError(TreeMap<String, Integer> map) {
        try {
            map.firstKey();
        } catch (InternalError expected) {
            return;
        }
        throw new AssertionError("Expected InternalError from firstKey()");
    }
}
