# OrbisServer [![Build Status](https://travis-ci.org/orbisgis/orbisserver.png?branch=master)](https://travis-ci.org/orbisgis/orbisserver)
Orbisserver is Web Java OSGI application for data loading, process execution and data sharing, based on the Wisdom framework, OGC 
services and OrbisGIS libraries. The application includes a base built on the [Wisdom-Framework](http://wisdom-framework.org) library, a basic server, 
its api allowing the creation of different services and services implementing OGC standards.OrbisServer is part of the OrbisGIS platform

OrbisGIS is a java GIS application dedicated to research in GIScience.
OrbisGIS is developed by the GIS group of the DECIDE team of the
[Lab-STICC](http://www.lab-sticc.fr/) CNRS laboratory.

The GIS group of the DECIDE team is located at :

Laboratoire Lab-STICC – CNRS UMR 6285
Equipe DECIDE
UNIVERSITÉ DE BRETAGNE-SUD
Institut Universitaire de Technologie de Vannes
8, Rue Montaigne - BP 561 56017 Vannes Cedex

OrbisServer is distributed under LGPL 3 license.

Copyright (C) 2017 CNRS (Lab-STICC UMR CNRS 6285)

## Start the server

Clone the repository and go to the root directory `orbisserver`
and execute :
```
mvn clean install
```
Then go to the `core` directory and execute :

```
mvn wisdom:run
```
The server takes a few time to start.
Once started, you can open your browser at `http://localhost:8080`.


## Core module

The core module is the entry point of the application which include the Wisdom-Framework.

### Configuration

As the application is based on the Wisdom-Framework, you can find a complete 
documentation on the server configuration in the wisdom [documentation](http://wisdom-framework.org/reference/0.10.0/index.html).
The configuration file is located at `orbisserver/core/src/main/configuration/application.conf`

##### Ports
Use these properties to configure the ports :

`http.port = 8080`  The HTTP port<br />
`https.port = 9090`  The HTTPS port<br />
You can use the value `0` to use a random port.

##### H2 database
Use these properties to configure the ports:

`db.h2file.driver="org.h2.Driver"` The H2 driver class<br />
`db.h2file.url="jdbc:h2:./target/db/h2-it.db"` The database location<br />


## BaseServer API

The baseserver-api module contains all the interfaces and abstract classes for the creation of a basic web
server working on the core module. The created server will be able to manage all the services
based on this api.

####Creation of a service

A service is created by implementing the two classes `Service` and `ServiceFactory`.

The `Service` implementation is the core of the service, it contains the main mecanism.
The `ServiceFactory` implementation is aimed to create an register the service. For the service registering, 
the impementation should get the `BaseServer` class this way : 
``` java
@Requires private BaseServer baseServer;
```
and then, once the service started, register it in the start method, annotated `@Validate`, this way : 
``` java
@Validate
public void start(){
    baseServer.registerServiceFactory(this);
}
```

So, the application will load the bundle, start the `ServiceFactory` and register the `Service` making it available.

## BaseServer

Basic server based on the BaseServer API. It contains the main route, the administration system and
the service managing. The server offer the different operations from the services in a website dived in three
part : Data, Process and Share.
The Data part is use to manage the available data in the user workspace.
The Process part which contains the operations to process the data.
The Share part contains the operations to open data like cartographic representation.

## WpsService

Service implementing the BaseServer API and distributing WPS processes as operation. The WPS service mechanism is 
provided by the [OrbisWPS](https://github.com/orbisgis/orbiswps) library which includes a WPS 2.0 server, a bundle of basic 
scripts which are distributed as operations and a groovy api used for the script writing.

More information about the OrbisWPS can be found [here](https://github.com/orbisgis/orbiswps). 