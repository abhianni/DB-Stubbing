CREATE DATABASE wiremock;
use wiremock;
CREATE TABLE `stubs` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`uuid` varchar(45) NOT NULL,
`stubMappingName` varchar(200) DEFAULT NULL,
`priority` int(11) DEFAULT '5',
`scenarioName` varchar(45) DEFAULT NULL,
`request` longtext,
`response` longtext,
`createdOn` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updatedOn` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
`team` varchar(100) NOT NULL DEFAULT 'Others',
`urlIdentifierType` varchar(45) NOT NULL DEFAULT 'url',
`url` varchar(200) NOT NULL,
`createRequest` longtext NOT NULL,
`createResponse` longtext NOT NULL,
`active` int(11) DEFAULT '1',
`host` varchar(200) DEFAULT 'null',
PRIMARY KEY (`id`,`uuid`),
UNIQUE KEY `uuid_UNIQUE` (`uuid`),
UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=latin1