# Console-Based Package Delivery Tracker for Local Stores

## Project Overview
This project implements a simple, console-driven system designed to help local stores manage and track packages. It demonstrates fundamental object-oriented programming (OOP) principles, in-memory data structures (Java Collections), basic input/output operations, and robust error handling within a command-line interface. All package, store, and delivery person data is stored in memory and is not persistent; it will be lost upon program termination.

This system is built to showcase core application development skills, focusing on modularity, data integrity, and a user-friendly console experience, aligning with standard project review criteria.

## Features Implemented

The Package Delivery Tracker provides the following functionalities:

* **Store Management:**
    * Add new local stores (with a unique ID, Name, and Address).
    * Display a list of all registered stores.
* **Delivery Person Management:**
    * Register new delivery personnel (with a unique ID, Name, and Contact Information).
    * Display a list of all registered delivery persons.
* **Package Management:**
    * Add new packages (requiring a unique Tracking ID, Sender, Recipient, Destination Address, and an Associated Store).
    * Assign an existing package to a registered delivery person.
    * Update the status of a package (e.g., `PENDING`, `IN_TRANSIT`, `DELIVERED`, `CANCELED`).
    * View all packages in the system.
    * Filter and display packages based on their current status.
    * View all packages assigned to a specific delivery person.
    * View all packages originating from a specific store.
    * Search for a package using its unique Tracking ID.
* **Robustness & Validation:**
    * Comprehensive input validation for all user inputs (e.g., non-empty fields, valid contact numbers, appropriate Tracking ID formats).
    * Error handling for invalid operations (e.g., assigning an already assigned package, updating status to an illogical state, non-existent IDs).
    * Prevention of duplicate Store IDs, Delivery Person IDs, and Package Tracking IDs.

## Project Structure

The project adheres to a clear, modular structure for better organization and separation of concerns:
