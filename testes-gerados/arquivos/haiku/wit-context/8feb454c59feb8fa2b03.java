/*
 * @test
 * @summary Verifies that TreeMap.entrySet() throws InternalError as in the current checkout excerpt
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        TreeMap<String, Integer> empty = new TreeMap<>();
        try {
            empty.entrySet();
            throw new AssertionError("expected InternalError from entrySet()");
        } catch (InternalError expected) {
        }

        TreeMap<String, Integer> filled = new TreeMap<>();
        filled.put("a", 1);
        try {
            filled.entrySet();
            throw new AssertionError("expected InternalError from entrySet() on non-empty map");
        } catch (InternalError expected) {
        }
    }
}
