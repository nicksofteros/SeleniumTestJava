package core;

public class Settings
{
    //public static final String BASE_URL = "https://www.cineworld.co.uk/";
    //public static final String BASE_URL = "https://www.cinema-city.pl/";
    //get main url from property - maven parameter: -Dmain_url
    public static final String BASE_URL = System.getProperty("main_url");
    public static final String PATH_TO_FIREFOX_DRIVER = "C:\\SeleniumDrivers\\geckodriver.exe";

    public static final int DRIVER_TIMEOUT = 60;
    public static final int ELEMENT_TIMEOUT = 15;
}
