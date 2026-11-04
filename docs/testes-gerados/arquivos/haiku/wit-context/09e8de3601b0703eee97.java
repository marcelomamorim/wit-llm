/*
 * @test
 * @summary Witup-style checks for Module.addReads null handling, unnamed-module return value and caller check
 * @run main ModuleWitupTest
 */

public class ModuleWitupTest {
    public static void main(String[] args) throws Exception {
        Module unnamed = ModuleWitupTest.class.getModule();
        if (unnamed.isNamed()) {
            throw new RuntimeException("expected unnamed module for test class");
        }
        Module base = Object.class.getModule();

        try {
            base.addReads(null);
            throw new RuntimeException("NullPointerException expected for named module");
        } catch (NullPointerException e) {
        }

        try {
            unnamed.addReads(null);
            throw new RuntimeException("NullPointerException expected for unnamed module");
        } catch (NullPointerException e) {
        }

        if (unnamed.addReads(base) != unnamed) {
            throw new RuntimeException("unnamed module addReads must return this");
        }

        try {
            base.addReads(unnamed);
            throw new RuntimeException("IllegalCallerException expected when caller is not the module");
        } catch (IllegalCallerException e) {
        }
    }
}
