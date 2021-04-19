-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 19 Kwi 2021, 19:15
-- Wersja serwera: 10.4.17-MariaDB
-- Wersja PHP: 8.0.1

CREATE DATABASE outbox;
USE outbox;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `outbox`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `areas`
--

CREATE TABLE `areas` (
  `ID` int(11) NOT NULL,
  `name` varchar(128) NOT NULL,
  `voivodeship` varchar(128) NOT NULL,
  `city` varchar(128) NOT NULL,
  `department_street_and_number` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `areas`
--

INSERT INTO `areas` (`ID`, `name`, `voivodeship`, `city`, `department_street_and_number`) VALUES
(1, 'Rzeszow-Zalesie-1', 'Podkarpackie', 'Rzeszow', 'Sassanki 21/37'),
(2, 'Rzeszow-Nowe-Miasto-1', 'Podkarpackie', 'Rzeszow', 'Podwislocze 56'),
(3, 'Krakow-Prokocim', 'Malopolskie', 'Krakow', 'Rakus 15');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `packages`
--

CREATE TABLE `packages` (
  `ID` int(11) NOT NULL,
  `typeID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `user_infoID` int(11) NOT NULL,
  `email` varchar(128) NOT NULL,
  `package_number` varchar(64) NOT NULL,
  `time_of_planned_delivery` varchar(64) NOT NULL,
  `additional_comment` varchar(512) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `packages`
--

INSERT INTO `packages` (`ID`, `typeID`, `userID`, `user_infoID`, `email`, `package_number`, `time_of_planned_delivery`, `additional_comment`) VALUES
(1, 1, 1, 5, 'piotr.lewandowski@gmail.com', '180420211755/123456', '?', 'Brak'),
(2, 2, 1, 5, 'piotr.lewandowski@gmail.com', '180420211755/321654', '?', 'Nie ma'),
(3, 3, 1, 5, 'piotr.lewandowski@gmail.com', '180420211755/435621', '?', 'Prosze dzwonic do drzwi');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `package_history`
--

CREATE TABLE `package_history` (
  `ID` int(11) NOT NULL,
  `packageID` int(11) NOT NULL,
  `status` enum('Zarejestrowana','Odebrana Od Klienta','W Transporcie','Przekazana Do Doręczenia','Dostarczona','Nieobecność Odbiorcy','Ponowna Próba Doręczenia','Do Odebrania W Oddziale','Zwrot Do Nadawcy','Zwrócona Do Nadawcy') NOT NULL,
  `date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `package_history`
--

INSERT INTO `package_history` (`ID`, `packageID`, `status`, `date`) VALUES
(1, 1, 'Zarejestrowana', '2021-04-19 19:14:00'),
(2, 2, 'Odebrana Od Klienta', '2021-04-20 19:26:00'),
(3, 3, 'W Transporcie', '2021-04-19 19:17:00');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `package_type`
--

CREATE TABLE `package_type` (
  `ID` int(11) NOT NULL,
  `size_name` varchar(16) NOT NULL,
  `size` varchar(16) NOT NULL,
  `weight` varchar(16) NOT NULL,
  `price` varchar(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `package_type`
--

INSERT INTO `package_type` (`ID`, `size_name`, `size`, `weight`, `price`) VALUES
(1, 'mała', '8x38x64', '25', '11,99'),
(2, 'średnia', '19x38x64', '25', '12,99'),
(3, 'duża', '41x38x64', '25', '14,99');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `ID` int(11) NOT NULL,
  `user_infoID` int(11) NOT NULL,
  `areaID` int(11) DEFAULT NULL,
  `email` varchar(128) NOT NULL,
  `password` varchar(256) NOT NULL,
  `role` enum('Klient','Kurier','Kurier Międzyoddziałowy','Menadżer','Administrator') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `users`
--

INSERT INTO `users` (`ID`, `user_infoID`, `areaID`, `email`, `password`, `role`) VALUES
(1, 1, NULL, 'klient@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Klient'),
(2, 2, 1, 'kurier@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Kurier'),
(3, 3, 2, 'menadzer@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Menadżer'),
(4, 4, 3, 'administrator@gmail.com', '9e38e8d688743e0d07d669a1fcbcd35b', 'Administrator');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user_infos`
--

CREATE TABLE `user_infos` (
  `ID` int(11) NOT NULL,
  `name` varchar(128) NOT NULL,
  `surname` varchar(128) NOT NULL,
  `phone_number` varchar(32) NOT NULL,
  `street_and_number` varchar(256) NOT NULL,
  `city` varchar(128) NOT NULL,
  `voivodeship` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Zrzut danych tabeli `user_infos`
--

INSERT INTO `user_infos` (`ID`, `name`, `surname`, `phone_number`, `street_and_number`, `city`, `voivodeship`) VALUES
(1, 'Jan', 'Kowalski', '789456321', 'Mickiewicza 90', 'Rzeszow', 'Podkarpackie'),
(2, 'Adam', 'Adamiak', '432198765', 'Lwowska 123', 'Rzeszow', 'Podkarpackie'),
(3, 'Jakub', 'Szmul', '456789321', 'Jana Pawla 21', 'Krakow', 'Malopolskie'),
(4, 'Karol', 'Wilk', '231564879', 'Warola Kilka 32', 'Lezajsk', 'Podkarpackie'),
(5, 'Piotr', 'Lewandowski', '678091546', 'Sassanki 57', 'Rzeszow', 'Podkarpackie');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `areas`
--
ALTER TABLE `areas`
  ADD PRIMARY KEY (`ID`);

--
-- Indeksy dla tabeli `packages`
--
ALTER TABLE `packages`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `typeID` (`typeID`,`userID`,`user_infoID`),
  ADD KEY `user_infoID` (`user_infoID`),
  ADD KEY `userID` (`userID`);

--
-- Indeksy dla tabeli `package_history`
--
ALTER TABLE `package_history`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `packageID` (`packageID`);

--
-- Indeksy dla tabeli `package_type`
--
ALTER TABLE `package_type`
  ADD PRIMARY KEY (`ID`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `user_infoID` (`user_infoID`),
  ADD KEY `areaID` (`areaID`);

--
-- Indeksy dla tabeli `user_infos`
--
ALTER TABLE `user_infos`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT dla zrzuconych tabel
--

--
-- AUTO_INCREMENT dla tabeli `areas`
--
ALTER TABLE `areas`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT dla tabeli `packages`
--
ALTER TABLE `packages`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT dla tabeli `package_history`
--
ALTER TABLE `package_history`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT dla tabeli `package_type`
--
ALTER TABLE `package_type`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT dla tabeli `users`
--
ALTER TABLE `users`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT dla tabeli `user_infos`
--
ALTER TABLE `user_infos`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `packages`
--
ALTER TABLE `packages`
  ADD CONSTRAINT `packages_ibfk_1` FOREIGN KEY (`user_infoID`) REFERENCES `user_infos` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `packages_ibfk_2` FOREIGN KEY (`typeID`) REFERENCES `package_type` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `packages_ibfk_3` FOREIGN KEY (`userID`) REFERENCES `users` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ograniczenia dla tabeli `package_history`
--
ALTER TABLE `package_history`
  ADD CONSTRAINT `package_history_ibfk_1` FOREIGN KEY (`packageID`) REFERENCES `packages` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ograniczenia dla tabeli `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`user_infoID`) REFERENCES `user_infos` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `users_ibfk_2` FOREIGN KEY (`areaID`) REFERENCES `areas` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
