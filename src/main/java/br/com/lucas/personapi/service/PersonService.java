package br.com.lucas.personapi.service;

import br.com.lucas.personapi.exception.PersonNotFoundException;
import br.com.lucas.personapi.model.Person;
import br.com.lucas.personapi.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository repository;

    public Person savePerson(Person p){
        return repository.save(p);
    }

    public Person getPerson(Long id){
        return repository.findById(id).orElse(null);
    }

    public void deletePerson(Long id){
        repository.deleteById(id);
    }

    public List<Person> getPeople(){
        return repository.findAll();
    }

    public void verifyIfExists(Long id) throws PersonNotFoundException {
        if(this.getPerson(id) == null){
            throw new PersonNotFoundException(id);
        }
    }
}
