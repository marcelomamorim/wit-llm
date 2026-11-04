/* @test    @summary Test for Timer constructor with name and daemon flag    @run main TimerWitupTest */

import java.util.Timer;

public class TimerWitupTest {

    public static void main(String[] args) {
        testTimerConstructorWithNameAndDaemon();
    }

    public static void testTimerConstructorWithNameAndDaemon() {
        String name = "TestTimer";
        boolean isDaemon = true;
        Timer timer = new Timer(name, isDaemon);
        // Verifica se o nome do thread foi definido corretamente
        String threadName = timer.thread.getName();
        if (!threadName.equals(name)) {
            throw new AssertionError("Expected thread name: " + name + ", but was: " + threadName);
        }
        // Verifica se o thread está configurado como daemon
        boolean daemonFlag = timer.thread.isDaemon();
        if (daemonFlag != isDaemon) {
            throw new AssertionError("Expected daemon: " + isDaemon + ", but was: " + daemonFlag);
        }
        // Verifica se o thread foi iniciado
        if (!timer.thread.isAlive()) {
            throw new AssertionError("Thread should be alive after constructor call.");
        }
    }
}
