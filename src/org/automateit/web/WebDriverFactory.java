/**
 * This file is part of Automate It!'s free and open source web and mobile 
 * application testing framework.
 * 
 * Automate It! is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Automate It! is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Automate It!.  If not, see <http://www.gnu.org/licenses/>.
 **/

package org.automateit.web;

import java.io.File;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.opera.OperaDriver;

import org.automateit.core.StringCapabilities;
import org.automateit.util.CommonProperties;

/**
 * This class returns a class implementing webdriver interface.
 *
 * Supported web browser driver are:
 * 
 * 1) FirefoxDriver
 * 2) IEdriver
 * 3) OperaDriver
 * 4) ChromeDriver
 * 5) SafariDriver
 * 
 * @author mburnside
 */
public class WebDriverFactory {
    
    /**
     * Firefox WebDriver identifier key
     */
    public static final String FIREFOXWEBDRIVER = "FIREFOXWEBDRIVER";
    
     /**
      * Internet Explorer (IE) WebDriver identifier key
      */
    public static final String IEWEBDRIVER = "IEWEBDRIVER";
    
     /**
      * Chrome WebDriver identifier key
      */
    public static final String CHROMEWEBDRIVER = "CHROMEWEBDRIVER";
    
    /**
      * Chrome Mobile app WebDriver identifier key
      */
    public static final String CHROMEMOBILEBDRIVER = "CHROMEMOBILEDRIVER";
    
     /**
      * Opera WebDriver identifier key
      */
    public static final String OPERAWEBDRIVER = "OPERAWEBDRIVER";
    
    /**
     * Safari WebDriver identifier key
     */
    public static final String SAFARIWEBDRIVER = "SAFARIWEBDRIVER";
   
    /**
     *  Logging object
     */
    protected static Logger logger = Logger.getLogger(WebDriverFactory.class);
    
    /**
     * Default Constructor.
     */
    public WebDriverFactory() { }
    
