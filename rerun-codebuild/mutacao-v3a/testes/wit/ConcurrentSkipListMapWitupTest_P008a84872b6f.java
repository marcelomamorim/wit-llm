/* @test    @summary Test for ConcurrentSkipListMap.remove(Object, Object) with null key to trigger NullPointerException    @run main ConcurrentSkipListMapWitupTest_P008a84872b6f */

import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentSkipListMapWitupTest_P008a84872b6f {

    public static void main(String[] args) {
        testRemoveWithNullKey();
    }

    public static void testRemoveWithNullKey() {
        ConcurrentSkipListMap<Object, Object> map = new ConcurrentSkipListMap<>();
        boolean threwNullPointer = false;
        try {
            map.remove(null, "value");
        } catch (NullPointerException expected) {
            threwNullPointer = true;
        }
        if (!threwNullPointer) {
            throw new AssertionError("Expected NullPointerException when removing with null key.");
        }
    }
}
