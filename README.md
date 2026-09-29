# Valorant Performance Tracker #

CIS 2232 semester project

## Development Team ##

Business Client (BA):  Brian	<br/>
Lead Developer:  Prabin Bhomjan	<br/>
Project Manager / QA:  Hassan	<br/>

## Description ##

The Valorant Performance Tracker is a web application that helps Valorant players keep track of their performance after each match. Valorant is a competitive first-person shooter where players look at statistics such as kills, deaths, assists, damage, Average Combat Score (ACS), and KAST to see how well they performed. Although the game provides statistics after a match, this application allows players to record their own match history and easily compare their performance over time.

The user enters information from a completed Valorant match, including the player's name, the agent played, the map, kills, deaths, assists, total damage dealt, rounds played, and KAST rounds. The application saves this information and uses several of the fields to calculate additional performance statistics: the KDA ratio, a simplified Average Combat Score, and the KAST percentage.

The goal of the application is to give Valorant players a simple way to record their match statistics and view calculated performance information. In the future, the stored match information could also be used to compare results between different agents and maps.

## Project Information ##

Project Title:  Valorant Performance Tracker	<br/>
Server Port:  8080 (http://localhost:8080)	<br/>
Application folder:  Project/Valorant_Performance_Tracker_App	<br/>
Database script:  Project/Valorant_Performance_Tracker_App/src/main/resources/db/mysql/createDatabase.sql	<br/>
Java package:  ca.hccis.valorant	<br/>
Main entity:  ValorantMatch (table valorant_match), based on the Match class from Assignment 1	<br/>

## Color ##

Main Color:  Valorant Red (#FF4655)	<br/>
Secondary Color:  To be determined

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

To be determined in a future sprint
