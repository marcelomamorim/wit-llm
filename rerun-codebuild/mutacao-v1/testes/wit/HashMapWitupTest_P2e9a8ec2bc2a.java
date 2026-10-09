/* @test    @summary Test HashMap.replaceAll with null function to trigger NullPointerException    @run main HashMapWitupTest_P2e9a8ec2bc2a */

import java.util.HashMap;
import java.util.function.BiFunction;

public class HashMapWitupTest_P2e9a8ec2bc2a {

    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        
        try {
            map.replaceAll(null);
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
    }
}
