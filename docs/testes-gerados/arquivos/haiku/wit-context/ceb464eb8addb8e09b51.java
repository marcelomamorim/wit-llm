/*
 * @test
 * @summary Witup-generated checks for Module.isExported(String, Module)
 * @run main ModuleWitupTest
 */

public class ModuleWitupTest {

    interface Action {
        void run() throws Exception;
    }

    public static void main(String[] args) throws Exception {
        Module base = Object.class.getModule();
        Module unnamed = ModuleWitupTest.class.getModule();

        if (!base.isExported("java.lang", unnamed)) {
            throw new RuntimeException("java.lang should be exported from java.base");
        }
        if (base.isExported("no.such.pkg.witup", unnamed)) {
            throw new RuntimeException("nonexistent package should not be exported");
        }

        expectNpe(() -> base.isExported(null, unnamed));
        expectNpe(() -> base.isExported("java.lang", null));
        expectNpe(() -> base.isExported(null, null));
    }

    static void expectNpe(Action action) {
        try {
            action.run();
        } catch (NullPointerException e) {
            return;
        } catch (Exception e) {
            throw new RuntimeException("unexpected exception: " + e, e);
        }
        throw new RuntimeException("expected NullPointerException");
    }
}
