/*
 * @test
 * @summary Checks SelectorImpl.selectNow(Consumer) null-action and closed-selector behavior through reflection
 * @modules java.base/sun.nio.ch
 * @run main/othervm --add-opens java.base/sun.nio.ch=ALL-UNNAMED SelectorImplWitupTest
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;
import java.util.function.Consumer;

public class SelectorImplWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> selectorImpl = Class.forName("sun.nio.ch.SelectorImpl");
        Method selectNow = selectorImpl.getMethod("selectNow", Consumer.class);
        selectNow.setAccessible(true);

        Consumer<Object> action = k -> { };

        try (Selector sel = SelectorProvider.provider().openSelector()) {
            try {
                selectNow.invoke(sel, (Object) null);
                throw new AssertionError("expected NullPointerException for null action");
            } catch (InvocationTargetException e) {
                if (!(e.getCause() instanceof NullPointerException)) {
                    throw new AssertionError("unexpected cause: " + e.getCause(), e);
                }
            }

            int n = (Integer) selectNow.invoke(sel, action);
            if (n != 0) {
                throw new AssertionError("expected 0 ready keys, got " + n);
            }
        }

        Selector closed = SelectorProvider.provider().openSelector();
        closed.close();
        try {
            selectNow.invoke(closed, action);
            throw new AssertionError("expected ClosedSelectorException on closed selector");
        } catch (InvocationTargetException e) {
            if (!(e.getCause() instanceof ClosedSelectorException)) {
                throw new AssertionError("unexpected cause: " + e.getCause(), e);
            }
        }
    }
}
