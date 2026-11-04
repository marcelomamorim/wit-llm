/*
 * @test
 * @summary Test for java.nio.charset.Charset.remove()
 * @run main CharsetWitupTest
 */

import java.nio.charset.Charset;

public class CharsetWitupTest {

    public static void main(String[] args) {
        testRemoveMethod();
    }

    public static void testRemoveMethod() {
        Charset charset = Charset.forName("UTF-8");
        try {
            charset.remove();
            // Se nenhuma exceção for lançada, o teste passa, pois o método deve lançar UnsupportedOperationException
            System.out.println("remove() did not throw as expected");
        } catch (UnsupportedOperationException e) {
            // Esperado
            System.out.println("remove() threw UnsupportedOperationException as expected");
        } catch (Throwable t) {
            System.err.println("Unexpected exception: " + t);
            t.printStackTrace();
        }
    }
}
