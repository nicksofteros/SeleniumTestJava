import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import pages.HomePage;


import static core.Core.*;

public class FirstTest
{
    @Test
    public void firstTest()
    {
        HomePage.goTo();
        HomePage.checkHomePageElements();
        HomePage.clickOnShowMoreFilms();
        HomePage.clickOnCommingSoon();
    }

    @BeforeClass
    public void beforeClass()
    {
        startDriver();
    }

    @AfterClass
    public void afterClass()
    {
        stopDriver();
    }
}