USE outbox;
INSERT INTO `users` (`ID`, `user_infoID`, `areaID`, `email`, `password`, `role`) VALUES
(1, 1, NULL, 'klient.rzeszow@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Klient'),
(2, 2, NULL, 'klient.krakow@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Klient'),
(3, 3, NULL, 'klient.kolbuszowa@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Klient'),
(4, 4, 2, 'k.rzeszow@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Kurier'),
(5, 5, 2, 'k.rzeszow2@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Kurier'),
(6, 6, 3, 'k.kolbuszowa@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Kurier'),
(7, 7, 5, 'k.krakow@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Kurier'),
(8, 8, 4, 'ko.podkarpacie@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Kurier Miedzyoddzialowy'),
(9, 9, 6, 'ko.maloposkie@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Kurier Miedzyoddzialowy'),
(10, 10, 2, 'm.rzeszow@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Menadzer'),
(11, 11, 3, 'm.kolbuszowa@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Menadzer'),
(12, 12, 4, 'm.podkarpacie@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Menadzer'),
(13, 13, 5, 'm.krakow@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Menadzer'),
(14, 14, 6, 'm.malopolskie@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Menadzer'),
(15, 15, NULL, 'admin@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Administrator');



