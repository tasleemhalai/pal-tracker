package io.pivotal.pal.tracker;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

   public String word;
    @GetMapping("/")
    public String sayHello() {
        return word;
    }

    public WelcomeController( @Value("${WELCOME_MESSAGE}") String msg){

       this.word=msg;
    }
}