/* @test    @summary Teste do método remove() de ParallelListResourceBundle que lança UnsupportedOperationException    @run main ParallelListResourceBundleWitupTest */

import sun.util.resources.ParallelListResourceBundle;

public class ParallelListResourceBundleWitupTest {

    public static void main(String[] args) {
        testRemoveThrowsException();
    }

    public static void testRemoveThrowsException() {
        ParallelListResourceBundle bundle = new ParallelListResourceBundle() {
            @Override
            protected Object[][] getContents() {
                return new Object[0][0];
            }
        };
        try {
            bundle.remove();
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: UnsupportedOperationException was thrown as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception type: " + t);
        }
    }
}
