/* @test    @summary Test for AbstractMap.put to verify UnsupportedOperationException is thrown.    @run main AbstractMapWitupTest_Pa44320f56563 */

import java.util.AbstractMap;

public class AbstractMapWitupTest_Pa44320f56563 {

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
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: UnsupportedOperationException thrown as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }
}
