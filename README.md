# News Channel Management System

## Overview
This is a **JavaFX-based News Channel Management System** that allows users to manage news programs, track TRP ratings, and analyze various statistics related to news programs and their anchors.

## Features
- **Add a News Program**: Enter program details including program name, anchor, month, TRP rating, and business profit.
- **Find Max Collection Month**: Identifies the month with the highest business profit.
- **Find the Busiest Anchor**: Determines which anchor is hosting the most programs.
- **Sort Programs by TRP**: Sorts the news programs in descending order based on TRP rating.
- **Find Program with Least TRP**: Identifies the program with the lowest TRP rating.

## Technologies Used
- Java
- JavaFX
- Collections Framework (ArrayList, HashMap, Comparator)

## Installation
1. **Clone the Repository:**
   ```sh
   git clone https://github.com/yourusername/NewsChannelApp.git
   cd NewsChannelApp
   ```
2. **Ensure JavaFX is Installed:**
   - Download JavaFX SDK from [GluonHQ](https://gluonhq.com/products/javafx/)
   - Extract it and set up environment variables.
3. **Compile and Run the Application:**
   ```sh
   javac --module-path /path/to/javafx/lib --add-modules javafx.controls NewsChannelApp.java
   java --module-path /path/to/javafx/lib --add-modules javafx.controls NewsChannelApp
   ```

## Usage
1. Launch the application.
2. Click **'Add Program'** to enter program details.
3. Click **'Max Collection Month'** to see the month with the highest revenue.
4. Click **'Busiest Anchor'** to find the most engaged anchor.
5. Click **'Sort by TRP'** to list programs in order of TRP.
6. Click **'Least TRP Program'** to identify the lowest-rated program.

## Code Structure
- **`Channel`** (Abstract Class): Represents a news channel program.
- **`NewsChannel`** (Subclass): Implements program details.
- **`NewsChannelApp`** (Main Class): Handles GUI interactions and program logic.
