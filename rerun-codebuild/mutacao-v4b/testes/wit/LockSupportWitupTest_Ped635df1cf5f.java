/* @test
   @summary Testa o método getBlocker de LockSupport para entrada nula.
   @run main LockSupportWitupTest_Ped635df1cf5f
 */
import java.util.concurrent.locks.LockSupport;
public class LockSupportWitupTest_Ped635df1cf5f {
    public static void main(String[] args) {
        try {
            LockSupport.getBlocker(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // esperado
        }
    }
}
