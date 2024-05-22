package ru.library.springcourse.libraryappwithboot.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

public class PersonDto {

    @NotEmpty(message = "FullName should not be empty")
    @Size(min = 8, max = 100, message = "The FullName should be between 8 and 100 characters")
    @Pattern(regexp = "[А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+", message = "The FullName should have the format: Surname Name Patronymic")
    private String fullName;

    @Min(value = 1900, message = "Year of Birthday should be more than 1900")
    @NotNull(message = "Year of Birthday should not be empty")
    private Integer yearOfBirthday;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getYearOfBirthday() {
        return yearOfBirthday;
    }

    public void setYearOfBirthday(Integer yearOfBirthday) {
        this.yearOfBirthday = yearOfBirthday;
    }
}
