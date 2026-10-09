/* @test
   @summary Testa o método setVersion de HttpCookie para comportamentos válidos e inválidos.
   @run main HttpCookieWitupTest_P78f026e0fcfe
 */
import java.net.HttpCookie;
public class HttpCookieWitupTest_P78f026e0fcfe {
    public static void main(String[] args) {
        testSetVersionValid();
        testSetVersionInvalid();
    }

    private static void testSetVersionValid() {
        HttpCookie cookie = new HttpCookie("name", "value");
        try {
            cookie.setVersion(0);
            System.out.println("setVersion(0) passed");
        } catch (Exception e) {
            throw new AssertionError("setVersion(0) threw an exception", e);
        }
        try {
            cookie.setVersion(1);
            System.out.println("setVersion(1) passed");
        } catch (Exception e) {
            throw new AssertionError("setVersion(1) threw an exception", e);
        }
    }

    private static void testSetVersionInvalid() {
        HttpCookie cookie = new HttpCookie("name", "value");
        boolean exceptionThrown = false;
        try {
            cookie.setVersion(2);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for setVersion(2)");
        }
        exceptionThrown = false;
        try {
            cookie.setVersion(-1);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for setVersion(-1)");
        }
        System.out.println("setVersion(invalid) tests passed");
    }
}
