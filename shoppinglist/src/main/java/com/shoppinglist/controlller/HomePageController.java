package com.shoppinglist.controlller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomePageController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "index";
    }

    @GetMapping(value = "/health", produces = "text/json")
    public ResponseEntity<String> health() {
        return new ResponseEntity<String>("I am healthy", HttpStatus.OK);
    }

}
