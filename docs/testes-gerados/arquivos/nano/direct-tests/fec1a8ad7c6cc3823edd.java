/* @test    @summary Test putIfAbsent method in Collections class    @run main CollectionsWitupTest */

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testPutIfAbsentThrows();
    }

    public static void testPutIfAbsentThrows() {
        Map<String, String> map = new HashMap<>();
        try {
            String result = Collections.putIfAbsent(map, "key", "value");
            // Since putIfAbsent throws UnsupportedOperationException, this line should not be reached.
            System.out.println("Unexpected success: " + result);
        } catch (UnsupportedOperationException e) {
            System.out.println("Caught expected UnsupportedOperationException");
        }
    }
}
