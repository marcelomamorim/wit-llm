/*
 * @test
 * @summary Checks public argument validation of Module.addOpens for null package and null target module
 * @run main ModuleWitupTest
 */

public class ModuleWitupTest {

    static void expectThrows(Class<? extends Throwable> type, Runnable r, String msg) {
        try {
            r.run();
        } catch (Throwable t) {
            if (type.isInstance(t)) {
                return;
            }
            throw new RuntimeException(msg + ": unexpected " + t, t);
        }
        throw new RuntimeException(msg + ": no exception thrown");
    }

    public static void main(String[] args) {
        final Module unnamed = ModuleWitupTest.class.getModule();
        final Module other = String.class.getModule();

        expectThrows(IllegalArgumentException.class,
                () -> unnamed.addOpens(null, other),
                "null package");

        expectThrows(NullPointerException.class,
                () -> unnamed.addOpens("java.lang", null),
                "null other module");

        expectThrows(NullPointerException.class,
                () -> unnamed.addOpens("java.lang", null),
                "null other module repeated");

        if (!unnamed.isNamed()) {
            Module r = unnamed.addOpens("java.lang", other);
            if (r != unnamed) {
                throw new RuntimeException("unnamed module addOpens must return this");
            }
        }
    }
}
