/*
 * @test
 * @summary Checks Module.addReads observable behavior: null argument, unnamed module return value, and IllegalCallerException for a named module called from another module
 * @run main ModuleWitupTest
 */

public class ModuleWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Module unnamed = ModuleWitupTest.class.getModule();
        Module javaBase = Object.class.getModule();

        check(!unnamed.isNamed(), "test class should be in an unnamed module");
        check(javaBase.isNamed(), "java.base should be a named module");

        // Unnamed module: addReads does nothing and returns this
        check(unnamed.addReads(javaBase) == unnamed, "unnamed.addReads(javaBase) should return this");
        check(unnamed.addReads(unnamed) == unnamed, "unnamed.addReads(self) should return this");

        // null argument must be rejected by Objects.requireNonNull
        boolean npeUnnamed = false;
        try {
            unnamed.addReads(null);
        } catch (NullPointerException e) {
            npeUnnamed = true;
        }
        check(npeUnnamed, "unnamed.addReads(null) should throw NullPointerException");

        boolean npeNamed = false;
        try {
            javaBase.addReads(null);
        } catch (NullPointerException e) {
            npeNamed = true;
        }
        check(npeNamed, "javaBase.addReads(null) should throw NullPointerException");

        // Named module invoked by a caller outside it must throw IllegalCallerException
        boolean icee = false;
        try {
            javaBase.addReads(unnamed);
        } catch (IllegalCallerException e) {
            icee = true;
        }
        check(icee, "javaBase.addReads(...) from another module should throw IllegalCallerException");
    }
}
