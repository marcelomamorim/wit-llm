/*
 * @test
 * @summary Checks the public final visibility of SystemModuleFinders without invoking the nested find method
 * @modules java.base/jdk.internal.module
 * @run main SystemModuleFindersWitupTest
 */
import java.lang.reflect.Modifier;

public class SystemModuleFindersWitupTest {
    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("jdk.internal.module.SystemModuleFinders");
        int m = c.getModifiers();
        if (!Modifier.isPublic(m) || !Modifier.isFinal(m)) {
            throw new RuntimeException("unexpected modifiers: " + Modifier.toString(m));
        }
    }
}
