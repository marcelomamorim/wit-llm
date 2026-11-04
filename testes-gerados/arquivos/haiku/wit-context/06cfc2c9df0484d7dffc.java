/*
 * @test
 * @summary WITUP-derived tests for ConstantBootstraps.nullConstant
 * @run main ConstantBootstrapsWitupTest
 */
import java.lang.invoke.ConstantBootstraps;
import java.lang.invoke.MethodHandles;

public class ConstantBootstrapsWitupTest {
    public static void main(String[] args) throws Exception {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        try {
            ConstantBootstraps.nullConstant(lookup, null, null);
            throw new RuntimeException("expected NullPointerException for null type");
        } catch (NullPointerException expected) {
        }

        try {
            ConstantBootstraps.nullConstant(lookup, null, int.class);
            throw new RuntimeException("expected IllegalArgumentException for primitive type");
        } catch (IllegalArgumentException expected) {
        }

        try {
            ConstantBootstraps.nullConstant(lookup, null, void.class);
            throw new RuntimeException("expected IllegalArgumentException for void type");
        } catch (IllegalArgumentException expected) {
        }

        Object s = ConstantBootstraps.nullConstant(lookup, null, String.class);
        if (s != null) {
            throw new RuntimeException("expected null for reference type String");
        }

        Object o = ConstantBootstraps.nullConstant(lookup, null, Object.class);
        if (o != null) {
            throw new RuntimeException("expected null for reference type Object");
        }

        Object arr = ConstantBootstraps.nullConstant(lookup, null, int[].class);
        if (arr != null) {
            throw new RuntimeException("expected null for array type");
        }
    }
}
