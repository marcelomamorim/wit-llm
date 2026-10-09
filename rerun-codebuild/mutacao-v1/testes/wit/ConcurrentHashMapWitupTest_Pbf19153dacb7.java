/* @test    @summary Test computeIfPresent with null key and null remappingFunction to trigger NullPointerException    @run main ConcurrentHashMapWitupTest_Pbf19153dacb7 */

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapWitupTest_Pbf19153dacb7 {

    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        try {
            map.computeIfPresent(null, null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
    }
}
