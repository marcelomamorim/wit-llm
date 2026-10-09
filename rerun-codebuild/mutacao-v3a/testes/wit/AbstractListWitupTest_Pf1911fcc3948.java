/* @test    @summary Test remove method of AbstractList for UnsupportedOperationException    @run main AbstractListWitupTest_Pf1911fcc3948 */

import java.util.AbstractList;

public class AbstractListWitupTest_Pf1911fcc3948 {
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
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: Caught expected UnsupportedOperationException");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }
}
