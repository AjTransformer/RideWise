package com.airtribe.ridewise;

import com.airtribe.exception.NoDriverAvailableException;
import com.airtribe.model.*;
import com.airtribe.service.DriverService;
import com.airtribe.service.RideService;
import com.airtribe.service.RiderService;
import com.airtribe.strategy.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Choose default strategies here. You can change at runtime if you wish.
        RideMatchingStrategy matchingStrategy = new NearestDriverStrategy();
        FareStrategy defaultFare = new DefaultFareStrategy();
        FareStrategy fareStrategy = new PeakHourFareStrategy(defaultFare);

        RiderService riderService = new RiderService();
        DriverService driverService = new DriverService();
        RideService rideService = new RideService(matchingStrategy, fareStrategy, driverService, riderService);

        Scanner sc = new Scanner(System.in);

        while (true) {
            printMenu();
            System.out.print("Choose option: ");
            String option = sc.nextLine().trim();
            try {
                switch (option) {
                    case "1":
                        addRider(sc, riderService);
                        break;
                    case "2":
                        addDriver(sc, driverService);
                        break;
                    case "3":
                        viewAvailableDrivers(driverService);
                        break;
                    case "4":
                        requestRide(sc, riderService, rideService);
                        break;
                    case "5":
                        completeRide(sc, rideService);
                        break;
                    case "6":
                        viewRides(rideService);
                        break;
                    case "7":
                        System.out.println("Exiting. Goodbye.");
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (NoDriverAvailableException ex) {
                System.out.println("Operation failed: " + ex.getMessage());
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("==== RideWise ====");
        System.out.println("1) Add Rider");
        System.out.println("2) Add Driver");
        System.out.println("3) View Available Drivers");
        System.out.println("4) Request Ride");
        System.out.println("5) Complete Ride");
        System.out.println("6) View Rides");
        System.out.println("7) Exit");
    }

    private static void addRider(Scanner sc, RiderService riderService) {
        System.out.print("Rider name: ");
        String name = sc.nextLine().trim();
        double x = readDouble(sc, "Location X: ");
        double y = readDouble(sc, "Location Y: ");
        Rider r = riderService.registerRider(name, x, y);
        System.out.println("Registered: " + r);
    }

    private static void addDriver(Scanner sc, DriverService driverService) {
        System.out.print("Driver name: ");
        String name = sc.nextLine().trim();
        double x = readDouble(sc, "Current Location X: ");
        double y = readDouble(sc, "Current Location Y: ");
        System.out.println("Vehicle types: 1) BIKE 2) AUTO 3) CAR");
        System.out.print("Choose vehicle type: ");
        String vt = sc.nextLine().trim();
        VehicleType type = VehicleType.CAR;
        switch (vt) {
            case "1": type = VehicleType.BIKE; break;
            case "2": type = VehicleType.AUTO; break;
            case "3": type = VehicleType.CAR; break;
            default:
                System.out.println("Invalid choice, defaulting to CAR.");
        }
        com.airtribe.model.Driver d = driverService.registerDriver(name, x, y, type);
        System.out.println("Registered: " + d);
    }

    private static void viewAvailableDrivers(DriverService driverService) {
        List<com.airtribe.model.Driver> list = driverService.listAvailableDrivers();
        if (list.isEmpty()) {
            System.out.println("No available drivers.");
            return;
        }
        System.out.println("Available drivers:");
        list.forEach(System.out::println);
    }

    private static void requestRide(Scanner sc, RiderService riderService, RideService rideService) throws NoDriverAvailableException {
        System.out.print("Rider ID: ");
        String riderId = sc.nextLine().trim();
        Rider rider = riderService.getRiderById(riderId);
        if (rider == null) {
            System.out.println("Rider not found.");
            return;
        }
        double distance = readDouble(sc, "Trip distance in km: ");
        var ride = rideService.requestRide(riderId, distance);
        System.out.println("Ride requested: " + ride);
        var receipt = rideService.getFareReceipt(ride.getId());
        System.out.println("Fare: " + receipt);
    }

    private static void completeRide(Scanner sc, RideService rideService) {
        System.out.print("Ride ID to complete: ");
        String rideId = sc.nextLine().trim();
        try {
            var ride = rideService.completeRide(rideId);
            System.out.println("Ride completed: " + ride);
            var receipt = rideService.getFareReceipt(rideId);
            System.out.println("Fare receipt: " + receipt);
        } catch (Exception ex) {
            System.out.println("Could not complete ride: " + ex.getMessage());
        }
    }

    private static void viewRides(RideService rideService) {
        var rides = rideService.listAllRides();
        if (rides.isEmpty()) {
            System.out.println("No rides yet.");
            return;
        }
        rides.forEach(System.out::println);
    }

    private static double readDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
