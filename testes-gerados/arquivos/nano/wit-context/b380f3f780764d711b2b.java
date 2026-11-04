/* @test    @summary Test for TreeMap.replaceAll to verify behavior with null BiFunction    @run main TreeMapWitupTest */

import java.util.TreeMap;
import java.util.function.BiFunction;

public class TreeMapWitupTest {

    public static void main(String[] args) {
        testReplaceAllWithNullFunction();
        testReplaceAllWithValidFunction();
    }

    private static void testReplaceAllWithNullFunction() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        try {
            map.replaceAll(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testReplaceAllWithValidFunction() {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(1, "one");
        map.put(2, "two");
        BiFunction<Integer, String, String> func = (k, v) -> v.toUpperCase();
        map.replaceAll(func);
        if ("ONE".equals(map.get(1)) && "TWO".equals(map.get(2))) {
            System.out.println("PASS: replaceAll applied correctly");
        } else {
            System.out.println("FAIL: replaceAll did not apply correctly");
        }
    }
}
