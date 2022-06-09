DROP TABLE players IF EXISTS cascade ;
CREATE TABLE players(
    id INT NOT NULL AUTO_INCREMENT,
    playerId INT NOT NULL UNIQUE,
    teamId INT NOT NULL,
    firstName VARCHAR(20),
    lastName VARCHAR (20),
    position VARCHAR (2),
    age INT,
    primary key (id)
);

DROP TABLE battingStats IF EXISTS cascade ;
CREATE TABLE battingStats(
    id INT NOT NULL AUTO_INCREMENT,
    battingStatsId INT NOT NULL UNIQUE,
    ab INT DEFAULT 0,
    pa INT DEFAULT 0,
    bb INT DEFAULT 0,
    singles INT DEFAULT 0,
    doubles INT DEFAULT 0,
    triples INT DEFAULT 0,
    hr INT DEFAULT 0,
    hbp INT DEFAULT 0,
    primary key (id)
);

DROP TABLE pitchingStats IF EXISTS cascade ;
CREATE TABLE pitchingStats(
  id INT NOT NULL AUTO_INCREMENT,
  pitchingStatsId INT NOT NULL UNIQUE,
  ip INT DEFAULT 0,
  gs INT DEFAULT 0,
  sv INT DEFAULT 0,
  h INT DEFAULT 0,
  er INT DEFAULT 0,
  k INT DEFAULT 0,
  bb INT DEFAULT 0,
  w INT DEFAULT 0,
  l INT DEFAULT 0,
  primary key (id)
);

DROP TABLE seasons IF EXISTS cascade ;
CREATE TABLE seasons(
    id INT NOT NULL AUTO_INCREMENT,
    seasonId INT NOT NULL UNIQUE,
    playerId INT NOT NULL,
    battingStatsId INT,
    pitchingStatsId INT,
    year INT,
    gp INT DEFAULT 0,
    primary key (id)
);