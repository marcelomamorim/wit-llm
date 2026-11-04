/*
 * @test
 * @summary Verifies that TreeMap.lastKey() throws InternalError as in the current source
 * @run main TreeMapWitupTest
 */

import java.util.Map;
import java.util.TreeMap;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testEmptyMapThrowsInternalError();
        testPopulatedMapThrowsInternalError();
        testCopyConstructorMapThrowsInternalError();
    }

    static void testEmptyMapThrowsInternalError() {
        TreeMap<String, Integer> map = new TreeMap<String, Integer>();
        expectInternalError(map);
    }

    static void testPopulatedMapThrowsInternalError() {
        TreeMap<String, Integer> map = new TreeMap<String, Integer>();
        map.put("a", 1);
        map.put("b", 2);
        expectInternalError(map);
    }

    static void testCopyConstructorMapThrowsInternalError() {
        Map<String, Integer> source = new TreeMap<String, Integer>();
        source.put("x", 10);
        TreeMap<String, Integer> map = new TreeMap<String, Integer>(source);
        expectInternalError(map);
    }

    static void expectInternalError(TreeMap<String, Integer> map) {
        try {
            map.lastKey();
        } catch (InternalError expected) {
            return;
        }
        throw new RuntimeException("Expected InternalError from lastKey()");
    }
}
