package pages;

import core.Core;
import core.Settings;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;
import static core.Core.*;

public class HomePage
{
    private static By quickBookSelector = By.className("quickbook-component");
    private static By quickBookDropDownSelector = By.xpath("//div[@data-automation-id='selectCinema']//button");
    private static By byCinemaSelector = By.xpath("//button[@data-automation-id='searchByCinema']");
    private static By byFilmSelector = By.xpath("//button[@data-automation-id='searchByFilm']");

    private static By filmRowsSelector = By.xpath("//div[@class='row' and @aria-expanded='true']");

    private static By nowBookingSelector = By.id("nowBooking_tab");
    private static By commingSoonSelector = By.id("comingSoon_tab");
    private static By showMoreFilmsSelector = By.xpath("//div[contains(@class,'show-more-btn-container')]//button");

    private static final String LOG_PREFIX = "[HOME PAGE]: ";

    public static void goTo()
    {
        log(LOG_PREFIX + "go to main URL");
        try
        {
            DRIVER.get(Settings.BASE_URL);
        }
        catch(TimeoutException e)
        {
            log("TIMEOUT EXCEPTION: can't load main page after" + Settings.DRIVER_TIMEOUT + "seconds");
        }
    }

    public static void checkHomePageElements()
    {
        log(LOG_PREFIX + "checking elements after page loaded");

        WebElement quickBook = DRIVER.findElement(quickBookSelector);
        Assert.assertTrue( quickBook.isDisplayed(),"Quick Book element is not displayed" );

        WebElement quickBookDropDown = waitAndGetElement(quickBookDropDownSelector);
        Assert.assertTrue  ( quickBookDropDown.isDisplayed(),"Quick Book dropdown element is not displayed" );

        WebElement byCinema = waitAndGetElement(byCinemaSelector);
        Assert.assertTrue  ( byCinema.isDisplayed(),"By Cinema element is not displayed" );
        Assert.assertTrue  ( byCinema.getAttribute("class").contains("active"),"By Cinema element is not active" );

        WebElement byFilm = waitAndGetElement(byFilmSelector);
        Assert.assertTrue  ( byFilm.isDisplayed(),"By Film element is not displayed" );
        Assert.assertFalse ( byFilm.getAttribute("class").contains("active") );

        WebElement nowBooking = waitAndGetElement(nowBookingSelector);
        Assert.assertTrue( nowBooking.isDisplayed(),"new Booking element is not displayed" );

        WebElement ncommingSoon = Core.waitAndGetElement(commingSoonSelector);
        Assert.assertTrue( ncommingSoon.isDisplayed(),"comming Soon element is not displayed" );
    }

    public static void clickOnShowMoreFilms()
    {
        log(LOG_PREFIX + "click on Show more Films");

        List<WebElement> filmsBefore = DRIVER.findElements(filmRowsSelector);
        int rowsBefore = 0;

        //check if list of elements is null
        if(filmsBefore != null)
        {
            rowsBefore = filmsBefore.size();
        }
        else
        {
            Assert.fail("Film list - rows is null");
        }

        WebElement element = waitAndGetElement(showMoreFilmsSelector);
        element.click();

        List<WebElement> filmsAfter = DRIVER.findElements(filmRowsSelector);
        int rowsAfter = 0;

        //check if list of elements is null
        if(filmsAfter != null)
        {
            rowsAfter = filmsAfter.size();
        }
        else
        {
            Assert.fail("Film list - rows is null");
        }

        log("rows before click: " + rowsBefore + ", rows after click: " + rowsAfter );
        Assert.assertTrue(rowsAfter > rowsBefore, "Rows after click on Show more films are not greater than before click");
    }

    public static void clickOnCommingSoon()
    {
        log(LOG_PREFIX + "click on Comming Soon");

        WebElement element = waitAndGetElement(commingSoonSelector);
        element.click();

        List<WebElement> filmsAfter = DRIVER.findElements(filmRowsSelector);
        int rowsAfter = filmsAfter.size();

        log("film rows after click: " + rowsAfter );
        Assert.assertTrue(rowsAfter > 0, "Rows after click on Comming soon are not > 0");
    }
}
