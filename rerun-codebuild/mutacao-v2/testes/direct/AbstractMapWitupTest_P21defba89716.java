/*
 * @test
 * @summary Test AbstractMap.put throws UnsupportedOperationException
 * @run main AbstractMapWitupTest_P21defba89716
 */

import java.util.AbstractMap;

public class AbstractMapWitupTest_P21defba89716 {
    public static void main(String[] args) {
        AbstractMap<Object, Object> map = new AbstractMap<Object, Object>() {
            public java.util.Set<java.util.Map.Entry<Object, Object>> entrySet() {
                throw new UnsupportedOperationException();
            }
        };

        try {
            map.put("key", "value");
            throw new Error("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        }
    }
}
