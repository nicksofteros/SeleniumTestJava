package pages;

import core.Core;
import core.Settings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;
import static core.Core.*;

public class HomePage
{
    private static By quickBookSelector = By.className("quickbook-component");
    private static By quickBookDropDownSelector = By.xpath("//div[@data-automation-id='selectCinema']//button");
    private static By byCinemakSelector = By.xpath("//button[@data-automation-id='searchByCinema']");
    private static By byFilmSelector = By.xpath("//button[@data-automation-id='searchByFilm']");

    private static By filmRowsSelector = By.xpath("//div[@class='row' and @aria-expanded='true']");

    private static By nowBookingSelector = By.linkText("Now booking");
    private static By commingSoonSelector = By.linkText("Coming soon");
    private static By showMoreFilmsSelector = By.xpath("//button[contains(.,'Show more films')]");

    private static final String LOG_PREFIX = "[HOME PAGE]: ";

    public static void goTo()
    {
        log(LOG_PREFIX + "go to main URL");
        DRIVER.get(Settings.BASE_URL);
    }

    public static void checkHomePageElements()
    {
        log(LOG_PREFIX + "checking elements after page loaded");

        WebElement quickBook = DRIVER.findElement(quickBookSelector);
        Assert.assertTrue( quickBook.isDisplayed() );

        WebElement quickBookDropDown = waitAndGetElement(quickBookDropDownSelector);
        Assert.assertTrue  ( quickBookDropDown.isDisplayed() );
        Assert.assertEquals( quickBookDropDown.getAttribute("title"),"Choose a cinema");

        WebElement byCinema = waitAndGetElement(byCinemakSelector);
        Assert.assertTrue  ( byCinema.isDisplayed() );
        Assert.assertEquals( byCinema.getText(),"By Cinema" );
        Assert.assertTrue  ( byCinema.getAttribute("class").contains("active") );

        WebElement byFilm = waitAndGetElement(byFilmSelector);
        Assert.assertTrue  ( byFilm.isDisplayed() );
        Assert.assertEquals( byFilm.getText(),"By Film" );
        Assert.assertFalse ( byFilm.getAttribute("class").contains("active") );

        WebElement nowBooking = waitAndGetElement(nowBookingSelector);
        Assert.assertTrue( nowBooking.isDisplayed() );

        WebElement ncommingSoon = Core.waitAndGetElement(commingSoonSelector);
        Assert.assertTrue( ncommingSoon.isDisplayed() );
    }

    public static void clickOnShowMoreFilms()
    {
        log(LOG_PREFIX + "click on Show more Films");

        List<WebElement> filmsBefore = DRIVER.findElements(filmRowsSelector);
        int rowsBefore = filmsBefore.size();

        WebElement element = waitAndGetElement(showMoreFilmsSelector);
        element.click();

        List<WebElement> filmsAfter = DRIVER.findElements(filmRowsSelector);
        int rowsAfter = filmsAfter.size();

        log("rows before click: " + rowsBefore + ", rows after click: " + rowsAfter );
        Assert.assertTrue(rowsAfter > rowsBefore);
    }

    public static void clickOnCommingSoon()
    {
        log(LOG_PREFIX + "click on Comming Soon");

        WebElement element = waitAndGetElement(commingSoonSelector);
        element.click();

        List<WebElement> filmsAfter = DRIVER.findElements(filmRowsSelector);
        int rowsAfter = filmsAfter.size();

        log("film rows after click: " + rowsAfter );
        Assert.assertTrue(rowsAfter > 0);
    }
}
