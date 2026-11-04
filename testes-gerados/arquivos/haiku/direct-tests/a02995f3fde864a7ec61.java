/*
 * @test
 * @summary Checks observable behavior of the public Module.addOpens path that System's addOpens lambda delegates to
 * @run main SystemWitupTest
 */

public class SystemWitupTest {

    public static void main(String[] args) throws Exception {
        Module unnamed = SystemWitupTest.class.getModule();
        Module javaBase = Object.class.getModule();

        // Unnamed module ignores addOpens and returns itself.
        Module result = unnamed.addOpens("java.lang", javaBase);
        if (result != unnamed) {
            throw new RuntimeException("addOpens on unnamed module should return this");
        }

        // Null package name must be rejected.
        boolean npe = false;
        try {
            unnamed.addOpens(null, javaBase);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("expected NullPointerException for null package name");
        }

        // Null target module must be rejected.
        npe = false;
        try {
            unnamed.addOpens("java.lang", null);
        } catch (NullPointerException e) {
            npe = true;
        }
        if (!npe) {
            throw new RuntimeException("expected NullPointerException for null target module");
        }
    }
}
