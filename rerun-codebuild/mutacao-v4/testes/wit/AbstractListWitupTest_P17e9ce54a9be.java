/* @test
   @summary Testa o método remove de AbstractList para comportamento padrão.
   @run main AbstractListWitupTest_P17e9ce54a9be
 */
import java.util.AbstractList;
public class AbstractListWitupTest_P17e9ce54a9be {
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
            list.remove(0);
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // esperado
        }
    }
}
