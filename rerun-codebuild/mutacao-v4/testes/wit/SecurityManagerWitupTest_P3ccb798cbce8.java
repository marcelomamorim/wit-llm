/* @test
   @summary Testa o método checkLink do SecurityManager para entrada nula.
   @run main SecurityManagerWitupTest_P3ccb798cbce8
 */
import java.lang.SecurityManager;
public class SecurityManagerWitupTest_P3ccb798cbce8 {
    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkLink(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
            if (!e.getMessage().contains("library can't be null")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
