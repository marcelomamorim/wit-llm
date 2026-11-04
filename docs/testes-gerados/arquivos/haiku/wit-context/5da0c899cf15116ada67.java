/*
 * @test
 * @summary TreeMap.subMap(K,K) in the current checkout always throws InternalError
 * @run main TreeMapWitupTest
 */

import java.util.SortedMap;
import java.util.TreeMap;

public class TreeMapWitupTest {
    public static void main(String[] args) {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "a");
        map.put(2, "b");
        boolean thrown = false;
        try {
            SortedMap<Integer, String> sub = map.subMap(1, 2);
            if (sub == null) {
                throw new RuntimeException("subMap returned null");
            }
        } catch (InternalError e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected InternalError from subMap(K, K)");
        }
    }
}
