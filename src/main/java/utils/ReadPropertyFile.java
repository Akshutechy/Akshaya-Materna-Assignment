package utils;

import constants.FrameworkConstants;
import enums.ConfigProperties;
import exception.InvalidFilePathException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public final class ReadPropertyFile {
    private ReadPropertyFile() {
    }

    private static Properties properties = new Properties();

    static {
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(FrameworkConstants.getConfigFilePath());
            properties.load(fs);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static String getValue(ConfigProperties key) {
        String value = "";
        value = properties.getProperty(key.name().toLowerCase());
        if (Objects.isNull(value)) {
            throw new InvalidFilePathException("Property name " + key + " is not found. Please check config.properties");
        }
        return value;
    }
}
