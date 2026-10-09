/* @test
   @summary Verifica comportamento do método submit com entrada nula.
   @run main ForkJoinPoolWitupTest_Pc440083ff3f0
 */
import java.util.concurrent.ForkJoinPool;
public class ForkJoinPoolWitupTest_Pc440083ff3f0 {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        try {
            pool.submit((Runnable) null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
