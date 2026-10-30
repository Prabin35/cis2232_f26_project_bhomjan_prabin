# Valorant Performance Tracker #

CIS 2232 semester project

## Development Team ##

Business Client (BA): Brian <br/>
Lead Developer: Prabin Bhomjan <br/>
Project Manager / QA: Hassan <br/>

## Description ##

The Valorant Performance Tracker is a web application that helps Valorant players keep track of their performance after each match. Valorant is a competitive first-person shooter where players look at statistics such as kills, deaths, assists, damage, Average Combat Score (ACS), and KAST to see how well they performed. Although the game provides statistics after a match, this application allows players to record their own match history and easily compare their performance over time.

The user enters information from a completed Valorant match, including the player's name, the agent played, the map, kills, deaths, assists, total damage dealt, rounds played, and KAST rounds. The application saves this information and uses several of the fields to calculate additional performance statistics: the KDA ratio, a simplified Average Combat Score, and the KAST percentage.

The goal of the application is to give Valorant players a simple way to record their match statistics and view calculated performance information. In the future, the stored match information could also be used to compare results between different agents and maps.

## Project Information ##

Project Title: Valorant Performance Tracker <br/>
Server Port: 8080 (http://localhost:8080) <br/>
Application folder: Project/Valorant_Performance_Tracker_App <br/>
Database script: Project/Valorant_Performance_Tracker_App/src/main/resources/db/mysql/createDatabase.sql <br/>
Java package: ca.hccis.valorant <br/>
Main entity: ValorantMatch (table valorant_match), based on the Match class from Assignment 1 <br/>

## Color ##

Main Color: Valorant Red (#FF4655) <br/>
Secondary Color: To be determined

## Required Fields ##

| Name | Data Type | Description |
|:---|:---:|:---|
| Player Name | String | The player's username or Riot ID |
| Agent | String | The Valorant agent used during the match |
| Map | String | The map where the match was played |
| Kills | Integer | Total number of kills earned during the match |
| Deaths | Integer | Total number of deaths during the match |
| Assists | Integer | Total number of assists earned during the match |
| Damage Dealt | Integer | Total amount of damage dealt during the match |
| Rounds Played | Integer | Total number of rounds played during the match |
| KAST Rounds | Integer | Number of rounds where the player had a kill, assist, survived, or was traded |

Note: the database also carries an auto-generated `id` (primary key) for each record.

## Calculation ##

The application calculates three statistics from the entered fields. They are derived from the stored data and are not stored in the database.

**KDA Ratio**

`KDA = (Kills + Assists) / Deaths`

If a player has zero deaths, the application must handle this separately to prevent division by zero.

**Simplified Average Combat Score (ACS)**

`ACS = ((Kills x 150) + (Assists x 50) + Damage Dealt) / Rounds Played`

This is a simplified calculation for this project and is not intended to reproduce Valorant's official ACS calculation.

**KAST Percentage**

`KAST % = (KAST Rounds / Rounds Played) x 100`

Example: 20 kills, 8 assists, 14 deaths, 3,200 damage, 21 rounds, 16 KAST rounds gives KDA = 2.00, ACS = 314.29, KAST = 76.19%.

## Report Details ##

### Sprint 2 - Player Performance Report ###

For Sprint 2, I am adding a Player Performance Report to the application. The report will allow users to search for a player by entering their name and view the matches recorded for that player.

The report will use JDBC to get the match information from the database. It will also save the report results to a text file so that the user has a copy of the report.

### Report Input ###

The user will enter the following information:

| Name | Data Type | Description |
|:---|:---:|:---|
| Player Name | String | Name of the player whose match records will be displayed |

The player name is required and can have up to 100 characters. The search will ignore uppercase and lowercase differences but must match the full player name.

### Report Output ###

After the user enters the player name, the application will display the matching match records.

The following information will be displayed:

| Name | Description |
|:---|:---|
| Agent | Agent used by the player |
| Map | Map played |
| Kills | Total kills in the match |
| Deaths | Total deaths in the match |
| Assists | Total assists in the match |
| KDA | Calculated KDA ratio |
| ACS | Calculated Average Combat Score |
| KAST % | Calculated KAST percentage |

The report will use the same calculations that are already specified in the Calculation section.

I am also including a bar chart to show the simplified ACS for each match. This will make it easier to compare the player's performance between matches.

If the player does not have any matching records, the application will display a message instead of showing an empty report.

### JDBC ###

For this sprint, I am using JDBC instead of Spring Data JPA to get the report information from the database.

The `PlayerReportDAO` class will handle the database query using JDBC and a PreparedStatement. It will search the `valorant_match` table using the player name entered by the user.

The matching records will be stored in `PlayerReportRow` objects and sent to the controller to display the results.

### Controller and Views ###

The `PlayerReportController` will handle the report input and output.

The report will have two HTML pages:

- `player-input.html` - Allows the user to enter the player name.
- `player-results.html` - Displays the matching records, calculated statistics, and chart.

The controller will get the player name from the input form, call the DAO to get the matching records, and send the results to the HTML page.

### File I/O ###

Along with displaying the report on the screen, the application will also save the results to a text file.

The file will be saved in the following folder:

`C:\cis2232\`

The filename will contain the report name and the current date and time.

Example:

`ValorantPlayerReport20261030153000.txt`

The text file will contain the player's name, date and time the report was generated, and the match statistics.

The `PlayerReportFile` class will handle writing the report to the text file.

If no matching records are found, the application will not create a file.

### Testing ###

After completing the report, I will test the following:

1. Search for a player who exists in the database.
2. Check if the correct match records are displayed.
3. Check if the KDA, ACS, and KAST calculations are correct.
4. Check if the bar chart displays the ACS values.
5. Check if the report is saved as a text file in `C:\cis2232\`.
6. Search for a player who does not exist.
7. Check if the application handles an empty player name.

Once the report is working correctly, I will assign Work Item 5 to my BA for testing.

The BA will check if the report meets the requirements and update the GitHub issue with the testing results. If any problems are found, I will make the necessary changes before the issue is closed.