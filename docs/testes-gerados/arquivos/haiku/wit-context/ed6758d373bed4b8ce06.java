/*
 * @test
 * @summary Verifies that ConstantBootstraps.getStaticFinal throws NullPointerException when the type argument is null
 * @run main ConstantBootstrapsWitupTest
 */

public class ConstantBootstrapsWitupTest {
    public static void main(String[] args) {
        java.lang.invoke.MethodHandles.Lookup lookup = java.lang.invoke.MethodHandles.lookup();
        try {
            java.lang.invoke.ConstantBootstraps.getStaticFinal(lookup, "MAX_VALUE", null);
            throw new RuntimeException("Expected NullPointerException for null type");
        } catch (NullPointerException expected) {
        }
        try {
            java.lang.invoke.ConstantBootstraps.getStaticFinal(null, null, null);
            throw new RuntimeException("Expected NullPointerException for null type with null lookup and name");
        } catch (NullPointerException expected) {
        }
    }
}
