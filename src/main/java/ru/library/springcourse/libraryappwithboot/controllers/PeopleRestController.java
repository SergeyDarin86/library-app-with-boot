package ru.library.springcourse.libraryappwithboot.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.library.springcourse.libraryappwithboot.models.Person;
import ru.library.springcourse.libraryappwithboot.services.BooksService;
import ru.library.springcourse.libraryappwithboot.services.PeopleService;
import ru.library.springcourse.libraryappwithboot.util.PersonErrorResponse;
import ru.library.springcourse.libraryappwithboot.util.PersonNotCreatedException;
import ru.library.springcourse.libraryappwithboot.util.PersonNotFoundException;
import ru.library.springcourse.libraryappwithboot.util.PersonValidator;

import java.util.List;

@RestController
@RequestMapping("/library")
public class PeopleRestController {

    private final PeopleService peopleService;

    private final BooksService booksService;

    private final PersonValidator personValidator;


    @Autowired
    public PeopleRestController(PeopleService peopleService, BooksService booksService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.booksService = booksService;
        this.personValidator = personValidator;
    }


    @GetMapping("/peopleNew/{id}")
    public Person show(@PathVariable("id") int id) {
        // работа с сервисом книг (если нам необходимы книги пользователя)
        booksService.findAllBooksByPerson(peopleService.showWithException(id));
        return peopleService.showWithException(id);
    }

    @GetMapping("/allPeople")
    public List<Person> people() {
       return peopleService.allPeople();
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid Person person
            , BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errorList = bindingResult.getFieldErrors();
            errorList.stream().forEach(fieldError -> errorMsg
                    .append(fieldError.getField())
                    .append(" - ").append(fieldError.getDefaultMessage())
                    .append(";"));

            throw new PersonNotCreatedException(errorMsg.toString());
        }


        peopleService.save(person);
        //отправляем Http-ответ со статусом 200 и пустым телом
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handlerException(PersonNotFoundException e){
        PersonErrorResponse response = new PersonErrorResponse(
                "Человек с таким id не найден",
                System.currentTimeMillis()
        );
        // В HTTP ответе будет тело ответа (response) и статус в заголовке http-ответа
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handlerException(PersonNotCreatedException e){
        PersonErrorResponse response = new PersonErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        // В HTTP ответе будет тело ответа (response) и статус в заголовке http-ответа
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
