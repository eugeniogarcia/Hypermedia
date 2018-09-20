package hello;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class GreetingController {

    private static final String TEMPLATE = "Hello, %s!";

    @RequestMapping("/greeting")
    public HttpEntity<Greeting> greeting(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name) {

        Greeting greeting = new Greeting(String.format(TEMPLATE, name));
        //The most interesting part of the method implementation is how you create the link pointing to the controller method 
        //and how you add it to the representation model. 
        //Both linkTo(…) and methodOn(…) are static methods on ControllerLinkBuilder that allow you to fake a method 
        //invocation on the controller. The LinkBuilder returned will have inspected the controller method’s mapping annotation 
        //to build up exactly the URI the method is mapped to.
        //The call to withSelfRel() creates a Link instance that you add to the Greeting representation model.
        //If you call withRel("xxx") instead of naming the link as 

		//{"content":"Hello, eugenio!","_links":{"xxx":{"href":"http://localhost:8081/greeting?name=eugenio"}}}
		//{"content":"Hello, eugenio!","_links":{"self":{"href":"http://localhost:8081/greeting?name=eugenio"}}}
        
        greeting.add(linkTo(methodOn(GreetingController.class).greeting(name)).withSelfRel());

        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }
}
