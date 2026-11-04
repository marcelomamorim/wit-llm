/*
 * @test
 * @summary Test UnsupportedOperationException from sort(Comparator) method
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.Comparator;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        Collections c = new Collections() {
            @Override
            public void sort(Comparator<? super Object> comp) {
                throw new UnsupportedOperationException();
            }
        };

        boolean thrown = false;
        try {
            c.sort(null);
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Expected UnsupportedOperationException not thrown");
        }
    }
}
