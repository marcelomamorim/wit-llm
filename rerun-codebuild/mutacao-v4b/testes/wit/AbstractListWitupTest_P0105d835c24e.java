/* @test
   @summary Testa o método add(int, E) de AbstractList que lança UnsupportedOperationException.
   @run main AbstractListWitupTest_P0105d835c24e
 */
import java.util.AbstractList;
public class AbstractListWitupTest_P0105d835c24e {
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
            list.add(0, new Object());
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
