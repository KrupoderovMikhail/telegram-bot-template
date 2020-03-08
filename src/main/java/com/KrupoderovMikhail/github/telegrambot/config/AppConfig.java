package com.KrupoderovMikhail.github.telegrambot.config;

import com.KrupoderovMikhail.github.telegrambot.logger.LoggingAnnotationProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Krupoderov Mikhail
 * @version 1.0
 */
@Configuration
public class AppConfig {

    @Bean
    public LoggingAnnotationProcessor loggingAnnotationProcessor() {
        return new LoggingAnnotationProcessor();
    }
}
