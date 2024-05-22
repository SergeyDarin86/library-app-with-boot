package ru.library.springcourse.libraryappwithboot.util;

public class PersonNotCreatedException extends RuntimeException{

    // с помощью super мы передаем сообщение об ошибке в RuntimeException
    // и оно будет в этом исключении
    public PersonNotCreatedException(String errorMsg){
        super(errorMsg);
    }

}
