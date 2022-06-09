DROP TABLE teams IF EXISTS ;
CREATE TABLE teams(
    id INT NOT NULL AUTO_INCREMENT,
    teamId INT NOT NULL UNIQUE,
    leagueId INT NOT NULL,
    name VARCHAR(30),
    city VARCHAR(30),
    PRIMARY KEY(id)
    );