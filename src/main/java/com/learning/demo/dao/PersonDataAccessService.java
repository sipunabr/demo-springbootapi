package com.learning.demo.dao;

import com.learning.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("personDao")
public class PersonDataAccessService implements PersonDao {


    private static List<Person> DB = new ArrayList<>();

    @Override
    public Optional<Person> getPersonById(UUID id) {
        return DB.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();

    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> personMayBe = getPersonById(id);
        if (!personMayBe.isPresent()) {
            return 0;
        }
        DB.remove(personMayBe.get());
        return 1;

    }

    @Override
    public int updatePersonById(UUID id, Person updatePerson) {
        return getPersonById(id)
                .map(person ->{
                    int indexOfPersonToUpdate = DB.indexOf(person);
                    if (indexOfPersonToUpdate >=0){
                        DB.set(indexOfPersonToUpdate,new Person(id, updatePerson.getName()));
                        return 1;
                    }
                    return 0;
                }).orElse(0);
    }

    @Override
    public List<Person> selectAllPerson() {
        return DB;
    }

    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 1;
    }


}
