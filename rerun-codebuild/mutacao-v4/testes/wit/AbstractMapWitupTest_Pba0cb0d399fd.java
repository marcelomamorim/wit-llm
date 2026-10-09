/* @test
   @summary Testa o método put de AbstractMap para comportamento de exceção UnsupportedOperationException.
   @run main AbstractMapWitupTest_Pba0cb0d399fd
 */
import java.util.AbstractMap;
public class AbstractMapWitupTest_Pba0cb0d399fd {
    public static void main(String[] args) {
        try {
            AbstractMap<Object, Object> map = new AbstractMap<Object, Object>() {
                @Override
                public java.util.Set<java.util.Map.Entry<Object, Object>> entrySet() {
                    return null;
                }
            };
            map.put("key", "value");
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
