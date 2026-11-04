/*
 * @test
 * @summary Verifica que Norm2AllModes.getNFCInstance() retorna uma instancia nao nula e estavel (singleton) sem lancar excecao no estado normal.
 * @modules java.base/sun.text.normalizer
 * @run main/othervm --add-opens java.base/sun.text.normalizer=ALL-UNNAMED Norm2AllModesWitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Norm2AllModesWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("sun.text.normalizer.Norm2AllModes");
        Method m = c.getMethod("getNFCInstance");
        m.setAccessible(true);

        Object first;
        Object second;
        try {
            first = m.invoke(null);
            second = m.invoke(null);
        } catch (InvocationTargetException e) {
            throw new AssertionError("getNFCInstance() lancou excecao inesperada", e.getCause());
        }

        if (first == null) {
            throw new AssertionError("getNFCInstance() retornou null");
        }
        if (!c.isInstance(first)) {
            throw new AssertionError("Tipo de retorno inesperado: " + first.getClass());
        }
        if (first != second) {
            throw new AssertionError("getNFCInstance() nao retornou a mesma instancia em chamadas sucessivas");
        }
    }
}
