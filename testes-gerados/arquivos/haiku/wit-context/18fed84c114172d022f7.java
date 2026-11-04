/*
 * @test
 * @summary Smoke test for sun.nio.ch.Util class availability; the WIT expath for the anonymous add(E) is not reached
 * @modules java.base/sun.nio.ch
 * @run main UtilWitupTest
 */

public class UtilWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("sun.nio.ch.Util");
        if (!"sun.nio.ch.Util".equals(c.getName())) {
            throw new AssertionError("unexpected class name: " + c.getName());
        }
    }
}
