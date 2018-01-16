package de.jambonna.lolpapers.ci.webdriver;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class WebdriverTestAbstract {
    private static final Logger logger = LoggerFactory.getLogger(WebdriverTestAbstract.class);
    

//    private PhantomJSDriver mainDriver;
    private Path workingDir;
    
    private boolean withDevHeader;
    private String languageHeader;
    private Long rngSeedHeader;
    
    public String getEnvValue(String keyPart) {
        String prop = "de.jambonna.lolpapers.ci." + keyPart;
        String value = System.getProperty(prop);
        logger.debug("Prop {} value : \"{}\"", prop, value);
        if (value == null || value.length() == 0) {
            throw new IllegalStateException("Provide \"" + prop + "\" system prop");
        }
        return value;
    }

    public String getAppBaseUrl() {
        return getEnvValue("baseUrl");
    }
    public String getPhantomJSBasePath() {
        return getEnvValue("phantomJSPath");
    }
    public Path getWorkingDir() throws IOException {
        if (workingDir == null) {
            String workingDirPath = "webdriver-var";
            try {
                workingDirPath = getEnvValue("workingDir");
            } catch (Exception e) {
            }
            workingDir = Paths.get(workingDirPath).toAbsolutePath();
            logger.info("Working dir is {}, creating it if needed", workingDir);
            Files.createDirectories(workingDir);
        }
        return workingDir;
    }

    public void setWithDevHeader(boolean withDevHeader) {
        this.withDevHeader = withDevHeader;
    }

    public void setLanguageHeader(String languageHeader) {
        this.languageHeader = languageHeader;
    }
    public void setDefaultLanguageHeader() {
        setLanguageHeader("fr-FR,fr;q=0.9,en-US;q=0.8,en;q=0.7");
    }

    public void setRngSeedHeader(Long rngSeedHeader) {
        this.rngSeedHeader = rngSeedHeader;
    }
    
    
    
    public void addPhantomJSDriverCapsHeader(
            DesiredCapabilities caps, String header, String headerValue) {
        caps.setCapability(
                PhantomJSDriverService.PHANTOMJS_PAGE_CUSTOMHEADERS_PREFIX + header, 
                headerValue);
    }
    public PhantomJSDriver createPhantomJSDriver() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(
                PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, 
                getPhantomJSBasePath());
        if (languageHeader != null) {
            addPhantomJSDriverCapsHeader(caps, "Accept-Language", languageHeader);
        }
        if (withDevHeader) {
            addPhantomJSDriverCapsHeader(caps, "X-Dev-Key", getEnvValue("devKey"));
        }
        if (rngSeedHeader != null) {
            addPhantomJSDriverCapsHeader(caps, "X-Dev-Rng-Seed", rngSeedHeader.toString());
        }
        PhantomJSDriver driver = new PhantomJSDriver(caps);
        logger.debug("PhantomJS driver created");
        return driver;
    }
    
    public void initBrowserForLolpapers(PhantomJSDriver driver) {
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().setSize(new Dimension(1000, 600));
//        setHeader(driver, "Accept-Language", "fr-FR,fr;q=0.9,en-US;q=0.8,en;q=0.7");
        logger.debug("Browser options set for Lolpapers");
    }

//    public PhantomJSDriver getMainDriver() {
//        if (mainDriver == null) {
//            mainDriver = createPhantomJSDriver();
//        }
//        return mainDriver;
//    }
    
    public String getCurrentRelUrl(WebDriver driver) {
        String baseUrl = getAppBaseUrl();
        String url = driver.getCurrentUrl();
        if (url == null || !url.startsWith(baseUrl)) {
            throw new IllegalStateException("Invalid current url : \"" + url + "\"");
        }
        return url.substring(baseUrl.length());
    }
    
//    public void setHeader(PhantomJSDriver driver, String name, String value) {
//        if (value != null) {
//            driver.executePhantomJS("page.customHeaders['" + name + "'] = '" + value + "';");
//        } else {
//            driver.executePhantomJS("del page.customHeaders['" + name + "'];");
//        }
//    }
    
//    public Path getScreenshotsDir() {
//        return getWorkingDir().resolve("screens");
//    }
    
    public void wait(int msec) throws InterruptedException {
        Thread.sleep(msec);
    }
    public void waitPopoverAnim() throws InterruptedException {
        wait(350);
    }
    
    public void screenshot(TakesScreenshot driver, String name) throws IOException {
        byte[] data = driver.getScreenshotAs(OutputType.BYTES);

//        Path screensDir = getScreenshotsDir();
        Path screenPath = getWorkingDir().resolve(name + ".png");
        logger.info("Saving screenshot {} ...", screenPath);
//        Files.readAllBytes(screenPath)
        Files.write(screenPath, data);
    }
    
    public void devUserConnect(PhantomJSDriver driver, String idOrNewUserName) {
        String js = "$(document.body).append('<form action=\"" + getAppBaseUrl() + "dev/userConnect\" method=\"post\" id=\"dev-user-connect\"><input name=\"id\" id=\"dev-user-connect-id\" /></form>');";
        logger.debug("Executing JS : {}", js);
        driver.executeScript(js);
        logger.debug("Submitting connect form...");
        driver.findElementById("dev-user-connect-id").sendKeys(idOrNewUserName);
        driver.findElementById("dev-user-connect").submit();
    }
}
