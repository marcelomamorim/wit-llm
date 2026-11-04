/*
 * @test
 * @summary WITUP expath for TreeMap.lastKey(): current checkout always throws InternalError
 * @run main TreeMapWitupTest
 */

import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        TreeMap<String, Integer> empty = new TreeMap<>();
        expectInternalError(empty);

        TreeMap<String, Integer> populated = new TreeMap<>();
        populated.put("a", 1);
        populated.put("b", 2);
        expectInternalError(populated);
    }

    static void expectInternalError(TreeMap<String, Integer> map) {
        try {
            map.lastKey();
        } catch (InternalError e) {
            return;
        }
        throw new RuntimeException("Expected InternalError from lastKey()");
    }
}
