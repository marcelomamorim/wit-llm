/* @test    @summary Verify that AbstractList.remove throws UnsupportedOperationException for any index    @run main AbstractListWitupTest_Pf1911fcc3948 */

import java.util.AbstractList;

public class AbstractListWitupTest_Pf1911fcc3948 {

    public static void main(String[] args) {
        testRemoveThrows();
    }

    public static void testRemoveThrows() {
        AbstractList<Object> list = new AbstractList<Object>() {
            @Override
            public Object get(int index) {
                return null;
            }

            @Override
            public int size() {
                return 1;
            }
        };
        try {
            list.remove(0);
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: remove() correctly throws UnsupportedOperationException.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
