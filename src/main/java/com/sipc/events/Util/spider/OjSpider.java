package com.sipc.events.Util.spider;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;
@Component
public class OjSpider {
    @Value("${oj.username}")
    private String username;
    @Value("${oj.password}")
    private String password;
    @Value("${oj.t2pass}")
    private String t2Pass;
    @Value("${oj.t3pass}")
    private String t3Pass;
    @Value("${oj.t4pass}")
    private String t4Pass;

    public String getSessionId() throws InterruptedException {
        WebDriver driver;
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--window-size=1920,1080");
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        options.addArguments("--disable-in-process-stack-traces");
        options.addArguments("--disable-logging");
        options.addArguments("--log-level=3");
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.get("https://code.sipcoj.com/");
        //login
        driver.findElement(By.xpath("//*[@id=\"header\"]/ul/div[2]/button[1]")).click();
        driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div[2]/div/form/div[1]/div/div[1]/input")).sendKeys(username);
        driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div[2]/div/form/div[2]/div/div/input")).sendKeys(password);
        driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div[2]/div/div/button")).click();
        //select contest
        driver.findElement(By.xpath("//*[@id=\"header\"]/ul/li[3]")).click();
        Thread.sleep(1000);
        //task 2
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div/div[1]/div[3]/div/ol/li[3]/div/div[1]/p/a")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div[1]/div/div/div[1]/div[3]/div/div[2]/div/input")).sendKeys(t2Pass);
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div[1]/div/div/div[1]/div[3]/div/div[2]/button")).click();
        //select contest
        driver.findElement(By.xpath("//*[@id=\"header\"]/ul/li[3]")).click();
        Thread.sleep(1000);
        //task 3
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div/div[1]/div[3]/div/ol/li[4]/div/div[1]/p/a")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div[1]/div/div/div[1]/div[3]/div/div[2]/div/input")).sendKeys(t3Pass);
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div[1]/div/div/div[1]/div[3]/div/div[2]/button")).click();
        //select contest
        driver.findElement(By.xpath("//*[@id=\"header\"]/ul/li[3]")).click();
        Thread.sleep(1000);
        //task 4
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div/div[1]/div[3]/div/ol/li[5]/div/div[1]/p/a")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div[1]/div/div/div[1]/div[3]/div/div[2]/div/input")).sendKeys(t4Pass);
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div[1]/div/div/div[1]/div[3]/div/div[2]/button")).click();
        Thread.sleep(2000);
        Set<Cookie> cookies = driver.manage().getCookies();
        String res = null;
        for (Cookie cookie : cookies) {
            if ("sessionid".equals(cookie.getName())) {
                res = cookie.getValue();
            }
        }
        driver.close();
        return res;
    }

}
