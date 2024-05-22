package ru.library.springcourse.libraryappwithboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.library.springcourse.libraryappwithboot.models.PersonForDto;

@Repository
public interface PeopleForDtoRepository extends JpaRepository<PersonForDto,Integer> {
}
