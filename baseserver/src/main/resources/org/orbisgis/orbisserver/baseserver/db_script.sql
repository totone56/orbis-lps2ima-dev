-- Script of the initiation of the database.
DROP TABLE IF EXISTS session_table;
CREATE TABLE IF NOT EXISTS session_table (username VARCHAR(50), password VARCHAR(50), expirationTime LONG, poolSize INT);
INSERT INTO session_table VALUES ('admin', 'admin', 172800000, 10);
DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users (idUser INT(4),pseudo VARCHAR(255) UNIQUE,password VARCHAR(255),image VARCHAR(255),superAdmin	BOOLEAN,CONSTRAINT userPK PRIMARY KEY (idUser));
DROP TABLE IF EXISTS groups;
CREATE TABLE IF NOT EXISTS groups (idGroup INT(4),nom VARCHAR(255) UNIQUE,persistance BOOLEAN,CONSTRAINT groupPK PRIMARY KEY (idGroup));
DROP TABLE IF EXISTS linkUserGroup;
CREATE TABLE IF NOT EXISTS linkUserGroup (admin BOOLEAN,idUser INT(4),idGroup INT(4),CONSTRAINT idGroupFK FOREIGN KEY (idGroup) REFERENCES groups(idGroup),CONSTRAINT idUserFK FOREIGN KEY (idUser) REFERENCES users(idUser));
DROP TABLE IF EXISTS script;
CREATE TABLE IF NOT EXISTS script (nom VARCHAR(255),auteur VARCHAR(255),dateScript DATE,CONSTRAINT scriptPK PRIMARY KEY (nom));
DROP TABLE IF EXISTS bundle;
CREATE TABLE IF NOT EXISTS bundle (nom VARCHAR(255),auteur VARCHAR(255),dateScript DATE,url VARCHAR(255),estCharge BOOLEAN,CONSTRAINT bundlePK PRIMARY KEY (nom));
DROP TABLE IF EXISTS linkScriptGroup;
CREATE TABLE IF NOT EXISTS linkScriptGroup (nomScript VARCHAR(255),nomBundle VARCHAR(255),CONSTRAINT nomScriptLinkGroupFK FOREIGN KEY (nomScript) REFERENCES script(nom),CONSTRAINT nomBundleLinkScriptFK FOREIGN KEY (nomBundle) REFERENCES bundle(nom));
DROP TABLE IF EXISTS linkGroupBundle;
CREATE TABLE IF NOT EXISTS linkGroupBundle (idGroup INT(4),nomBundle VARCHAR(255),CONSTRAINT idGroupLinkBundleFK FOREIGN KEY (idGroup) REFERENCES groups(idGroup),CONSTRAINT nomBundleLinkGroupFK FOREIGN KEY (nomBundle) REFERENCES bundle(nom));