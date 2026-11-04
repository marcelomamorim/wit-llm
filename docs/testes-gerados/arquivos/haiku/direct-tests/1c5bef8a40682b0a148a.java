/*
 * @test
 * @summary Verifies the public signature of sun.text.normalizer.Norm2AllModes.normalize(CharSequence, Appendable) via reflection
 * @modules java.base/sun.text.normalizer
 * @run main Norm2AllModesWitupTest
 */

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Norm2AllModesWitupTest {
    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("sun.text.normalizer.Norm2AllModes");

        if (!Modifier.isFinal(cls.getModifiers())) {
            throw new RuntimeException("Norm2AllModes should be final");
        }
        if (Modifier.isPublic(cls.getModifiers())) {
            throw new RuntimeException("Norm2AllModes should not be public");
        }

        Method m = cls.getDeclaredMethod("normalize", CharSequence.class, Appendable.class);

        if (!Modifier.isPublic(m.getModifiers())) {
            throw new RuntimeException("normalize(CharSequence, Appendable) should be public");
        }
        if (Modifier.isStatic(m.getModifiers())) {
            throw new RuntimeException("normalize(CharSequence, Appendable) should not be static");
        }
        if (m.getReturnType() != Appendable.class) {
            throw new RuntimeException("Unexpected return type: " + m.getReturnType());
        }
    }
}
