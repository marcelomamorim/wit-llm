/*
 * @test
 * @summary Teste do método addAll de FixedList para comportamento esperado.
 * @run main FixedListAddAllTest
 */

import java.util.Collection;
import com.sun.java.util.jar.pack.FixedList;

public class FixedListAddAllTest {

    public static void main(String[] args) {
        testAddAllThrowsException();
    }

    public static void testAddAllThrowsException() {
        FixedList<String> list = new FixedList<String>(3);
        Collection<String> dummyCollection = new java.util.ArrayList<>();
        try {
            list.addAll(0, dummyCollection);
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: UnsupportedOperationException thrown as expected.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
