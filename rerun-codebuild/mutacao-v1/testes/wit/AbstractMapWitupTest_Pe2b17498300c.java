/* @test    @summary Test for AbstractMap.put method to verify UnsupportedOperationException is thrown.    @run main AbstractMapWitupTest_Pe2b17498300c */

import java.util.AbstractMap;

public class AbstractMapWitupTest_Pe2b17498300c {
    public static void main(String[] args) {
        testPutThrowsException();
    }

    public static void testPutThrowsException() {
        AbstractMap<Object, Object> map = new AbstractMap<Object, Object>() {
            @Override
            public java.util.Set<java.util.Map.Entry<Object, Object>> entrySet() {
                return null;
            }
        };
        try {
            map.put("key", "value");
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
