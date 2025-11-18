package org.client.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
    System.out.print("State (empty by default): ");
    String state = scanner.nextLine();

    VirtualLeadDTO[] leads = service.findLeads(low, high, state);
    System.out.println();
    printLeads(leads);
  }

  private static void handleFindLeadsByDate() throws Exception {
    System.out.print("Start date (dd-MM-yyyy): ");
    String start = scanner.nextLine();
    System.out.print("End date (dd-MM-yyyy): ");
    String end = scanner.nextLine();

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
    System.out.print("ID (-1 by default): ");
    String id = scanner.nextLine();
    if (id.isEmpty()) {
      id = "-1";
    }

    System.out.print("First name (empty by default): ");
    String firstName = scanner.nextLine();
    if (firstName.isEmpty()) {
      firstName = "";
    }

    System.out.print("Last name (empty by default): ");
    String lastName = scanner.nextLine();
    if (lastName.isEmpty()) {
      lastName = "";
    }

    System.out.print("Annual revenue (0 by default): ");
    double annualRevenue = parseDouble(scanner.nextLine(), 0);

    System.out.print("Phone (empty by default): ");
    String phone = scanner.nextLine();
    if (phone.isEmpty()) {
      phone = "";
    }

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

    System.out.print("Postal code (empty by default): ");
    String postalCode = scanner.nextLine();
    if (postalCode.isEmpty()) {
      postalCode = "";
    }

    System.out.print("City (empty by default): ");
    String city = scanner.nextLine();
    if (city.isEmpty()) {
      city = "";
    }

    System.out.print("State (empty by default): ");
    String state = scanner.nextLine();
    if (state.isEmpty()) {
      state = "";
    }

    System.out.print("Country (empty by default): ");
    String country = scanner.nextLine();
    if (country.isEmpty()) {
      country = "";
    }

    System.out.print("Creation date [dd/MM/yyyy] (today if empty): ");
    String dateInput = scanner.nextLine();
    Date creationDate;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    if (dateInput.isEmpty()) {
      creationDate = new Date();
    } else {
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
