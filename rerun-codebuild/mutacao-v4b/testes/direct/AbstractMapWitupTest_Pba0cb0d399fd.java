/* @test
   @summary Verifies that calling put on AbstractMap throws UnsupportedOperationException.
   @run main AbstractMapWitupTest_Pba0cb0d399fd
 */
import java.util.AbstractMap;
public class AbstractMapWitupTest_Pba0cb0d399fd {
    public static void main(String[] args) {
        AbstractMap<String, String> map = new AbstractMap<String, String>() {
            @Override
            public java.util.Set<java.util.Map.Entry<String, String>> entrySet() {
                return null;
            }
        };
        try {
            map.put("key", "value");
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
