/*
 * @test
 * @summary Test AbstractMap.put(K,V) throws UnsupportedOperationException
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

        boolean thrown = false;
        try {
            map.put("key", "value");
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException not thrown by put");
        }
    }
}
