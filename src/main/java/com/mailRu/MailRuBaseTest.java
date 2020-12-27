package com.mailRu;

import com.utils.BaseTest;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;

public class MailRuBaseTest extends BaseTest {
    private final static String propertiesPath = "\\src\\main\\resources\\mailRuProps";

    @BeforeSuite
    public static void setPropertiesPath() throws IOException {
        getProperties(propertiesPath);
    }
}
