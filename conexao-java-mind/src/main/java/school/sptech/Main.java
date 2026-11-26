package school.sptech;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.load();

        ProcessadorCLP processador = new ProcessadorCLP(dotenv);

        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate(
                processador::executar,
                0,
                15,
                TimeUnit.MINUTES
        );

        Runtime.getRuntime().addShutdownHook(
                new Thread(scheduler::shutdown)
        );
    }
}