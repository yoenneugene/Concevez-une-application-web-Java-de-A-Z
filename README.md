# Concevez-une-application-web-Java-de-A-Z
-- Créer la base de données
CREATE DATABASE IF NOT EXISTS `data` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Sélectionner la base de données pour l'utiliser
USE `data`;

-- Créer la table 'profil'
CREATE TABLE `profil` (
  `argent` float NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `adresse_mail` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Créer la table 'transaction'
CREATE TABLE `transaction` (
  `amount` float NOT NULL,
  `expediteur_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `adresse_mail_destinataire` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4t322d3225pin040uyklw566d` (`expediteur_id`),
  CONSTRAINT `FK4t322d3225pin040uyklw566d` FOREIGN KEY (`expediteur_id`) REFERENCES `profil` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `profil` (argent, id, adresse_mail, password) VALUES
(500.00, 1, 'john.doe@example.com', 'password123'),
(1500.00, 2, 'jane.doe@example.com', 'securePass456'),
(300.75, 3, 'alice.smith@example.com', 'alice2023'),
(1000.00, 4, 'bob.jones@example.com', 'b0bsecure'),
(750.25, 5, 'charles.brown@example.com', 'charlie123'),
(120.00, 6, 'diane.lee@example.com', 'diane456'),
(960.80, 7, 'eva.green@example.com', 'eva2023'),
(430.50, 8, 'frank.white@example.com', 'frank789');


INSERT INTO `transaction` (amount, expediteur_id, id, adresse_mail_destinataire, description) VALUES
(100.00, 1, 11, 'jane.doe@example.com', 'Payment for services'),
(200.00, 2, 12, 'alice.smith@example.com', 'Gift'),
(50.00, 3, 13, 'john.doe@example.com', 'Refund for product'),
(30.00, 4, 14, 'jane.doe@example.com', 'Dinner payment');
