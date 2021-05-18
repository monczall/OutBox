USE outbox;
INSERT INTO `packages` (`ID`, `typeID`, `userID`, `courierID`, `user_infoID`, `email`, `package_number`, `time_of_planned_delivery`, `additional_comment`) VALUES
(1, 1, 1, NULL, 2, 'klient.krakow@gmail.com', '180420211755/123456', '10:30 - 15:30', 'Brak'),
(2, 2, 2, NULL, 1, 'klient.rzeszow@gmail.com', '180420211755/321654', '10:30 - 15:30', 'Nie ma'),
(3, 3, 3, NULL, 1, 'klient.rzeszow@gmail.com', '180420211755/435621', '17:30 - 21:00', 'Prosze dzwonic do drzwi');
