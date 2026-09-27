# Project: Valorant Performance Tracker

---

## Development Team

Business Client: Brian

Lead Developer: Prabin

Quality Control: Hassan

---

## Description

This project will allow Valorant players to track and analyze their match performance statistics. Valorant is a competitive first-person shooter game where players can review statistics such as kills, deaths, assists, damage dealt, and round performance. While the game provides post-match statistics, this application will allow players to maintain their own match history and compare performance trends over time.

The application will store information about each completed match, including the player's name, selected agent, map played, kills, deaths, assists, total damage dealt, rounds played, and KAST rounds. Using this information, the system will calculate additional performance metrics that help players evaluate their overall contribution during a match.

The application will calculate a KDA Ratio, a simplified Average Combat Score (ACS), and a KAST Percentage. These calculated values provide a quick summary of player performance and can be used to compare results across multiple matches. The application may also be extended in the future to compare performance by agent, map, or other game-related statistics.

---

## Color

Main Color: #FF4655 (Valorant Red)

---

## Required Fields

This will be a list of fields and their datatype (class design format).

* id: int // primary key
* playerName: String
* agent: String
* map: String
* kills: int
* deaths: int
* assists: int
* damageDealt: int
* roundsPlayed: int
* kastRounds: int
* kdaRatio: double // calculated
* averageCombatScore: double // calculated
* kastPercentage: double // calculated

---

## Calculation

Once the user enters all match statistics, the application will calculate three performance metrics.

### KDA Ratio

The KDA Ratio measures overall contribution based on kills, assists, and deaths.

KDA Ratio = (Kills + Assists) / Deaths

Example:

20 kills + 8 assists = 28

28 / 14 deaths = 2.00 KDA

Note: If deaths equal 0, the application must handle division by zero appropriately.

### Simplified Average Combat Score (ACS)

A simplified ACS formula will be used for this project.

ACS = ((Kills × 150) + (Assists × 50) + Damage Dealt) / Rounds Played

Example:

((20 × 150) + (8 × 50) + 3200) / 21

= (3000 + 400 + 3200) / 21

= 314.29 ACS

### KAST Percentage

KAST represents rounds where the player earned a kill, assist, survived, or was traded.

KAST % = (KAST Rounds / Rounds Played) × 100

Example:

16 / 21 × 100

= 76.19%

---

## Report Details

