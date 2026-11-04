/*
 * @test    @summary Test addReads method of java.lang.System
 * @run main SystemWitupTest
 */
import java.lang.Module;
import java.lang.System;

public class SystemWitupTest {
    public static void main(String[] args) {
        Module m1 = new DummyModule("Module1");
        Module m2 = new DummyModule("Module2");
        try {
            System system = new System();
            system.addReads(m1, m2);
            System.out.println("addReads executed successfully");
        } catch (Throwable t) {
            t.printStackTrace();
            System.err.println("addReads threw an exception");
        }
    }

    static class DummyModule extends Module {
        protected DummyModule(String name) {
            super(new ModuleLayer() {}, name, 0, null, null);
        }
        @Override
        public void implAddReads(Module target) {
            System.out.println("implAddReads called with " + target.getName());
        }
        @Override
        public String getName() {
            return super.getName();
        }
    }
}
