/* @test
   @summary Verifies that calling remove(int) on AbstractList throws UnsupportedOperationException.
   @run main AbstractListWitupTest_P17e9ce54a9be
 */
import java.util.*;
public class AbstractListWitupTest_P17e9ce54a9be {
    public static void main(String[] args) {
        List<Integer> list = new AbstractList<Integer>() {
            private final List<Integer> internal = new ArrayList<>();
            @Override
            public Integer get(int index) {
                return internal.get(index);
            }
            @Override
            public int size() {
                return internal.size();
            }
        };
        list.add(1);
        list.add(2);
        list.add(3);
        try {
            list.remove(0);
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
