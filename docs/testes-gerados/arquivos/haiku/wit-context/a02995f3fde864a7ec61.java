/*
 * @test
 * @summary Checks public Module.addOpens null-argument and return-value behavior on the unnamed module, as an observable proxy for the System anonymous JavaLangAccess.addOpens path
 * @run main SystemWitupTest
 */

public class SystemWitupTest {
    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Module unnamed = SystemWitupTest.class.getModule();
        Module other = String.class.getModule();

        try {
            unnamed.addOpens(null, other);
            check(false, "expected NullPointerException for null pn");
        } catch (NullPointerException expected) {
        }

        try {
            unnamed.addOpens("java.lang", null);
            check(false, "expected NullPointerException for null other");
        } catch (NullPointerException expected) {
        }

        Module result = unnamed.addOpens("java.lang", other);
        check(result == unnamed, "addOpens on unnamed module should return this");
    }
}
