package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("http://localhost:%d/product/create", serverPort);
    }

    @Test
    void testCreateProductAndVerifyInList(ChromeDriver driver) {
        // 1. Go to Create Page
        driver.get(baseUrl);

        // 2. Fill the form
        driver.findElement(By.id("nameInput")).sendKeys("Sampo Cap Bambang");
        driver.findElement(By.id("quantityInput")).sendKeys("100");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // 3. Verify redirected to List Page
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/product/list"));

        // 4. Check if product is in the table
        List<WebElement> productNameCells = driver.findElements(By.xpath("//td[contains(text(), 'Sampo Cap Bambang')]"));
        assertEquals(1, productNameCells.size());

        WebElement quantityCell = driver.findElement(By.xpath("//td[contains(text(), '100')]"));
        assertTrue(quantityCell.isDisplayed());
    }
}