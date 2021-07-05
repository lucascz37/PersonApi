package br.com.lucas.personapi.controller;

import br.com.lucas.personapi.dto.request.PersonDTO;
import br.com.lucas.personapi.exception.PersonNotFoundException;
import br.com.lucas.personapi.mapper.PersonMapper;
import br.com.lucas.personapi.model.Person;
import br.com.lucas.personapi.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService service;
    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    public PersonController(PersonService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Person>> getPeople(){
        return new ResponseEntity<>(service.getPeople(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Long id) throws PersonNotFoundException {
        service.verifyIfExists(id);
        return new ResponseEntity<>(service.getPerson(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Person> updatePerson(@RequestBody @Valid PersonDTO personDTO) throws PersonNotFoundException {
        service.verifyIfExists(personDTO.getId());
        Person p = service.savePerson(personMapper.toModel(personDTO));
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody @Valid PersonDTO personDTO){
        return new ResponseEntity<>(service.savePerson(personMapper.toModel(personDTO)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePerson(@PathVariable Long id) throws PersonNotFoundException {
        service.verifyIfExists(id);
        service.deletePerson(id);
    }
}
