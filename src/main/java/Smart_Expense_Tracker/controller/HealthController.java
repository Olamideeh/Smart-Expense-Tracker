package Smart_Expense_Tracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class HealthController {
    @GetMapping("/")
    public String home() {
        return "welcome to Smart Expense Tracker API";
    }

    @GetMapping("/health")
    public String health() {
        return "Application is running sucessfully";
    }

    @GetMapping("/About")
    public String about() {
        return "Smart Expense Tracker Backend\n" +
                "Version 1.0\n" +
                "Built during my 100 Days of Code Challenge.";
    }
}
