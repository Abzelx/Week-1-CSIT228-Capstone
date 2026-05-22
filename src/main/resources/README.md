# Local Disaster Reporting System

## Group Members
- Ernest Joaquin Abella
- Earl Abzel Abesia
- Vence Delos Reyes
- Omar Macapaso
- John Kurt Realista

## Project Description

Many communities experience disasters such as floods, fires, and landslides but currently lack a
centralized system for reporting and tracking these events. People rely on social media or
word-of-mouth, which is unorganized, unverified, and slow.

This desktop application provides a local community disaster monitoring platform where users can
report incidents, view all reports in a structured list, filter and analyze data by type or
location, and generate simple summary reports.

## Proposed Features

- User login and registration with role-based access
- Report a new disaster incident (type, location, description, date)
- View all incidents in a sortable, filterable table
- Search incidents by keyword or location
- Update incident status (active, monitoring, resolved)
- Generate summary statistics per incident type
- Admin: manage users and verify or resolve reports

## Planned Technologies

- Java
- JavaFX
- JDBC
- SQLite

## Evaluation Criteria Mapping (Initial)

- OOP: Planned use of classes such as `User`, `Incident`, `DatabaseManager`, and `ReportGenerator`
- GUI: JavaFX with FXML views — `LoginView`, `MainView`, `ReportIncidentView`, `IncidentListView`
- UML: Use Case and Class Diagram included in `/diagrams/`
- Design Pattern: Singleton for `DatabaseManager` to maintain a single shared database connection
