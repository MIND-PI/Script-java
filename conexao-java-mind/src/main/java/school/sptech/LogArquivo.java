package school.sptech;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Busca o caminho do arquivo do Log
// O Logback informa

public class LogArquivo {
    public static String getCaminho(){
//        LoggerContext armazena os logs e destinos de cada um
        LoggerContext contexto = (LoggerContext) LoggerFactory.getILoggerFactory();

        // Busca no <root> do XML o destino chamado ARQUIVO
        FileAppender<ILoggingEvent> appender = (FileAppender<ILoggingEvent>)
                contexto.getLogger(Logger.ROOT_LOGGER_NAME).getAppender("ARQUIVO");


        if(appender == null){
            return null;
        }

        // Retorna o valor do campo file
        return appender.getFile();
    }
}