    /**
     * Use this constructor if you want to get an implementation of Selenium
     * using a WebDriver.
     * 
     * @param webDriverId the id of the WebDriver to use
     * @param browserURL the starting URL including just a domain name
     * 
     * @return Instance of a WebDriver implementation
     * 
     * @throws Exception 
     */
    public WebDriver getWebDriver(String webDriverId, String browserURL) throws Exception {
        
        logger.debug("Getting a version 3.0 Selenium/WebDriver for webdriver id: " + webDriverId);
        
        if(webDriverId == null) throw new Exception("webDriverId cannot be null."); 
        
        if(webDriverId.equals(FIREFOXWEBDRIVER)) {
            
            logger.debug("Creating an instance of Firefox Driver");
            
            logger.debug("Setting system property for Firefox Driver - webdriver.firefox.driver=" + CommonProperties.getInstance().get("webdriver.firefox.driver"));
            
            String driverLocation = CommonProperties.getInstance().get("webdriver.firefox.driver.location");
            
            // now we check if its a real file and where it is expected to be. If not, add another "dot" to the path to back one more dir
            File f = new File(driverLocation);
            if(!f.exists() && !f.isFile()) driverLocation = "." + driverLocation;
            
            if((driverLocation == null) || (driverLocation.trim().length() == 0))
                   throw new Exception("The system property 'webdriver.firefox.driver.location' is not configured propertly: " + driverLocation);
            
            System.setProperty("webdriver.gecko.driver", driverLocation);
            
            CommonProperties.getInstance().setBrowserType(CommonProperties.getInstance().FIREFOX);
            
            return ((WebDriver)new FirefoxDriver());
           
        }
        
        else if(webDriverId.equals(IEWEBDRIVER)) {
            
            logger.debug("Creating an instance of WebDriverBackedSelenium using Internet Explorer Driver");
            
            String driverLocation = CommonProperties.getInstance().get("webdriver.ie.driver.location");
            
            // now we check if its a real file and where it is expected to be. If not, add another "dot" to the path to back one more dir
            File f = new File(driverLocation);
            if(!f.exists() && !f.isFile()) driverLocation = "." + driverLocation;
            
            logger.debug("Setting system property for IE Driver - webdriver.ie.driver.location=" + driverLocation);
            
            if((driverLocation == null) || (driverLocation.trim().length() == 0))
                   throw new Exception("The system property 'webdriver.ie.driver' is not configured propertly: " + driverLocation);
            
            System.setProperty("webdriver.ie.driver", driverLocation);
            
            CommonProperties.getInstance().setBrowserType(CommonProperties.getInstance().IE);
            
            return ((WebDriver)new InternetExplorerDriver());
            
        }
        
        else if(webDriverId.equals(SAFARIWEBDRIVER)) {
            
            logger.debug("Creating an instance of Safari Driver. Please be sure that the webDriver extension is installed and enabled on Safari.");
            
            CommonProperties.getInstance().setBrowserType(CommonProperties.getInstance().SAFARI);
            
            return ((WebDriver)new SafariDriver());
           
        }
        
        else if(webDriverId.equals(CHROMEWEBDRIVER)) {
            
            logger.debug("Creating an instance of Chrome Driver");
            
            String driverLocation = CommonProperties.getInstance().get("webdriver.chrome.driver.location");
            
            // now we check if its a real file and where it is expected to be. If not, add another "dot" to the path to back one more dir
            File f = new File(driverLocation);
            if(!f.exists() && !f.isFile()) driverLocation = "." + driverLocation;
            
            logger.debug("Setting system property for Chrome Driver - webdriver.chrome.driver.location=" + driverLocation);
            
            if((driverLocation == null) || (driverLocation.trim().length() == 0))
                   throw new Exception("The system property 'webdriver.chrome.driver.location' is not configured propertly: " + driverLocation);
            
            System.setProperty("webdriver.chrome.driver", driverLocation);
            
            CommonProperties.getInstance().setBrowserType(CommonProperties.getInstance().CHROME);
            
            ChromeOptions options = new ChromeOptions();

            options.addArguments("disable-infobars");
            options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
            
            return ((WebDriver)new ChromeDriver(options));
           
        }
        
        else if(webDriverId.equals(CHROMEMOBILEBDRIVER)) {
            
            logger.debug("Creating an instance of Chrome Mobile Web Driver");
            
            String driverLocation = CommonProperties.getInstance().get("webdriver.chrome.driver.location");
            
            // now we check if its a real file and where it is expected to be. If not, add another "dot" to the path to back one more dir
            File f = new File(driverLocation);
            if(!f.exists() && !f.isFile()) driverLocation = "." + driverLocation;
            
            logger.debug("Setting system property for Chrome Mobile Web Driver - webdriver.chrome.driver.location=" + driverLocation);
            
            if((driverLocation == null) || (driverLocation.trim().length() == 0))
                   throw new Exception("The system property 'webdriver.chrome.driver.location' is not configured propertly: " + driverLocation);
            
            System.setProperty("webdriver.chrome.driver", driverLocation);
            
            CommonProperties.getInstance().setBrowserType(CommonProperties.getInstance().CHROME);
            
            Map<String, String> mobileEmulation = new HashMap<>();

            mobileEmulation.put(StringCapabilities.DEVICE_NAME.getCapability(), CommonProperties.getInstance().get(StringCapabilities.DEVICE_NAME.getCapability()));
            mobileEmulation.put(StringCapabilities.BROWSER_NAME.getCapability(), CommonProperties.getInstance().get(StringCapabilities.BROWSER_NAME.getCapability()));
            
            ChromeOptions options = new ChromeOptions();
            
            options.setExperimentalOption("mobileEmulation", mobileEmulation);

            //options.addArguments("disable-infobars");
            //options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
            
            return ((WebDriver)new ChromeDriver(options));
           
        }
        
        else if(webDriverId.equals(OPERAWEBDRIVER)) {
            
            logger.debug("Creating an instance of WebDriverBackedSelenium using Opera Driver");
            
            CommonProperties.getInstance().setBrowserType(CommonProperties.getInstance().OPERA);
            
            return ((WebDriver)new OperaDriver());
            
        }
        
        // if we get here then we could not find the correct driver so throw an excpetion
        throw new Exception("Unable to return a new instance of a webdriver implementation because none were found for id;" + webDriverId);
        
    }
    
}