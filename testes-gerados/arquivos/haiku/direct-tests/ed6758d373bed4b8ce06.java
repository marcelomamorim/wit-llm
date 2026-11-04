/*
 * @test
 * @summary Tests public behavior of ConstantBootstraps.getStaticFinal(Lookup, String, Class) for reference and primitive types and null type
 * @run main ConstantBootstrapsWitupTest
 */

import java.lang.invoke.ConstantBootstraps;
import java.lang.invoke.MethodHandles;

public class ConstantBootstrapsWitupTest {
    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        Object truth = ConstantBootstraps.getStaticFinal(lookup, "TRUE", Boolean.class);
        check(truth == Boolean.TRUE, "Boolean.TRUE expected for reference type");

        Object max = ConstantBootstraps.getStaticFinal(lookup, "MAX_VALUE", int.class);
        check(max instanceof Integer, "primitive constant must be returned boxed");
        check(((Integer) max).intValue() == Integer.MAX_VALUE, "Integer.MAX_VALUE expected for int.class");

        boolean npe = false;
        try {
            ConstantBootstraps.getStaticFinal(lookup, "TRUE", (Class<?>) null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "NullPointerException expected when type is null");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
