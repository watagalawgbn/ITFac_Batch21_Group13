package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static WebDriver initializeDriver(String browser) {
        WebDriver webDriver;

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                
                // Disable password manager and breach detection (from incoming)
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                prefs.put("profile.password_manager_leak_detection", false);
                options.setExperimentalOption("prefs", prefs);
                
                // Combined arguments from both versions
                options.addArguments("--start-maximized");
                options.addArguments("--disable-notifications");
                options.addArguments("--disable-save-password-bubble");
                options.addArguments("--disable-infobars");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-gpu");
                options.addArguments("--disable-extensions");
                options.addArguments("--disable-blink-features=AutomationControlled");
                options.addArguments("--remote-allow-origins=*");
                options.addArguments("--disable-web-security");
                options.addArguments("--disable-features=VizDisplayCompositor");
                options.addArguments("--disable-features=IsolateOrigins,site-per-process");
                options.addArguments("--disable-site-isolation-trials");
                options.addArguments("--disable-renderer-backgrounding");
                options.addArguments("--disable-background-timer-throttling");
                options.addArguments("--disable-backgrounding-occluded-windows");
                options.addArguments("--disable-ipc-flooding-protection");
                options.addArguments("--disable-hang-monitor");
                options.setPageLoadStrategy(org.openqa.selenium.PageLoadStrategy.NORMAL);
                options.setAcceptInsecureCerts(true);
                webDriver = new ChromeDriver(options);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                webDriver = new FirefoxDriver();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                webDriver = new EdgeDriver();
                break;

            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions defaultOptions = new ChromeOptions();
                
                Map<String, Object> defaultPrefs = new HashMap<>();
                defaultPrefs.put("credentials_enable_service", false);
                defaultPrefs.put("profile.password_manager_enabled", false);
                defaultPrefs.put("profile.password_manager_leak_detection", false);
                defaultOptions.setExperimentalOption("prefs", defaultPrefs);
                
                defaultOptions.addArguments("--start-maximized");
                defaultOptions.addArguments("--disable-notifications");
                defaultOptions.addArguments("--disable-save-password-bubble");
                defaultOptions.addArguments("--disable-infobars");
                defaultOptions.addArguments("--disable-dev-shm-usage");
                defaultOptions.addArguments("--no-sandbox");
                defaultOptions.addArguments("--disable-gpu");
                defaultOptions.addArguments("--disable-extensions");
                defaultOptions.addArguments("--disable-blink-features=AutomationControlled");
                defaultOptions.addArguments("--remote-allow-origins=*");
                defaultOptions.addArguments("--disable-web-security");
                defaultOptions.addArguments("--disable-features=VizDisplayCompositor");
                defaultOptions.addArguments("--disable-features=IsolateOrigins,site-per-process");
                defaultOptions.addArguments("--disable-site-isolation-trials");
                defaultOptions.addArguments("--disable-renderer-backgrounding");
                defaultOptions.addArguments("--disable-background-timer-throttling");
                defaultOptions.addArguments("--disable-backgrounding-occluded-windows");
                defaultOptions.addArguments("--disable-ipc-flooding-protection");
                defaultOptions.addArguments("--disable-hang-monitor");
                defaultOptions.setPageLoadStrategy(org.openqa.selenium.PageLoadStrategy.NORMAL);
                defaultOptions.setAcceptInsecureCerts(true);
                webDriver = new ChromeDriver(defaultOptions);
                break;
        }

        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        webDriver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
        driver.set(webDriver);

        return webDriver;
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}