/* @test    @summary Test for AbstractMap.put method to verify UnsupportedOperationException is thrown.    @run main AbstractMapWitupTest_P317e7bba9145 */

import java.util.AbstractMap;

public class AbstractMapWitupTest_P317e7bba9145 {

    public static void main(String[] args) {
        testPutThrowsException();
    }

    public static void testPutThrowsException() {
        AbstractMap<Object, Object> map = new AbstractMap<Object, Object>() {
            @Override
            public java.util.Set<Entry<Object, Object>> entrySet() {
                return null;
            }
        };
        try {
            map.put("key", "value");
            System.err.println("Failed: Expected UnsupportedOperationException was not thrown.");
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            // Expected exception
            System.out.println("Passed: UnsupportedOperationException was thrown as expected.");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
            System.exit(1);
        }
    }
}
