package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PageElementInspector {

    public static void inspectPage(WebDriver driver) {
        System.out.println("\n========== PAGE ELEMENT INSPECTION ==========");
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page Title: " + driver.getTitle());
        System.out.println("\n");

        // Inspect all input elements
        inspectInputElements(driver);

        // Inspect all button elements
        inspectButtonElements(driver);

        // Inspect all form elements
        inspectFormElements(driver);

        System.out.println("============================================\n");
    }

    private static void inspectInputElements(WebDriver driver) {
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        System.out.println("===== INPUT ELEMENTS (" + inputs.size() + ") =====");
        for (int i = 0; i < inputs.size(); i++) {
            WebElement input = inputs.get(i);
            System.out.println("\nInput #" + (i + 1) + ":");
            printElementAttributes(input);
        }
    }

    private static void inspectButtonElements(WebDriver driver) {
        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        System.out.println("\n===== BUTTON ELEMENTS (" + buttons.size() + ") =====");
        for (int i = 0; i < buttons.size(); i++) {
            WebElement button = buttons.get(i);
            System.out.println("\nButton #" + (i + 1) + ":");
            printElementAttributes(button);
            System.out.println("  Text: " + button.getText());
        }

        // Also check for input type submit
        List<WebElement> submitInputs = driver.findElements(By.xpath("//input[@type='submit']"));
        if (!submitInputs.isEmpty()) {
            System.out.println("\n===== SUBMIT INPUT ELEMENTS (" + submitInputs.size() + ") =====");
            for (int i = 0; i < submitInputs.size(); i++) {
                WebElement submit = submitInputs.get(i);
                System.out.println("\nSubmit Input #" + (i + 1) + ":");
                printElementAttributes(submit);
            }
        }
    }

    private static void inspectFormElements(WebDriver driver) {
        List<WebElement> forms = driver.findElements(By.tagName("form"));
        System.out.println("\n===== FORM ELEMENTS (" + forms.size() + ") =====");
        for (int i = 0; i < forms.size(); i++) {
            WebElement form = forms.get(i);
            System.out.println("\nForm #" + (i + 1) + ":");
            printElementAttributes(form);
        }
    }

    private static void printElementAttributes(WebElement element) {
        try {
            System.out.println("  Tag: " + element.getTagName());
            System.out.println("  ID: " + element.getAttribute("id"));
            System.out.println("  Name: " + element.getAttribute("name"));
            System.out.println("  Type: " + element.getAttribute("type"));
            System.out.println("  Class: " + element.getAttribute("class"));
            System.out.println("  Placeholder: " + element.getAttribute("placeholder"));
            System.out.println("  Value: " + element.getAttribute("value"));
            System.out.println("  Visible: " + element.isDisplayed());
            System.out.println("  Enabled: " + element.isEnabled());
        } catch (Exception e) {
            System.out.println("  Error reading attributes: " + e.getMessage());
        }
    }
}
