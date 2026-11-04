/*
 * @test
 * @summary Testa o método clear() da classe FixedList para verificar comportamento de exceção.
 * @run main FixedListWitupTest
 */

import com.sun.java.util.jar.pack.FixedList;

public class FixedListWitupTest {

    public static void main(String[] args) {
        testClearThrowsUnsupportedOperationException();
    }

    public static void testClearThrowsUnsupportedOperationException() {
        FixedList<Object> list = new FixedList<Object>(5);
        try {
            list.clear();
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: Caught expected UnsupportedOperationException.");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }
}
