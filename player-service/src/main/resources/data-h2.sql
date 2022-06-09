insert into players (playerId, teamId, firstName, lastName, position, age) VALUES (1, 1, 'Mike', 'Trout', 'CF', 30);
insert into players (playerId, teamId, firstName, lastName, position, age) VALUES (2, 2, 'Justin', 'Verlander', 'SP', 35);
insert into players (playerId, teamId, firstName, lastName, position, age) VALUES (3, 1, 'John', 'Doe', '3B', 21);
insert into players (playerId, teamId, firstName, lastName, position, age) VALUES (4, 2, 'Mark', 'Smith', 'C', 40);

insert into battingStats (battingStatsId, ab, pa, bb, singles, doubles, triples, hr, hbp) VALUES (1, 10, 15, 4, 2, 1, 0, 1, 1);
insert into battingStats (battingStatsId, ab, pa, bb, singles, doubles, triples, hr, hbp) VALUES (2, 10, 17, 2, 2, 1, 0, 1, 1);
insert into battingStats (battingStatsId, ab, pa, bb, singles, doubles, triples, hr, hbp) VALUES (3, 14, 15, 0, 2, 1, 0, 1, 1);
insert into battingStats (battingStatsId, ab, pa, bb, singles, doubles, triples, hr, hbp) VALUES (4, 20, 21, 0, 6, 2, 0, 1, 1);
insert into battingStats (battingStatsId, ab, pa, bb, singles, doubles, triples, hr, hbp) VALUES (5, 5, 6, 1, 2, 1, 0, 0, 0);
insert into battingStats (battingStatsId, ab, pa, bb, singles, doubles, triples, hr, hbp) VALUES (6, 15, 18, 3, 4, 3, 0, 3, 0);

insert into pitchingStats (pitchingStatsId, ip, gs, sv, h, er, k, bb, w, l) VALUES (1, 27, 5, 0, 25, 9, 30, 5, 3, 1);
insert into pitchingStats (pitchingStatsId, ip, gs, sv, h, er, k, bb, w, l) VALUES (2, 27, 5, 1, 28, 10, 35, 5, 3, 2);
insert into pitchingStats (pitchingStatsId, ip, gs, sv, h, er, k, bb, w, l) VALUES (3, 27, 5, 2, 23, 11, 20, 5, 2, 3);
insert into pitchingStats (pitchingStatsId, ip, gs, sv, h, er, k, bb, w, l) VALUES (4, 27, 5, 1, 35, 20, 17, 5, 1, 4);
insert into pitchingStats (pitchingStatsId, ip, gs, sv, h, er, k, bb, w, l) VALUES (5, 27, 5, 3, 30, 15, 30, 5, 1, 3);
insert into pitchingStats (pitchingStatsId, ip, gs, sv, h, er, k, bb, w, l) VALUES (6, 35, 5, 5, 10, 3, 40, 5, 5, 0);

insert into seasons (seasonId, playerId, year, gp, battingStatsId, pitchingStatsId) VALUES (1, 1, 2019, 10, 1, 1);
insert into seasons (seasonId, playerId, year, gp, battingStatsId, pitchingStatsId) VALUES (2, 1, 2019, 10, 2, 2);
insert into seasons (seasonId, playerId, year, gp, battingStatsId, pitchingStatsId) VALUES (3, 1, 2019, 10, 3, 3);
insert into seasons (seasonId, playerId, year, gp, battingStatsId, pitchingStatsId) VALUES (4, 2, 2019, 10, 4, 4);
insert into seasons (seasonId, playerId, year, gp, battingStatsId, pitchingStatsId) VALUES (5, 2, 2019, 10, 5, 5);
insert into seasons (seasonId, playerId, year, gp, battingStatsId, pitchingStatsId) VALUES (6, 2, 2019, 10, 6, 6);
insert into seasons (seasonId, playerId, year, gp, battingStatsId, pitchingStatsId) VALUES (7, 3, 2019, 10, 1, 1);
insert into seasons (seasonId, playerId, year, gp, battingStatsId, pitchingStatsId) VALUES (8, 3, 2019, 10, 2, 2);
insert into seasons (seasonId, playerId, year, gp, battingStatsId, pitchingStatsId) VALUES (9, 3, 2019, 10, 3, 3);
insert into seasons (seasonId, playerId, year, gp, battingStatsId, pitchingStatsId) VALUES (10, 4, 2019, 10, 4, 4);
insert into seasons (seasonId, playerId, year, gp, battingStatsId, pitchingStatsId) VALUES (11, 4, 2019, 10, 5, 5);
insert into seasons (seasonId, playerId, year, gp, battingStatsId, pitchingStatsId) VALUES (12, 4, 2019, 10, 6, 6);