package core;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class AdditionalConditions
{
    public static ExpectedCondition<Boolean> angularHasFinishedProcessing()
    {
        return new ExpectedCondition<Boolean>()
        {
            //@Override
            public Boolean apply(WebDriver driver)
            {
                //for debuging:
                //SeleniumCore.log("angular=" + ((JavascriptExecutor) driver).executeScript("return (window.angular !== undefined)"));
                //SeleniumCore.log("angular2=" + ((JavascriptExecutor) driver).executeScript("return (angular.element(document).injector() !== undefined)"));
                // SeleniumCore.log( "angular3=" + ((JavascriptExecutor) driver).executeScript("return (angular.element(document))" ));
                //SeleniumCore.log( "angular3=" + ((JavascriptExecutor) driver).executeScript("return (angular.element(document).injector().get('$http').pendingRequests.length === 0)" ));
                //((JavascriptExecutor) driver).executeScript("return (jQuery.active) == 0");

                boolean isJSComplete = ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
                boolean isJQueryComplete = Boolean.valueOf(((JavascriptExecutor) driver).executeScript("return (jQuery.active) == 0").toString());

                final String query =
                        "window.angularFinished = false;" + "waitForAngular =function() {" + "  window.angularFinished = false;"
                                + "  var el = document.querySelector('body');"
                                + "  var callback = (function(){window.angularFinished=1});"
                                + "  angular.element(el).injector().get('$browser')."
                                + "      notifyWhenNoOutstandingRequests(callback);};";


                boolean isAngularComplete = ((JavascriptExecutor) driver).executeScript(query + "  waitForAngular(); return window.angularFinished;").toString().equals("1");
                Core.log("isJSComplete="+isJSComplete+"  isJQueryComplete="+isJQueryComplete+"  isAngularComplete="+isAngularComplete);

                return isJSComplete && isJQueryComplete && isAngularComplete;
            }
        };
    }


    public static ExpectedCondition<Boolean> javascriptHasFinishedProcessing()
    {
        return new ExpectedCondition<Boolean>()
        {
            //@Override
            public Boolean apply(WebDriver driver)
            {
                boolean isJSComplete = ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
                Core.log("isJSComplete="+isJSComplete);

                return isJSComplete ;
            }
        };
    }
}