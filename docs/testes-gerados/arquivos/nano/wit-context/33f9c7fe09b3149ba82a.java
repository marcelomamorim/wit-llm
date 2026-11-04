/*
 * @test
 * @summary Teste do método add(int, E) de FixedList que lança UnsupportedOperationException
 * @run main FixedListWitupTest
 */

import com.sun.java.util.jar.pack.FixedList;

public class FixedListWitupTest {

    public static void main(String[] args) {
        testAddThrowsException();
    }

    public static void testAddThrowsException() {
        FixedList<Object> list = new FixedList<Object>(3);
        try {
            list.add(0, new Object());
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: UnsupportedOperationException was correctly thrown.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
