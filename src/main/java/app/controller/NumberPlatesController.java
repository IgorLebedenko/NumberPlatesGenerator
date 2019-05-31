package app.controller;

import app.model.NumberPlate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/number")
public class NumberPlatesController {
    @Autowired
    private NumberPlate numberPlate;


    @GetMapping("/random")
    public String random() {
        return numberPlate.getRandom();
    }

    @GetMapping("/next")
    public String next() {
        return numberPlate.getNext();
    }
}