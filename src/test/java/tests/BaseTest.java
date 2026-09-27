package tests;

import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.LoadPropertiesUtils;
import org.apache.logging.log4j.Logger;

public abstract class BaseTest {
    protected static final Logger log = LogManager.getLogger(BaseTest.class);

    protected WebDriver driver;

    @BeforeEach
    public void setUp() {
        String browser = LoadPropertiesUtils.get("browser");

        if (!"chrome".equalsIgnoreCase(browser)) {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        int width = LoadPropertiesUtils.getInt("window.width");
        int height = LoadPropertiesUtils.getInt("window.height");

        ChromeOptions options = new ChromeOptions();

        if (LoadPropertiesUtils.getBoolean("headless")) {
            options.addArguments(
                    "--headless=new",
                    "--no-sandbox",
                    "--disable-dev-shm-usage",
                    "--disable-gpu"
            );
        }

        options.addArguments(
                "--window-size=" + width + "," + height,
                "--disable-extensions",
                "--disable-notifications",
                "--no-first-run",
                "--no-default-browser-check"
        );
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.setCapability("se:downloadsEnabled", false);

        ChromeDriver chromeDriver = new ChromeDriver(options);
        blockAds(chromeDriver);
        driver = chromeDriver;

        driver.manage().window().setSize(new Dimension(width, height));
    }

    private void blockAds(ChromeDriver chromeDriver) {
        try {
            chromeDriver.executeCdpCommand("Network.enable", java.util.Map.of());
            chromeDriver.executeCdpCommand("Network.setBlockedURLs", java.util.Map.of(
                    "urls", java.util.List.of(
                            "*googlesyndication.com*",
                            "*doubleclick.net*",
                            "*google-analytics.com*",
                            "*googletagmanager.com*",
                            "*adservice.google.com*",
                            "*amazon-adsystem.com*",
                            "*adnxs.com*",
                            "*taboola.com*",
                            "*outbrain.com*",
                            "*googleads*"
                    )
            ));
            log.info("Ad blocking enabled via CDP");
        } catch (Exception e) {
            log.warn("Cannot enable ad blocking: {}", e.getMessage());
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                log.warn("Driver quit failed: {}", e.getMessage());
            } finally {
                driver = null;
            }
        }
    }
}