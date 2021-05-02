CREATE DATABASE IF NOT EXISTS outbox;
USE outbox;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Baza danych: `outbox`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `areas`
--

CREATE TABLE IF NOT EXISTS `areas` (
  `ID` int(11) NOT NULL,
  `name` varchar(128) NOT NULL,
  `voivodeship` varchar(128) NOT NULL,
  `city` varchar(128) NOT NULL,
  `department_street_and_number` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `packages`
--

CREATE TABLE IF NOT EXISTS `packages` (
  `ID` int(11) NOT NULL,
  `typeID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `user_infoID` int(11) NOT NULL,
  `email` varchar(128) NOT NULL,
  `package_number` varchar(64) NOT NULL,
  `time_of_planned_delivery` varchar(64) NOT NULL,
  `additional_comment` varchar(512) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `package_history`
--

CREATE TABLE IF NOT EXISTS `package_history` (
  `ID` int(11) NOT NULL,
  `packageID` int(11) NOT NULL,
  `status` enum('Zarejestrowana','Odebrana Od Klienta','W Transporcie','Przekazana Do Doręczenia','Dostarczona','Nieobecność Odbiorcy','Ponowna Próba Doręczenia','Do Odebrania W Oddziale','Zwrot Do Nadawcy','Zwrócona Do Nadawcy') NOT NULL,
  `date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `package_type`
--

CREATE TABLE IF NOT EXISTS `package_type` (
  `ID` int(11) NOT NULL,
  `size_name` varchar(16) NOT NULL,
  `size` varchar(16) NOT NULL,
  `weight` varchar(16) NOT NULL,
  `price` varchar(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `ID` int(11) NOT NULL,
  `user_infoID` int(11) NOT NULL,
  `areaID` int(11) DEFAULT NULL,
  `email` varchar(128) NOT NULL,
  `password` varchar(256) NOT NULL,
  `role` enum('Klient','Kurier','Kurier Międzyoddziałowy','Menadżer','Administrator') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user_infos`
--

CREATE TABLE IF NOT EXISTS `user_infos` (
  `ID` int(11) NOT NULL,
  `name` varchar(128) NOT NULL,
  `surname` varchar(128) NOT NULL,
  `phone_number` varchar(32) NOT NULL,
  `street_and_number` varchar(256) NOT NULL,
  `city` varchar(128) NOT NULL,
  `voivodeship` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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