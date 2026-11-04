/*
 * @test
 * @summary Verifies that Module.addReads, the public API that System's addReads delegates to via implAddReads, returns the receiver module
 * @run main SystemWitupTest
 */

public class SystemWitupTest {
    public static void main(String[] args) throws Exception {
        Module javaBase = Object.class.getModule();
        Module testModule = SystemWitupTest.class.getModule();

        Module result1 = javaBase.addReads(testModule);
        if (result1 != javaBase) {
            throw new RuntimeException("addReads on java.base must return the receiver");
        }

        Module result2 = testModule.addReads(javaBase);
        if (result2 != testModule) {
            throw new RuntimeException("addReads on unnamed module must return the receiver");
        }

        Module result3 = javaBase.addReads(javaBase);
        if (result3 != javaBase) {
            throw new RuntimeException("addReads to self must return the receiver");
        }
    }
}
