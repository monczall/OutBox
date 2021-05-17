USE outbox;
INSERT INTO `packages` (`ID`, `typeID`, `userID`, `courierID`, `user_infoID`, `email`, `package_number`, `time_of_planned_delivery`, `additional_comment`) VALUES
(1, 1, 1, 0, 5, 'piotr.lewandowski@gmail.com', '180420211755/123456', '10:30 - 15:30', 'Brak'),
(2, 2, 1, 0, 5, 'piotr.lewandowski@gmail.com', '180420211755/321654', '10:30 - 15:30', 'Nie ma'),
(3, 3, 1, 0, 5, 'piotr.lewandowski@gmail.com', '180420211755/435621', '17:30 - 21:00', 'Prosze dzwonic do drzwi');
