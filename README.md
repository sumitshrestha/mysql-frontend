# Front End For MySQL

A Java Swing-based graphical front-end for MySQL database management. The application provides a complete GUI for interacting with MySQL, reducing reliance on raw SQL commands, and serves as a tool for database administrators (DBAs).

**Author:** Sumit Shrestha  
**Started:** July 2007  
**IDE:** NetBeans  
**Build Tool:** Apache Ant

---

## Features

- **Database Browser** — Tree-view navigation of databases and tables
- **SQL Terminal** — Interactive console for executing raw SQL queries
- **Table Management** — Create tables with columns, data types, primary keys, foreign keys, and triggers
- **Data Manipulation** — Insert, view, and manage table data through GUI forms
- **View Management** — Create and manage MySQL views
- **Stored Procedures** — Visual procedure builder with compile and execute support
- **Transaction Management** — Full transaction control with commit, rollback, and savepoints
- **Dynamic Driver Management** — Add and remove JDBC drivers at runtime without restarting
- **Document Editor** — Built-in SQL editor with find and replace functionality
- **Result Set Viewer** — Tabular display of query results
- **User Management** — View connected user information
- **Status Bar** — Real-time application status display

---

## Architecture

The project follows a layered architecture with a clear separation between the backend engine and the Swing UI.

```
Main.java  →  splash.java  →  Factory.java
                                    ↓
                             BackEnd (Engine)
                                    ↓
                             MainFrame (UI)
```

### Package Structure

```
org.edu.gces.s2005.projects.frontendformysql
├── Main.java                        Entry point
├── splash.java                      Splash screen and system initialization
├── Factory.java                     Factory for creating engine and initializing system
│
├── domain/
│   ├── BackEnd/                     Core backend engine
│   │   ├── BackEnd.java             Facade for all backend operations
│   │   ├── JDBC_ConnectionManager   Legacy JDBC connection management
│   │   ├── JDBC2_ConnectionManager  Portable JDBC connection management
│   │   ├── DatabaseReader           Reads database metadata via JDBC
│   │   ├── QueryGenerator           Builds SQL query strings
│   │   ├── QueryExecuter            Executes SQL queries
│   │   ├── ProcedureGenerator       Generates CREATE PROCEDURE SQL
│   │   ├── ProcedureCompiler        Compiles stored procedures
│   │   ├── ProcedureExecuter        Executes stored procedures
│   │   ├── TransactionManager       Manages commits, rollbacks, savepoints
│   │   ├── UserManager              Retrieves connected user information
│   │   └── System/                  System initialization and driver info
│   │
│   ├── BackEndComponent/            Reusable backend modules
│   │   ├── DriverModule/            Dynamic JDBC driver loading and management
│   │   ├── DataStructures/          Shared data structures (e.g., StatusBar)
│   │   ├── Editor/                  Document editing utilities
│   │   ├── IO/                      File I/O manager and utilities
│   │   └── XMLutil/                 XML parsing utilities
│   │
│   ├── BackEndData/                 Domain model (data objects)
│   │   ├── Database, Table, View
│   │   ├── TableAttribute, Tuple
│   │   ├── Foreign_key, TableTrigger
│   │   ├── Procedure, Transaction
│   │   ├── Driver, User
│   │
│   └── BackEndInterfaces/           Event/change interfaces used by UI→BackEnd communication
│
└── ui/
    ├── parent/                      Main application windows and panels
    │   ├── MainFrame                Main application window with toolbar
    │   ├── DatabaseTree             Tree-view panel for database navigation
    │   ├── SQLTerminal              Interactive SQL query terminal
    │   ├── TablePanel               Table data viewer and editor
    │   ├── ProcedureBuilderPanel    Visual stored procedure builder
    │   └── DriverManagerDialog      Dialog for managing JDBC drivers
    │
    ├── Forms/                       Modal dialog forms
    │   ├── Create_New_Table         Table creation wizard
    │   ├── createNewDatabaseForm    Database creation dialog
    │   ├── createNewViewForm        View creation dialog
    │   ├── addForeignKeyForm        Foreign key definition form
    │   ├── addTriggerForm           Trigger definition form
    │   ├── InsertValuesInTable      Row insertion form
    │   ├── InsertTableCollForm      Column insertion form
    │   ├── SavePointInputForm       Named savepoint dialog
    │   ├── UserDataInputDialog      User data input dialog
    │   ├── DocumentFindForm         Find in document dialog
    │   └── DocumentReplaceForm      Find & replace dialog
    │
    └── Components/                  Reusable UI components
        ├── AboutDialog              About dialog
        ├── ResultSetViewer          Tabular query result display
        ├── DataTableModel           Table model for Swing JTable
        ├── StatusBar                Application status bar
        ├── TransactionHistoryWindow Transaction history viewer
        ├── TransactionStateViewer   Current transaction state viewer
        ├── DriverInfoPanel          Driver information display
        ├── ButtonTabComponent       Closeable tab buttons
        ├── DocumentEditor/          Document editor components
        └── NewDriverWizard/         Step-by-step wizard for adding drivers
```

---

## Prerequisites

| Requirement | Details |
|---|---|
| Java SE JDK | JDK 5 or later (Java 1.5+) |
| MySQL Server | Any version with JDBC support |
| MySQL Connector/J | JDBC driver JAR for MySQL |
| Apache Ant | 1.6.5 or later (for building) |
| NetBeans IDE | Optional — for development |

---

## Building

The project uses Apache Ant with a standard NetBeans-generated build script.

```bash
# Compile the project
ant compile

# Build a distributable JAR
ant jar

# Clean build outputs
ant clean
```

The compiled classes are placed in `build/classes/` and the runnable JAR is produced in `dist/`.

---

## Running

```bash
java -jar dist/FrontEndForMySQL.jar
```

A splash screen is displayed while the system initializes the backend engine and loads driver configuration. If the splash screen cannot be displayed (e.g., headless environment), the application starts directly without it.

### First-Time Setup

1. Launch the application.
2. Open the **Driver Manager** and add the MySQL Connector/J JAR (`mysql-connector-java-x.x.x.jar`).
3. Select the driver and enter connection details (host, port, username, password).
4. Click **Connect** to establish a session.

---

## Key Design Patterns

- **Factory Pattern** — `Factory.java` centralizes creation of the backend engine and system initialization.
- **Facade Pattern** — `BackEnd.java` provides a single unified API for all backend operations (connect, query, transaction, procedure, user management).
- **Observer/Listener Interfaces** — `BackEndInterfaces/` defines change interfaces (e.g., `DatabaseTableChangeInterface`, `TransactionListener`) used by UI components to communicate changes back to the engine.
- **Dynamic Class Loading** — `JarClassLoader.java` loads JDBC driver JARs at runtime, enabling driver management without modifying the classpath.

---

## Configuration

Driver metadata is stored as XML under `lib/Driver/DriverInfo.xml` and managed by `DriverXMLDatabase.java`. The system reads this file at startup via `InitialDriverInfo.xml` in the `BackEnd/System/` package.

Application properties are stored in `src/properties.properties`.


