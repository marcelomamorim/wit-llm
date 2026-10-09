/* @test    @summary Test AbstractMap.put method throws UnsupportedOperationException in default implementation    @run main AbstractMapWitupTest_Pe2b17498300c */

import java.util.AbstractMap;

public class AbstractMapWitupTest_Pe2b17498300c {
    public static void main(String[] args) {
        AbstractMap<String, String> map = new AbstractMap<String, String>() {
            @Override
            public java.util.Set<java.util.Map.Entry<String, String>> entrySet() {
                return null;
            }
        };
        try {
            map.put("key", "value");
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception: " + t);
        }
    }
}
