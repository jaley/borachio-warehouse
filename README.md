Android, Dependency Injection and Mock Testing
==============================================

At [TouchType](http://www.touchtype-online.com) we've worked quite hard to set up a unit testing environment for Android that we're happy with, using [RoboGuice](http://code.google.com/p/roboguice) dependency injection and [Borachio](http://www.borachio.com) mock testing. [Paul Butcher](http://www.paulbutcher.com) has blogged about some of this work, and you can check out those blogs as a good starting point if you'd like a quick taster. Here you will find a worked example of using these tools to develop and test a really simple Android application.

This example is a thoroughly pointless and unnecessary user interface for Martin Fowler's Warehouse example in the [Mocks Aren't Stubs](http://martinfowler.com/articles/mocksArentStubs.html) blog post. If you're not familiar with Mock testing, that blog is a great place to learn about it. We're using the [Borachio](http://www.borachio.com) Mock testing framework, written by Paul Butcher specifically for android. This framework is written in Scala, meaning we write unit tests in Scala - but don't worry, we use it to test Java code anyway, it's easy! :-)


Notes on Building
-----------------

The project has a selection of tags, which are numbered to go along with the tutorial. Each of these /should/ just build, with one less-than-ideal piece of house work. I've had to check in the `local.properties` file, against all advice, because the process for building a Scala project on Android required it. This means, in order to build you'll need to change the paths inside it to match your installation. 

You need to install the version of [Scala](http://www.scala-lang.org) that matches the Borachio version in test/libs. At the time of writing this is 2.9.0.1 - adjust the `local.properties` as mentioned above to reflect your installation. Also change the ProGuard and SDK paths to wherever you've installed the Android SDK. Otherwise, this is pretty much a straight forward Ant-based Android project.



