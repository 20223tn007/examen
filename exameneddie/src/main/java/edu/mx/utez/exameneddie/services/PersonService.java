package edu.mx.utez.exameneddie.services;

import edu.mx.utez.exameneddie.config.ApiResponse;
import edu.mx.utez.exameneddie.models.Person;
import edu.mx.utez.exameneddie.models.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse> findAll() {
        return new ResponseEntity<>(new ApiResponse(repository.findAll(), HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> save(Person person) {
        if (person == null) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Invalid Person data"), HttpStatus.BAD_REQUEST);
        }

        // Generate CURP based on provided information
        String curp = generateCurp(person);

        if (curp == null) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "Failed to generate CURP"), HttpStatus.BAD_REQUEST);
        }

        person.setCurp(curp);


        Optional<Person> foundPerson = repository.findByCurp(person.getCurp());
        if (foundPerson.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST, true, "RecordAlreadyExist"), HttpStatus.BAD_REQUEST);
        }

        person = repository.saveAndFlush(person);

        return new ResponseEntity<>(new ApiResponse(person, HttpStatus.OK), HttpStatus.OK);
    }

    private String generateCurp(Person person) {
        if (person == null || person.getSurname() == null || person.getName() == null || person.getLastname() == null
                || person.getBirthdate() == null || person.getSexo() == null || person.getEdonac() == null) {
            return null;
        }

        String firstLetterSurname = person.getSurname().substring(0, 1);
        String firstVowelSurname = getFirstVowel(person.getSurname());

        String firstLetterLastname = person.getLastname() != null ? person.getLastname().substring(0, 1) : "X";


        String firstLetterName = person.getName().substring(0, 1);

        String lastTwoDigitsYear = String.format("%02d", person.getBirthdate().getYear() % 100);

        String birthMonth = String.format("%02d", person.getBirthdate().getMonthValue());
        String birthDay = String.format("%02d", person.getBirthdate().getDayOfMonth());


        String gender = person.getSexo().substring(0, 1);


        String placeOfBirth = person.getEdonac().substring(0, 2);


        String firstConsonantLastname = getFirstConsonantInternal(person.getLastname());
        String firstConsonantSurname = getFirstConsonantInternal(person.getSurname());
        String firstConsonantName = getFirstConsonantInternal(person.getName());


        String randomNumbers = String.format("%02d", (int) (Math.random() * 100));


        return firstLetterSurname.toUpperCase() + firstVowelSurname.toUpperCase() +
                firstLetterLastname.toUpperCase() + firstLetterName.toUpperCase() +
                lastTwoDigitsYear + birthMonth + birthDay +
                gender.toUpperCase() + placeOfBirth.toUpperCase() +
                firstConsonantLastname.toUpperCase() + firstConsonantSurname.toUpperCase() +
                firstConsonantName.toUpperCase() + randomNumbers;
    }

    private String getFirstVowel(String surname) {
        String vowels = "AEIOUaeiou";

        for (char c : surname.toCharArray()) {
            if (vowels.indexOf(c) != -1) {
                return String.valueOf(c).toUpperCase();
            }
        }

        return null;
    }

    private String getFirstConsonantInternal(String lastname) {
        if (lastname == null || lastname.isEmpty()) {
            return "";
        }

        lastname = lastname.toLowerCase();


        int firstVowelIndex = -1;
        for (int i = 0; i < lastname.length(); i++) {
            char currentChar = lastname.charAt(i);
            if (currentChar == 'a' || currentChar == 'e' || currentChar == 'i' || currentChar == 'o' || currentChar == 'u') {
                firstVowelIndex = i;
                break;
            }
        }


        if (firstVowelIndex != -1 && firstVowelIndex < lastname.length() - 1) {
            char firstConsonantInternal = lastname.charAt(firstVowelIndex + 1);
            return String.valueOf(firstConsonantInternal).toUpperCase();
        } else {

            return "";
        }
    }
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> delete(Long id) {
        Optional<Person> personOptional = repository.findById(id);
        if (personOptional.isPresent()) {
            repository.deleteById(id);
            return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, false, "borrado"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND, true, "id no registrado"), HttpStatus.NOT_FOUND);
        }
    }
}
