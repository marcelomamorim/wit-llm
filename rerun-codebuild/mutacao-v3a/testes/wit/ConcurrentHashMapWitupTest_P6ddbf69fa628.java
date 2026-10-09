/* @test    @summary Test the replaceAll method with null function to trigger NullPointerException    @run main ConcurrentHashMapWitupTest_P6ddbf69fa628 */

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class ConcurrentHashMapWitupTest_P6ddbf69fa628 {

    public static void main(String[] args) {
        testReplaceAllWithNullFunction();
    }

    static void testReplaceAllWithNullFunction() {
        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();
        map.put(1, "one");
        try {
            map.replaceAll(null);
            System.err.println("Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException expected) {
            System.out.println("NullPointerException caught as expected.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
