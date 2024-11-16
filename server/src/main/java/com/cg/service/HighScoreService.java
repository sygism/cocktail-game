package com.cg.service;

import lombok.NoArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Properties;
import org.slf4j.Logger;

@Service
@NoArgsConstructor
public class HighScoreService {

    private final String propertiesFilePath = "src/main/resources/high_score.properties";
    private final String highScorePropertyName = "env.variables.high_score";
    private static final Logger logger = LoggerFactory.getLogger(HighScoreService.class);

    public Integer getHighScore() {
        try (InputStream input = new FileInputStream(propertiesFilePath)) {
            Properties prop = new Properties();
            prop.load(input);
            return Integer.parseInt(prop.getProperty(highScorePropertyName));
        } catch (IOException ex) {
            logger.error("Error reading properties file: {}", ex.getMessage());
            return 0;
        }
    }

    public void updateHighScore(Integer newValue) {
        try (OutputStream output = new FileOutputStream(propertiesFilePath)) {
            Properties prop = new Properties();
            prop.load(new FileInputStream(propertiesFilePath));
            prop.setProperty(highScorePropertyName, newValue.toString());

            prop.store(output, null);
        } catch (IOException ex) {
            logger.error("Error writing to properties file: {}", ex.getMessage());
        }
    }
}