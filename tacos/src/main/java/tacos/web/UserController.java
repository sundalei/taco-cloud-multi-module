package tacos.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Slf4j
@Controller
@RequestMapping("users")
public class UserController {
    
    private static int counter = 0;

    @RequestMapping
    public String handleRequest(Model model) {
        model.addAttribute("msg", "user request received");
        log.info(model.toString());
        return "my-page";
    }

    @RequestMapping("{id}")
    public String handleRequestById(@PathVariable("id") String id, Model model) {
        model.addAttribute("msg", "user request received for the id: " + id);
        log.info(model.toString());
        return "my-page";
    }

    @ModelAttribute("time")
    public LocalDateTime getRequestTime() {
        return LocalDateTime.now();
    }

    @ModelAttribute("visits")
    public int getRequestCount() {
        return ++counter;
    }

    @ModelAttribute("querier")
    public void populateQuerierInfo(@RequestParam(value = "querier", required = false) String querier, Model model) {
        model.addAttribute("querier", querier == null ? "quest" : querier);
    }
}
