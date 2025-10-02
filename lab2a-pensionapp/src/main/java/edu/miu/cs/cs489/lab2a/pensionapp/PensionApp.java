package edu.miu.cs.cs489.lab2a.pensionapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.miu.cs.cs489.lab2a.pensionapp.model.Employee;
import edu.miu.cs.cs489.lab2a.pensionapp.model.PensionPlan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class PensionApp {

    public static void main(String[] args) throws JsonProcessingException {
        List<Employee> employees = loadSampleData();

        printAllEmployees(employees);
        printQuarterlyUpcomingEnrollees(employees);
    }

    private static List<Employee> loadSampleData() {
        return Arrays.asList(
            new Employee(1, "Daniel", "Agar", LocalDate.parse("2023-01-17"),
                    new BigDecimal("105945.50"), new PensionPlan("EX1089", null, new BigDecimal("100.00"))),
            new Employee(2, "Benard", "Shaw", LocalDate.parse("2022-09-03"),
                    new BigDecimal("197750.00"), null),
            new Employee(3, "Carly", "Agar", LocalDate.parse("2014-05-16"),
                    new BigDecimal("842000.75"), new PensionPlan("SM2307", LocalDate.parse("2017-05-17"), new BigDecimal("1555.50"))),
            new Employee(4, "Wesley", "Schneider", LocalDate.parse("2023-07-21"),
                    new BigDecimal("74500.00"), null),
            new Employee(5, "Anna", "Wiltord", LocalDate.parse("2023-03-15"),
                    new BigDecimal("85750.00"), null),
            new Employee(6, "Yosef", "Tesfalem", LocalDate.parse("2024-10-31"),
                    new BigDecimal("100000.00"), null)
        );
    }

    private static void printAllEmployees(List<Employee> employees) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        List<Employee> sorted = employees.stream()
                .sorted(Comparator.comparing(Employee::getYearlySalary).reversed()
                        .thenComparing(Employee::getLastName))
                .collect(Collectors.toList());

        System.out.println("=== All Employees ===");
        System.out.println(mapper.writeValueAsString(sorted));
    }

    private static void printQuarterlyUpcomingEnrollees(List<Employee> employees) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        LocalDate today = LocalDate.now();
        LocalDate startNextQuarter = today.plusMonths(3 - (today.getMonthValue() - 1) % 3).withDayOfMonth(1);
        LocalDate endNextQuarter = startNextQuarter.plusMonths(3).minusDays(1);

        List<Employee> enrollees = employees.stream()
                .filter(e -> e.getPensionPlan() == null)
                .filter(e -> {
                    LocalDate eligibleDate = e.getEmploymentDate().plusYears(3);
                    return !eligibleDate.isBefore(startNextQuarter) && !eligibleDate.isAfter(endNextQuarter);
                })
                .sorted(Comparator.comparing(Employee::getEmploymentDate).reversed())
                .collect(Collectors.toList());

        System.out.println("=== Quarterly Upcoming Enrollees ===");
        System.out.println(mapper.writeValueAsString(enrollees));
    }
}
