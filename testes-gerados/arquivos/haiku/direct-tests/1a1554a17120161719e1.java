/*
 * @test
 * @summary Verify ModuleLayer.findLoader returns the loader of a known module and throws IllegalArgumentException for unknown modules
 * @run main ModuleLayerWitupTest
 */

public class ModuleLayerWitupTest {

    public static void main(String[] args) {
        ClassLoader bootLoader = ModuleLayer.boot().findLoader("java.base");
        if (bootLoader != null) {
            throw new RuntimeException("expected null loader for java.base, got " + bootLoader);
        }

        try {
            ModuleLayer.boot().findLoader("no.such.module.witup");
            throw new RuntimeException("expected IllegalArgumentException for unknown module");
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().contains("not known to this layer")) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }

        try {
            ModuleLayer.empty().findLoader("java.base");
            throw new RuntimeException("expected IllegalArgumentException for empty layer");
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().contains("not known to this layer")) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
    }
}
