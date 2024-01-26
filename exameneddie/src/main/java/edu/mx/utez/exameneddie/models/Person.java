package edu.mx.utez.exameneddie.models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "person")
@NoArgsConstructor
@Getter
@Setter
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String name;
    @Column(length = 50, nullable = false)
    private String surname;
    @Column(length = 50)
    private String lastname;
    @Column(columnDefinition = "DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate birthdate;
    @Column(length = 50)
    private String sexo;
    @Column(length = 50)
    private String edonac;
    @Column(length = 18, nullable = false, unique = true)
    private String curp;

    public Person(Long id, String name, String surname, String lastname, LocalDate birthdate, String sexo, String edonac, String curp) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.sexo = sexo;
        this.edonac = edonac;
        this.curp = curp;
    }

    public String getFirstVowel(String surnameInitial) {
        if (surnameInitial == null || surnameInitial.isEmpty()) {
            return null; // O podrías devolver una cadena vacía, dependiendo de tus requisitos
        }

        // Convierte la inicial del apellido a minúsculas para facilitar la comparación
        char initialLowercase = Character.toLowerCase(surnameInitial.charAt(0));

        // Verifica si la inicial del apellido es una vocal y devuelve la primera vocal interna
        if (initialLowercase == 'a' || initialLowercase == 'e' || initialLowercase == 'i' || initialLowercase == 'o' || initialLowercase == 'u') {
            return String.valueOf(initialLowercase);
        } else {
            // Si la inicial no es una vocal, puedes personalizar esta lógica para encontrar la primera vocal interna.
            // En este ejemplo, simplemente devolvemos una cadena vacía.
            return "";
        }
    }

}
