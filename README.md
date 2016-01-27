# postingservice

A simple anonymous, location-based posting service based on Java EE 7 (Wildfly) and MongoDB. The given Java project is just a working title...

It is intended to license this software under the terms of the GNU General Public License, version 3.
The file contaning this license (license.txt) is available [here](https://github.com/sebikopp/postingservice/blob/master/ownjodel/src/main/resources/props/license.txt). 

Java dependencies needed at compile time are available at mavenCentral. As of now only the [MongoDB Java driver (currently used version: 3.0.4)](http://search.maven.org/#artifactdetails|org.mongodb|mongodb-driver|3.0.4|jar) is concerned. (Hyperlink points to maven page prividing needed information (e.g. hyperlinks to source file jars))

These dependencies are auto-downloaded and included at compile time (see [pom.xml](https://github.com/sebikopp/postingservice/blob/master/ownjodel/pom.xml)). They are licensed under the Apache License 2.0 ([see also](https://github.com/sebikopp/postingservice/blob/master/ownjodel/src/main/resources/props/3rd-party-licenses.txt)).

For the web view, JQuery 2.1.4 is used as a JavaScript library. It is contained in this repo and licensed under the MIT license ([see also](https://github.com/sebikopp/postingservice/blob/master/ownjodel/src/main/resources/props/3rd-party-licenses.txt))..

(C) Sebastian Kopp, 2015.
