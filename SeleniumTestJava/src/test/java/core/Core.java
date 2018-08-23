package core;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Core
{
    public static WebDriver DRIVER;
    public static BrowserMobProxy PROXY;
    private static Har HAR_OBJECT;

    public static void startDriver()
    {
        // start the proxy
        log("Proxy starting...");
        PROXY = new BrowserMobProxyServer();
        PROXY.start(0);

        // get the Selenium proxy object
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(PROXY);

        // configure it as a desired options for browser
        FirefoxOptions options = new FirefoxOptions();
        options.setCapability(CapabilityType.PROXY, seleniumProxy);

        //start the webdriver
        log("Driver starting...");
        System.setProperty("webdriver.gecko.driver", Settings.PATH_TO_FIREFOX_DRIVER);
        DRIVER = new FirefoxDriver(options);
        log("Driver started");

        //manage main settings for driver
        DRIVER.manage().timeouts().implicitlyWait(Settings.DRIVER_TIMEOUT, TimeUnit.SECONDS);
        DRIVER.manage().timeouts().pageLoadTimeout(Settings.DRIVER_TIMEOUT, TimeUnit.SECONDS);
        DRIVER.manage().deleteAllCookies();

        // create a new HAR objcet
        log("creating HAR file");
        PROXY.newHar(Settings.BASE_URL);
        HAR_OBJECT = PROXY.getHar();
    }

    public static void stopDriver()
    {
        // get the HAR data
        HAR_OBJECT = PROXY.getHar();

        //list all URLs for google analitycs:
        log("Google analytics URLs:");
        int analyticsCount = 0;
        List<HarEntry> entries = PROXY.getHar().getLog().getEntries();
        for(HarEntry entry :entries)
        {
            if(entry.getRequest().getUrl().contains("google-analytics.com/collect"))
            {
                analyticsCount++;
                log("URL: "+entry.getRequest().getUrl());
            }
        }

        Assert.assertTrue(analyticsCount>0);

        PROXY.stop();
        DRIVER.quit();
    }

    public static WebElement waitAndGetElement(By by)
    {
        Core.log(" -:- get element: [" + by + "]");
        WebElement element = null;
        WebDriverWait wait = new WebDriverWait(DRIVER, Settings.ELEMENT_TIMEOUT,50);

        try
        {
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return element;

        }
        catch (TimeoutException ex)
        {
            return null;
        }
    }

    public static void waitForJS()
    {
        log("----- wait for java script -----");

        WebDriverWait wait = new WebDriverWait(DRIVER, Settings.ELEMENT_TIMEOUT, 300);
        wait.until(ExpectedConditions.jsReturnsValue(""));

        boolean isComplete = wait.until(AdditionalConditions.javascriptHasFinishedProcessing());
        if (isComplete == false)
        {
            Assert.fail("wait for java script timeout");
        }
    }

    public static void log(String logString)
    {
        //it can be changed to another logger
        System.out.println(logString);
    }
}
