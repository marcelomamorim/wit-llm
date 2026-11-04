/*
 * @test
 * @summary Verifies NullPointerException behavior of Class.forName(Module, String) for null arguments
 * @run main ClassWitupTest
 */

public class ClassWitupTest {

    interface Action {
        void run() throws Exception;
    }

    public static void main(String[] args) throws Exception {
        Module m = ClassWitupTest.class.getModule();

        expectNPE(() -> Class.forName(null, "java.lang.String"));
        expectNPE(() -> Class.forName(m, null));
        expectNPE(() -> Class.forName(null, null));
    }

    static void expectNPE(Action action) {
        try {
            action.run();
        } catch (NullPointerException e) {
            return;
        } catch (Throwable t) {
            throw new RuntimeException("unexpected exception: " + t, t);
        }
        throw new RuntimeException("expected NullPointerException was not thrown");
    }
}
