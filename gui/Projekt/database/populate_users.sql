USE outbox;
INSERT INTO `users` (`ID`, `user_infoID`, `areaID`, `email`, `password`, `role`) VALUES
(1, 1, NULL, 'klient@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Klient'),
(2, 2, 1, 'kurier@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Kurier'),
(3, 3, 2, 'menadzer@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Menadżer'),
(4, 4, 3, 'administrator@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Administrator');
