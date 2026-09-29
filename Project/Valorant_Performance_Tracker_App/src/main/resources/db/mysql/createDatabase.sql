# Valorant Performance Tracker - Sprint 1 database setup
# Creates a fresh localhost database and the table that holds Valorant player
# performance records. Run this script the first time to set up your database.

# For hccis.ca version of the database (update the name to match your account)
# DROP DATABASE IF EXISTS <your_hccis_database>;
# CREATE DATABASE <your_hccis_database>;
# use <your_hccis_database>;

# For localhost (matches spring.datasource.url in application.properties)
DROP DATABASE IF EXISTS cis2232_valorant_performance_tracker;
CREATE DATABASE cis2232_valorant_performance_tracker;
use cis2232_valorant_performance_tracker;

-- ------------------------------------------------------------------------------
-- One table holding the nine fields specified by the BA plus an id primary key.
-- KDA ratio, simplified ACS and KAST percentage are calculated from these
-- fields by the application, so they are intentionally NOT stored here.
-- ------------------------------------------------------------------------------

DROP TABLE IF EXISTS valorant_match;

CREATE TABLE valorant_match
(
    id            INT          NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
    player_name   VARCHAR(100) NOT NULL COMMENT 'Player username or Riot ID',
    agent         VARCHAR(50)  NOT NULL COMMENT 'Valorant agent used during the match',
    map           VARCHAR(50)  NOT NULL COMMENT 'Map where the match was played',
    kills         INT          NOT NULL DEFAULT 0 COMMENT 'Total kills during the match',
    deaths        INT          NOT NULL DEFAULT 0 COMMENT 'Total deaths during the match',
    assists       INT          NOT NULL DEFAULT 0 COMMENT 'Total assists during the match',
    damage_dealt  INT          NOT NULL DEFAULT 0 COMMENT 'Total damage dealt during the match',
    rounds_played INT          NOT NULL DEFAULT 1 COMMENT 'Total rounds played during the match',
    kast_rounds   INT          NOT NULL DEFAULT 0 COMMENT 'Rounds with a kill, assist, survival or trade',
    PRIMARY KEY (id),
    CONSTRAINT chk_valorant_match_counts
        CHECK (kills >= 0 AND deaths >= 0 AND assists >= 0 AND damage_dealt >= 0),
    CONSTRAINT chk_valorant_match_rounds
        CHECK (rounds_played >= 1 AND kast_rounds >= 0 AND kast_rounds <= rounds_played)
) COMMENT 'This table holds Valorant player performance records';

-- Sample data (the first row is the worked example from the BA document)
INSERT INTO valorant_match
(player_name, agent, map, kills, deaths, assists, damage_dealt, rounds_played, kast_rounds)
VALUES ('Prabin1', 'Jett',    'Ascent', 20, 14, 8, 3200, 21, 16),
       ('Bryan', 'Sage',    'Bind',   12, 15, 11, 2450, 24, 17),
       ('BJ', 'Phoenix', 'Haven',  18, 10, 5, 3050, 20, 15);
