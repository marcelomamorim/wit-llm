/*
 * @test
 * @summary Verify that Normalizer2.getNFDInstance() returns a working NFD normalizer
 * @modules java.base/sun.text.normalizer
 * @run main/othervm --add-opens java.base/sun.text.normalizer=ALL-UNNAMED Normalizer2WitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Normalizer2WitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("sun.text.normalizer.Normalizer2");
        Method getNFD = cls.getMethod("getNFDInstance");
        getNFD.setAccessible(true);
        Object nfd;
        try {
            nfd = getNFD.invoke(null);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("getNFDInstance threw", e.getCause());
        }
        if (nfd == null) {
            throw new RuntimeException("getNFDInstance returned null");
        }
        if (!cls.isInstance(nfd)) {
            throw new RuntimeException("unexpected instance type");
        }

        Method normalize = cls.getMethod("normalize", CharSequence.class);
        normalize.setAccessible(true);

        // Precomposed A-grave must be decomposed to A + combining grave accent
        check(normalize, nfd, "\u00C0", "A\u0300");
        // Precomposed e-acute must be decomposed to e + combining acute accent
        check(normalize, nfd, "\u00E9", "e\u0301");
        // Plain ASCII is unchanged
        check(normalize, nfd, "abc", "abc");
        // Compatibility characters are not changed by canonical decomposition
        check(normalize, nfd, "\uFB01", "\uFB01");
        // Already decomposed input is unchanged
        check(normalize, nfd, "e\u0301", "e\u0301");
        // Empty input yields empty output
        check(normalize, nfd, "", "");
    }

    static void check(Method m, Object target, String in, String expected) throws Exception {
        Object result;
        try {
            result = m.invoke(target, in);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("normalize threw for input", e.getCause());
        }
        if (!expected.equals(result)) {
            throw new RuntimeException("expected " + escape(expected)
                    + " but got " + escape(String.valueOf(result)));
        }
    }

    static String escape(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            sb.append(String.format("\\u%04X", (int) s.charAt(i)));
        }
        return sb.toString();
    }
}
