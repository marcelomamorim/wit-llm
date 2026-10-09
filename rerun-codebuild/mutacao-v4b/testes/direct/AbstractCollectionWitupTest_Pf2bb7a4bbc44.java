/* @test
   @summary Verifies that calling add() on AbstractCollection throws UnsupportedOperationException.
   @run main AbstractCollectionWitupTest_Pf2bb7a4bbc44
 */
import java.util.*;
public class AbstractCollectionWitupTest_Pf2bb7a4bbc44 {
    public static void main(String[] args) {
        AbstractCollection<Object> collection = new AbstractCollection<Object>() {
            @Override
            public Iterator<Object> iterator() {
                return Collections.emptyIterator();
            }

            @Override
            public int size() {
                return 0;
            }
        };
        try {
            collection.add(new Object());
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
