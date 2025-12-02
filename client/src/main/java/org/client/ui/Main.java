package org.client.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Scanner;
import org.client.service.FactoryServiceClientVirtualCRM;
import org.client.service.ServiceClientVirtualCRM;
import org.client.service.dto.VirtualLeadDTO;

public class Main {

  private static final Scanner scanner =
      new Scanner(System.in);
  private static final ServiceClientVirtualCRM service = FactoryServiceClientVirtualCRM.createService();

  private static final ObjectMapper jsonMapper = new ObjectMapper()
      .enable(SerializationFeature.INDENT_OUTPUT);

  public static void main(String[] args) {

    while (true) {
      System.out.println("\n=== VirtualCRM Menu ===");
      System.out.println("1. findLeads");
      System.out.println("2. findLeadsByDate");
      System.out.println("3. add");
      System.out.println("4. delete");
      System.out.println("0. Exit");
      System.out.print("Choix: ");
      String choice = scanner.nextLine();

      System.out.println();
      try {
        switch (choice) {
          case "1":
            handleFindLeads();
            break;
          case "2":
            handleFindLeadsByDate();
            break;
          case "3":
            handleAddLead();
            break;
          case "4":
            handleDeleteLead();
            break;
          case "0":
            System.exit(0);
            break;
          default:
            System.out.println("Invalid choice.");
            break;
        }
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private static void handleFindLeads() throws Exception {
    System.out.print("Low revenue (0 by default): ");
    double low = parseDouble(scanner.nextLine(), 0);
    System.out.print("High revenue (0 by default): ");
    double high = parseDouble(scanner.nextLine(), 0);
    String state;
    do {
        System.out.print("State (empty by default) (no numbers allowed) : ");
        state = scanner.nextLine();
    }while(state.matches(".*\\d.*"));

    VirtualLeadDTO[] leads = service.findLeads(low, high, state);
    System.out.println();
    printLeads(leads);
  }

  private static void handleFindLeadsByDate() throws Exception {
    String start;
    do{
        System.out.print("Start date (dd/MM/yyyy): ");
        start = scanner.nextLine();
    }while(!isValidDate(start));

    String end;
    do{
        System.out.print("End date (dd/MM/yyyy): ");
        end = scanner.nextLine();
    }while(!isValidDate(end));

    VirtualLeadDTO[] leads = service.findLeadsByDate(start, end);
    System.out.println();
    printLeads(leads);
  }

  private static void handleAddLead() throws Exception {
    VirtualLeadDTO lead = promptLead();
    service.addLead(lead);
    System.out.println("\nLead added !");
  }

  private static void handleDeleteLead() throws Exception {
    System.out.print("ID of the lead to delete: ");
    String id = scanner.nextLine();
    if (id.isEmpty()) {
      System.out.println("invalid ID !");
      return;
    }

    // Using today's date juste to fill a value
    Date today = new Date();

    // Completed DTO mandatory for the rest service
    VirtualLeadDTO lead = new VirtualLeadDTO(
        id,
        "",
        "",
        0,
        "",
        today,
        "",
        "",
        "",
        "",
        "",
        ""
    );

    try {
      service.deleteLead(lead);
      System.out.println("\nLead deleted !");
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }


  private static VirtualLeadDTO promptLead() throws Exception {
      boolean bonFormat = true;
    System.out.print("ID (-1 by default): ");
    String id = scanner.nextLine();
    if (id.isEmpty()) {
      id = "-1";
    }

    String firstName;
    do {
        System.out.print("First name (empty by default) (no numbers allowed) : ");
        firstName = scanner.nextLine();
        if (firstName.isEmpty()) {
            firstName = "";
        }
    }while(firstName.matches(".*\\d.*"));

    String lastName;
    do {
        System.out.print("Last name (empty by default) (no numbers allowed) : ");
        lastName = scanner.nextLine();
        if (lastName.isEmpty()) {
            lastName = "";
        }
    }while(lastName.matches(".*\\d.*"));

    System.out.print("Annual revenue (0 by default): ");
    double annualRevenue = parseDouble(scanner.nextLine(), 0);

    String phone;
    do {
        System.out.print("Phone (empty by default) (only numbers allowed) : ");
        phone = scanner.nextLine();
        if (phone.isEmpty()) {
            phone = "";
        }
    }while(phone.matches(".*[a-zA-Z].*"));

    System.out.print("Company (empty by default): ");
    String company = scanner.nextLine();
    if (company.isEmpty()) {
      company = "";
    }

    System.out.print("Street (empty by default): ");
    String street = scanner.nextLine();
    if (street.isEmpty()) {
      street = "";
    }

    String postalCode;
    do {
        System.out.print("Postal code (empty by default) (only numbers allowed) : ");
        postalCode = scanner.nextLine();
        if (postalCode.isEmpty()) {
            postalCode = "";
        }
    }while(postalCode.matches(".*[a-zA-Z].*"));

    String city;
    do {
        System.out.print("City (empty by default) (no numbers allowed) : ");
        city = scanner.nextLine();
        if (city.isEmpty()) {
            city = "";
        }
    }while(city.matches(".*\\d.*"));

    String state;
    do {
        System.out.print("State (empty by default) (no numbers allowed) : ");
        state = scanner.nextLine();
        if (state.isEmpty()) {
            state = "";
        }
    }while(state.matches(".*\\d.*"));

    String country;
    do {
        System.out.print("Country (empty by default) (no numbers allowed) : ");
        country = scanner.nextLine();
        if (country.isEmpty()) {
            country = "";
        }
    }while(country.matches(".*\\d.*"));

    String dateInput;
    Date creationDate = null;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    do {
        System.out.print("Creation date [dd/MM/yyyy] (today if empty): ");
        dateInput = scanner.nextLine();
        if (dateInput.isEmpty()) {
            creationDate = new Date();
        }
    }while(!isValidDate(dateInput));

    if (!dateInput.isEmpty()) {
      creationDate = sdf.parse(dateInput);
    }

    return new VirtualLeadDTO(
        id, firstName, lastName, annualRevenue, phone,
        creationDate, company, state, street, postalCode, city, country,
        null
    );
  }

  private static double parseDouble(String input, double defaultValue) {
    if (input.isEmpty()) {
      return defaultValue;
    }
    try {
      return Double.parseDouble(input);
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

    public static boolean isValidDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if(dateStr.isEmpty()){
            return true;
        }
        try {
            LocalDate date = LocalDate.parse(dateStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

  private static void printLeads(VirtualLeadDTO[] leads) {
    if (leads == null || leads.length == 0) {
      System.out.println("\nNo lead found.");
      return;
    }

    // Configure an ObjectMapper to display the date in the corresponding format
    ObjectMapper displayMapper = new ObjectMapper()
        .enable(SerializationFeature.INDENT_OUTPUT)
        .setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));

    for (VirtualLeadDTO lead : leads) {
      try {
        String prettyJson = displayMapper.writeValueAsString(lead);
        System.out.println("\n" + prettyJson);
      } catch (Exception e) {
        // fallback to toString() if JSON fail
        System.out.println(lead);
      }
    }
  }

}
