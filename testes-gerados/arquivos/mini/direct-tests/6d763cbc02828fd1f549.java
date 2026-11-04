/*
 * @test
 * @summary Test java.lang.Module.addOpens method behavior
 * @run main ModuleWitupTest
 */

import java.lang.Module;
import java.lang.IllegalCallerException;
import java.util.Objects;
import jdk.internal.reflect.Reflection;

public class ModuleWitupTest {

    public static void main(String[] args) throws Exception {
        testAddOpensNullPackage();
        testAddOpensNullOtherModule();
        testAddOpensUnnamedModuleReturnsThis();
        testAddOpensNamedModuleCallerNotThisAndNotOpenThrows();
        testAddOpensNamedModuleCallerIsThisSucceeds();
    }

    private static void testAddOpensNullPackage() {
        Module m = ModuleWitupTest.class.getModule();
        try {
            m.addOpens(null, m);
            throw new RuntimeException("Expected IllegalArgumentException for null package");
        } catch (IllegalArgumentException e) {
            if (!"package is null".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
    }

    private static void testAddOpensNullOtherModule() {
        Module m = ModuleWitupTest.class.getModule();
        try {
            m.addOpens("java.lang", null);
            throw new RuntimeException("Expected NullPointerException for null other module");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testAddOpensUnnamedModuleReturnsThis() {
        // Unnamed modules have no name
        Module unnamed = new Object() {}.getClass().getModule();
        if (unnamed.isNamed()) {
            throw new RuntimeException("Test setup failure: module should be unnamed");
        }
        Module result = unnamed.addOpens("any.package", unnamed);
        if (result != unnamed) {
            throw new RuntimeException("addOpens should return this for unnamed modules");
        }
    }

    private static void testAddOpensNamedModuleCallerNotThisAndNotOpenThrows() throws Exception {
        Module named = ModuleWitupTest.class.getModule();
        if (!named.isNamed()) {
            throw new RuntimeException("Test setup failure: module should be named");
        }

        // We need to simulate a caller module different from 'named' and not open to it
        // Since getCallerModule and isOpen are private/internal, we cannot directly simulate
        // Instead, we test that calling addOpens from a different class triggers IllegalCallerException

        // We create a helper class in a different module to call addOpens
        try {
            Helper.callAddOpens(named, "java.lang", named);
            throw new RuntimeException("Expected IllegalCallerException");
        } catch (IllegalCallerException e) {
            // expected
        }
    }

    private static void testAddOpensNamedModuleCallerIsThisSucceeds() {
        Module named = ModuleWitupTest.class.getModule();
        if (!named.isNamed()) {
            throw new RuntimeException("Test setup failure: module should be named");
        }

        // Calling addOpens from the same module should succeed
        Module result = named.addOpens("java.lang", named);
        if (result != named) {
            throw new RuntimeException("addOpens should return this");
        }
    }

    static class Helper {
        static void callAddOpens(Module target, String pn, Module other) {
            target.addOpens(pn, other);
        }
    }
}
