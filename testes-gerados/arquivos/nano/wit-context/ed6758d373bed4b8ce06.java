/* @test    @summary Test getStaticFinal method for null type argument    @run main ConstantBootstrapsWitupTest */

import java.lang.invoke.MethodHandles;
import java.lang.invoke.ConstantBootstraps;

public class ConstantBootstrapsWitupTest {

    public static void main(String[] args) {
        testGetStaticFinalNullType();
    }

    public static void testGetStaticFinalNullType() {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            String name = "someName";
            Class<?> type = null;
            ConstantBootstraps.getStaticFinal(lookup, name, type);
            System.out.println("FAIL: Expected NullPointerException was not thrown.");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected.");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
