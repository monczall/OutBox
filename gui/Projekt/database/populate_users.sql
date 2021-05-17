USE outbox;
INSERT INTO `users` (`ID`, `user_infoID`, `areaID`, `email`, `password`, `role`) VALUES
(1, 1, NULL, 'klient@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Klient'),
(2, 2, 1, 'k.rzeszow1@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Kurier'),
(3, 3, 1, 'k.rzeszow2@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Kurier'),
(4, 4, 4, 'k.krakow@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Kurier'),
(5, 5, 3, 'ko.podkarpacie@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Kurier Międzyoddziałowy'),
(6, 6, 5, 'ko.maloposkie@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Kurier Międzyoddziałowy'),
(7, 7, 1, 'm.rzeszow@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Menadżer'),
(8, 8, 4, 'm.krakow@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Menadżer'),
(9, 9, NULL, 'admin@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Administrator');



