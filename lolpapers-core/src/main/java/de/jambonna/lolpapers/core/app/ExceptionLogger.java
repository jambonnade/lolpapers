package de.jambonna.lolpapers.core.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logs the App exceptions
 */
public class ExceptionLogger {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionLogger.class);
    
    
    private final Path logPath;

    public ExceptionLogger(VarFiles varFiles) throws AppException {
        try {
            logPath = varFiles.getVarPath().resolve("exceptions");
            varFiles.prepareDirectories(logPath);
        } catch (Exception e) {
            throw new AppException("Unable to init exception log directory", e);
        }
    }

    public UUID log(Throwable exception) {
        UUID uid = UUID.randomUUID();
        logger.error("Logging exception {} :", uid, exception);
        Path p = logPath.resolve(uid + ".log");
        try {
            PrintWriter writer = new PrintWriter(p.toFile(), "UTF-8");
            exception.printStackTrace(writer);
            writer.close();
        } catch (IOException ex) {
            logger.error("Can't log exception {} to {}", uid, p);
        }
        return uid;
    }
}
