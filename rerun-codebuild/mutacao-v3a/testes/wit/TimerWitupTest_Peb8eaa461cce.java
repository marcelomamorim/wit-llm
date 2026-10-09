/* @test    @summary Test scheduling with invalid delay and period values to trigger IllegalArgumentException    @run main TimerWitupTest_Peb8eaa461cce */

import java.util.Timer;
import java.util.TimerTask;

public class TimerWitupTest_Peb8eaa461cce {
    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // vazio
            }
        };
        // Teste com delay negativo
        try {
            timer.schedule(task, -1, 10);
            System.out.println("Falha: exceção não lançada para delay negativo");
        } catch (IllegalArgumentException e) {
            System.out.println("Sucesso: exceção lançada para delay negativo");
        }
        // Teste com period zero
        try {
            timer.schedule(task, 0, 0);
            System.out.println("Falha: exceção não lançada para period zero");
        } catch (IllegalArgumentException e) {
            System.out.println("Sucesso: exceção lançada para period zero");
        }
        // Teste com delay positivo e period negativo
        try {
            timer.schedule(task, 100, -5);
            System.out.println("Falha: exceção não lançada para period negativo");
        } catch (IllegalArgumentException e) {
            System.out.println("Sucesso: exceção lançada para period negativo");
        }
    }
}
