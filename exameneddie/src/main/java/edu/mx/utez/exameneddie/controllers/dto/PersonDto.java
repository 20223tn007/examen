package edu.mx.utez.exameneddie.controllers.dto;

import edu.mx.utez.exameneddie.models.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Setter
@Getter
public class PersonDto {
    private Long id;
    private String name;
    private String surname;
    private String lastname;
    private LocalDate birthdate;
    private String sexo;
    private String edonac;
    private String curp;

    public Person toEntity() {
        Person person = new Person();
        person.setId(this.id);
        person.setName(this.name);
        person.setSurname(this.surname);
        person.setLastname(this.lastname);
        person.setBirthdate(this.birthdate);
        person.setSexo(this.sexo);
        person.setEdonac(this.edonac);
        person.setCurp(this.curp);

        return person;
    }
}
