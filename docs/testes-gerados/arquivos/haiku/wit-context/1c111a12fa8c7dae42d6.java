/*
 * @test
 * @summary Smoke test for the public ModuleBootstrap.patcher() accessor
 * @modules java.base/jdk.internal.module
 * @run main ModuleBootstrapWitupTest
 */

public class ModuleBootstrapWitupTest {
    public static void main(String[] args) {
        if (jdk.internal.module.ModuleBootstrap.patcher() == null) {
            throw new RuntimeException("ModuleBootstrap.patcher() returned null");
        }
    }
}
