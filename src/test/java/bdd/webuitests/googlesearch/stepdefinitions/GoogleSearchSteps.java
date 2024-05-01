package bdd.webuitests.googlesearch.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.k11techlab.bdd.webuitestframework.pageobjects.googlesearchexample.Homepage;
import static org.k11techlab.bdd.webuitestframework.driverUtil.WebDriverEngine.getDriver;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.io.File;
import java.io.FileWriter;

/**
 * This class contains methods to perform action on home page.
 */
public class GoogleSearchSteps{

	FileWriter writer;
	File file;
	PrintWriter pw;

	Homepage homepage = new Homepage(getDriver());
	String csvFilePath = ".//src//test//java//outputFiles//searchResults.csv";


	/**
	 * method to open the mentioned url
	 */
	@Given("^user is on google search page \"([^\"]*)\"$")
	public void user_is_on_google_search_page_something(String url) throws Throwable {
		homepage.get(url);
	}

	/**
	 * method to search the required string on google search
	 * @param stringtoBeSearched value to be searched
	 * @throws Throwable
	 */
	@And("^searches for \"([^\"]*)\"$")
	public void searches_for_something(String stringtoBeSearched) throws Throwable {
		homepage.waitForPageToLoad(homepage.getTitle());
		homepage.waitForVisibility(homepage.searchbox);
		homepage.findElement(homepage.searchbox).sendKeys(stringtoBeSearched);
	}


	/**
	 * method to click on search button
	 * @throws Throwable
	 */
	@When("^user clicks on search button$")
	public void user_clicks_on_search_button() throws Throwable {
		homepage.waitForPageToLoad("Google");
		homepage.waitForElementToBeClickable(homepage.searchButton);
		homepage.clickOnElementUsingJs(homepage.searchButton);
	}



	/**
	 * saving the results title and link in the csv file present under outputFiles folder
	 * @throws Throwable
	 */
	@Then("^the results should be displayed and saved in csv file$")
	public void the_results_should_be_displayed_and_saved_in_csv_file() throws Throwable {
		homepage.waitForPageToLoad(homepage.getTitle());
		homepage.waitForVisibility(homepage.searchResults);
		List<WebElement> allSearchResults = homepage.findElements(homepage.searchResults);

		try{

			file = new File(csvFilePath);

			if (!file.exists())
				file.createNewFile();
			pw = new PrintWriter(new File(csvFilePath));
			StringBuilder sb = new StringBuilder();


			//for header
			sb.append("Title");
			sb.append("   ,    ");
			sb.append("Link");
			sb.append('\n');

			// writing the required data to CSV file
			for (WebElement result : allSearchResults){

				sb.append(result.getText());
				sb.append("   ,    ");
				sb.append(result.getAttribute("href"));
				sb.append('\n');
			}

			pw.write(sb.toString());

		}
		catch(Exception e) {
			System.out.println("Error while writing to csv file !!!");
			e.printStackTrace();
		} finally {
			try {
				pw.flush();
				pw.close();
			} catch (Exception e) {

				System.out.println("Error while flushing/closing fileWriter !!!");

				e.printStackTrace();
			}
		}
	}

	/**
	 * method to click on image button on google search page
	 * @throws Throwable
	 */
	@When("^user clicks on image tab$")
	public void user_clicks_on_image_tab() throws Throwable {
		homepage.waitForPageToLoad("Google Images");
		homepage.waitForElementToBeClickable(homepage.imagesButton);
		homepage.clickOnElementUsingJs(homepage.imagesButton);
	}

	/**
	 * method to verify if images are displayed in result
	 * @throws Throwable
	 */
	@Then("^the results images should be displayed$")
	public void the_results_images_should_be_displayed() throws Throwable {
		homepage.waitForPageToLoad(homepage.getTitle());
		homepage.waitForVisibility(homepage.imageContainer);
	}
}
