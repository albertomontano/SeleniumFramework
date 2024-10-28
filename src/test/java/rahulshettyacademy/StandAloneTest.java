package rahulshettyacademy;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StandAloneTest {
    public static void main(String[] args) {
        String selectedProductName ="ZARA COAT 3";
        //usamos la la siguiente linea de webDriverManager que importamos de maven, asi no es necesario tener
        //web driver instalado en la local, esto reemplaaria al System.setProperty("webDriver.chromeDriver","ruta/driver");
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://rahulshettyacademy.com/client/");
        driver.findElement(By.id("userEmail")).sendKeys("peter789@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("Peter1234#");
        driver.findElement(By.id("login")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".col-lg-4"))); //Aqui voy a esperar a que los productos sean visibles

        //Creo una lista de WebElements de los productos, los cuales se han encontrado con un localizador comun que es un class selector
        List<WebElement> products = (List<WebElement>) driver.findElements(By.cssSelector(".col-lg-4"));

        //usamos a continuacion Java Streams con una funcion lamda para guardar un WebElement prod al producto que
        // contenga el texto que sea igual a "ZARA COAT-3".
        // Primero se pasa la lista de productos a un Stream con products.stream(), enseguida, se filtra
        // con la función lamda que es como un loop, que va a encontrar el elemento, va a encontrar el first o Else que va a marcar
        // como null. Esto se puede hacer con un loop de busqueda tradicional, pero ya se esta usando el Java Streams con Lamda
        WebElement prod = products.stream().filter(product->
                product.findElement(By.cssSelector("b")).getText().equals(selectedProductName)).findFirst().orElse(null);

        //A continuacion se procede a dar click en add to cart, para esto, usamos el prod.findElement para limitar la busqueda de
        //todos los botones a solo los que esten dentro del elemento prod
        prod.findElement(By.xpath("//button[contains(text(), 'Add To Cart')]")).click();

        //A continuacion, un explicit wait para esperar a que aparezca un dinamic web element (toast message)
        WebElement toast = driver.findElement(By.cssSelector("#toast-container"));

        wait.until(ExpectedConditions.visibilityOf(toast));
        // tambien puede ser: wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));

        //tambien podemos poner un wait pra esperar a que el web elemento dinamico desaparezca:
        wait.until(ExpectedConditions.invisibilityOf(toast));

        driver.findElement(By.cssSelector("[routerlink=\"/dashboard/cart")).click();







    }


}
