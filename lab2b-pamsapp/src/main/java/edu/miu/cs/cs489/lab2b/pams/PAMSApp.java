package edu.miu.cs.cs489.lab2b.pams;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.miu.cs.cs489.lab2b.pams.model.Patient;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class PAMSApp {
    public static void main(String[] args) throws IOException {
        List<Patient> patients = loadPatients();

        // Sort by age DESC (oldest first = dob ASC)
        patients.sort(Comparator.comparing(Patient::getDob));

        ObjectMapper mapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mapper.writeValue(new File("patients.json"), patients);

        System.out.println("✅ Patients exported to patients.json");
        // Print to console
        System.out.println("=== Patients (sorted by age DESC) ===");
        System.out.println(mapper.writeValueAsString(patients));

        System.out.println("✅ Patients exported to patients.json");
    }

    private static List<Patient> loadPatients() {
        return Arrays.asList(
                new Patient(1, "Daniel", "Agar", "(641) 123-0009", "dagar@m.as",
                        "1 N Street", LocalDate.of(1987, 1, 19)),
                new Patient(2, "Ana", "Smith", null, "amsith@te.edu",
                        null, LocalDate.of(1948, 12, 5)),
                new Patient(3, "Marcus", "Garvey", "(123) 292-0018", null,
                        "4 East Ave", LocalDate.of(2001, 9, 18)),
                new Patient(4, "Jeff", "Goldbloom", "(999) 165-1192", "jgold@es.co.za",
                        null, LocalDate.of(1995, 2, 28)),
                new Patient(5, "Mary", "Washington", null, null,
                        "30 W Burlington", LocalDate.of(1932, 5, 31))
        );
    }
}
