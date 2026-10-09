/* @test    @summary Test for HashMap.replaceAll handling null function    @run main HashMapWitupTest_P4637f9ea2617 */

import java.util.HashMap;
import java.util.function.BiFunction;

public class HashMapWitupTest_P4637f9ea2617 {
    public static void main(String[] args) {
        testReplaceAllWithNullFunction();
        testReplaceAllWithValidFunction();
    }

    static void testReplaceAllWithNullFunction() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        try {
            map.replaceAll(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testReplaceAllWithValidFunction() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        BiFunction<String, Integer, Integer> func = (k, v) -> v + 10;
        try {
            map.replaceAll(func);
            if (map.get("a") == 11 && map.get("b") == 12) {
                System.out.println("PASS: replaceAll applied correctly");
            } else {
                System.out.println("FAIL: replaceAll did not update values as expected");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
