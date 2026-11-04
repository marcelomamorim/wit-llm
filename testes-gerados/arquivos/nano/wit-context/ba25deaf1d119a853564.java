/* @test    @summary Test for jdk.internal.module.ModulePatcher.getName() method    @run main ModulePatcherWitupTest */

import java.lang.module.ModuleReference;
import java.util.Collections;

public class ModulePatcherWitupTest {

    public static void main(String[] args) {
        testGetNameReturnsExpected();
    }

    public static void testGetNameReturnsExpected() {
        // Cria um mock ou stub de ModuleReference com método descriptor() que retorna um ModuleDescriptor com nome
        ModuleReference mockRef = new ModuleReference() {
            @Override
            public java.lang.module.ModuleDescriptor descriptor() {
                return java.lang.module.ModuleDescriptor.newModule("test.module");
            }
        };
        jdk.internal.module.ModulePatcher patcher = new jdk.internal.module.ModulePatcher(Collections.singletonMap("test.module", Collections.emptyList()));
        String name = patcher.getName();
        if (name == null || !name.equals("test.module")) {
            throw new AssertionError("Expected module name 'test.module', but got: " + name);
        }
    }
}
