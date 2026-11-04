/*
 * @test
 * @summary Public-API smoke test for CleanerImpl; get() is not reachable from the unnamed package
 * @run main CleanerImplWitupTest
 */

import java.lang.ref.Cleaner;
import jdk.internal.ref.CleanerImpl;

public class CleanerImplWitupTest {

    public static void main(String[] args) throws Exception {
        CleanerImpl impl = new CleanerImpl();
        if (!(impl instanceof Runnable)) {
            throw new AssertionError("CleanerImpl should implement Runnable");
        }

        Cleaner cleaner = Cleaner.create();
        int[] counter = new int[1];
        Cleaner.Cleanable c = cleaner.register(new Object(), () -> counter[0]++);
        c.clean();
        if (counter[0] != 1) {
            throw new AssertionError("cleaning action should run exactly once, ran " + counter[0]);
        }
        c.clean();
        if (counter[0] != 1) {
            throw new AssertionError("cleaning action should not run twice, ran " + counter[0]);
        }
    }
}
