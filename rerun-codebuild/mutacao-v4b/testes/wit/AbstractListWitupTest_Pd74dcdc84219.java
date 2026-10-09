/* @test
   @summary Testa o método set de AbstractList para comportamento esperado.
   @run main AbstractListWitupTest_Pd74dcdc84219
 */
import java.util.AbstractList;
public class AbstractListWitupTest_Pd74dcdc84219 {
    public static void main(String[] args) {
        AbstractList<Object> list = new AbstractList<Object>() {
            @Override
            public Object get(int index) {
                return null;
            }
            @Override
            public int size() {
                return 0;
            }
        };
        try {
            list.set(0, new Object());
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
