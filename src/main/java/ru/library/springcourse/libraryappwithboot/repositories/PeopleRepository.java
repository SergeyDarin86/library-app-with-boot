package ru.library.springcourse.libraryappwithboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.library.springcourse.libraryappwithboot.models.Person;


import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person,Integer> {
    Optional <Person> findPersonByFullName(String fullName);

    @Query("select p from Book b join Person p on p.personId = b.person.personId where b.bookId = :bookId")
    Optional<Person> findPersonByBookId(@Param("bookId") int bookId);

}
