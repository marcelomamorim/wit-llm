/*
 * @test
 * @summary Tests for java.lang.Module.isExported method.
 * @run main ModuleWitupTest
 */

import java.lang.Module;
import java.util.Objects;

public class ModuleWitupTest {

    public static void main(String[] args) {
        testNullPN();
        testNullOther();
    }

    public static void testNullPN() {
        Module module = createDummyModule();
        try {
            module.isExported(null, createDummyModule());
            System.out.println("testNullPN: Failed - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testNullPN: Passed");
        } catch (Exception e) {
            System.out.println("testNullPN: Failed - Unexpected exception: " + e);
        }
    }

    public static void testNullOther() {
        Module module = createDummyModule();
        try {
            module.isExported("java.lang", null);
            System.out.println("testNullOther: Failed - Expected NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testNullOther: Passed");
        } catch (Exception e) {
            System.out.println("testNullOther: Failed - Unexpected exception: " + e);
        }
    }

    private static Module createDummyModule() {
        // Cria um módulo dummy usando uma implementação anônima para fins de teste
        return new Module() {
            @Override
            public String getName() { return "dummy"; }
            @Override
            public boolean isExported(String pn, Module other) { return false; }
            @Override
            public boolean isOpen(String pn) { return false; }
            @Override
            public boolean isOpen(String pn, Module other) { return false; }
            @Override
            public boolean isExported(String pn) { return false; }
            @Override
            public boolean isNamed() { return false; }
            @Override
            public Set<String> getPackages() { return Collections.emptySet(); }
            @Override
            public Set<Module> getDirectExports() { return Collections.emptySet(); }
            @Override
            public Set<Module> getDirectImports() { return Collections.emptySet(); }
            @Override
            public Optional<Module> getLayer() { return Optional.empty(); }
            @Override
            public boolean isOpen() { return false; }
            @Override
            public boolean isAutomatic() { return false; }
            @Override
            public boolean isSystem() { return false; }
            @Override
            public boolean isAccessible() { return false; }
            @Override
            public boolean isExported(String pn, Module other) { return false; }
            @Override
            public boolean isOpen(String pn, Module other) { return false; }
            @Override
            public String toString() { return "DummyModule"; }
        };
    }
}
