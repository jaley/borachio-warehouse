Android, Dependency Injection and Mock Testing
==============================================

At [TouchType](http://www.touchtype-online.com) we've worked quite hard to set up a unit testing environment for Android that we're happy with, using [RoboGuice](http://code.google.com/p/roboguice) dependency injection and [Borachio](http://www.borachio.com) mock testing. [Paul Butcher](http://www.paulbutcher.com) has blogged about some of this work, and you can check out those blogs as a good starting point if you'd like a quick taster. Here you will find a worked example of using these tools to develop and test a really simple Android application.

This example is a thoroughly pointless and unnecessary user interface for Martin Fowler's Warehouse example in the [Mocks Aren't Stubs](http://martinfowler.com/articles/mocksArentStubs.html) blog post. If you're not familiar with Mock testing, that blog is a great place to learn about it. We're using the [Borachio](http://www.borachio.com) Mock testing framework, written by Paul Butcher specifically for android. This framework is written in Scala, meaning we write unit tests in Scala - but don't worry, we use it to test Java code anyway, it's easy! :-)

Dependency Injection is really useful to us for this development style. If you're not familiar with the design pattern, we recommend watching the video at the Google Guice project page, [here](http://code.google.com/p/google-guice/).

Notes on Building
-----------------

The project has a selection of tags, which are numbered to go along with the tutorial. Each of these /should/ just build, with one less-than-ideal piece of house work. I've had to check in the `local.properties` file, against all advice, because the process for building a Scala project on Android required that it is modified. This means, in order to build you'll need to change the paths inside it to match your installation. 

You need to install the version of [Scala](http://www.scala-lang.org) that matches the Borachio version in test/libs. At the time of writing this is 2.9.0.1 - adjust the `local.properties` as mentioned above to reflect your installation. Also change the ProGuard and SDK paths to wherever you've installed the Android SDK. Otherwise, this is pretty much a straight forward Ant-based Android project.


Tutorial
--------

This is a walkthrough of a project using Mocking and Dependency injection, built up in small stages. You can check out the code using the referenced tag if you want to play around with it at any stage.


### Step 1 -- A crude, non-functional UI.

This step was complete at the 1_EmptyUI tag:

    $ git checkout 1_EmptyUI

At this stage, you can quite simply import the project into Eclipse and hit the play button to try it out on your phone. There's not much to see - just a straight forward interface for adding inventory items and placing orders. All of it is completely non-functional. The remaining stages look to add a simple unit test, write the back end then migrate to Dependency Injection.


### Step 2 -- Add a failing unit test

This step refers to the code at the 2_FailingTest tag:

    $ git checkout 2_FailingTest

Now a test project has been added, with the required Borachio library and a straight forward unit test. The test calls the `fill()` function on the stub `Order` implementation, which right now should fail, as the implementation only ever returns `false`. You can run the tests from the test directory with:

    $ ant run-tests

You'll need either an emulator running or a phone plugged in and connected to ADB. This should should a failing JUnit assertion with `false` returned from `fill()` where `true` was expected. If you look at the Scala source in the test/src tree, you'll see how Borachio was used to Mock out `Order`'s dependency on the `Warehouse` component.

Got that? Good. Now we can implement `fill()`.

### Step 3 -- Unit test passes

If you want to verify that this now works:

    $ git checkout 3_TestPassing

Running the tests as before should now give you "OK". Of course, the UI still does nothing, but we've tested that the `fill()` function is working as expected. That's quite nice, as we actually defined the expectations for how `Order` should interact with the dependent `Warehouse` component in the previous step, and now we've verified that those expectations are all being met.

We didn't mention it previously, but this process of writing the unit test before implementing the real thing is Test Driven Development (TDD), which is our preferred way of working at TouchType. Now we know that works, let's connect up enough of the rest of the app to have a working interface before we move onto incorporating dependency injection.


### Step 4 -- A working application

If you want to build the app and run it:

    $ git checkout 4_WorkingApp

Build the app, either from Eclipse or with Ant from the project root. You may need to use the `android update project` command to generate a `local.properties` in the project root. If you do that though, remember to delete thr `proguard.cfg` file it generates before attempting to run Scala test code again, or something bad will likely happen.

We've had to provide some implementation of Warehouse for the application to actually do anything. We could write tests for Warehouse too, although it wouldn't make a particularly interesting Mock testing example, as there are no dependencies to mock out. Still, if it was persisting the inventory, then we'd certainly have something to mock. You could try this as an exercise -- or if you're feeling ambitious, wait until we've added Dependency Injection, then you can inject an inventory component and see the benefits of DI with TDD and Mock Testing first hand!

### Step 5 -- Adding Dependency Injection

You might like to see the code changes required to migrate an app (albeit a simple one) to RoboGuice. It's surprisingly straight forward. You can checkout this stage:

    $ git checkout 5_WithDependencyInjection

Or, for the casual reader, just view the diffs [here](https://github.com/jaley/borachio-warehouse/commit/16de54753b77874a923c420579dd39d72002f55f) and then [here](https://github.com/jaley/borachio-warehouse/commit/018524797fe818d71a268b405028fb773f961621). The most notable changes, in practice, are that your classes might be too tightly coupled initially. The process of creating an interface and implementation class seen in the second of those diffs is fairly common when introducing Dependency Injection. We find Eclipse's refactoring rename and "extract interface" features make this a trivial task, usually.

Now that we've started injecting dependencies between modules in our code, writing the tests can be much more straight-forward for large projects. Imagine there were 5 or 6 dependencies from the System Under Test to other areas of the code - all we need to do is inject mock instances of those dependencies, which we can use to present back test data to the system under test and assert that the interaction is as we expect.


### Step 6 -- Testing with Dependency Injection

The code for this stage is tagged

    $ git checkout 6_TestWithDI

At TouchType, we now basically have a template for writing Unit Tests, which is demonstrated at this stage. A Unit Test will be a Scala class using JUnit, Borachio and RoboGuice. We use the JUnit `setUp()` to initialize an Injector with a fake RoboGuice module, set up specifically for the test. This module will bind the System Under Test to the real implementation (which we want to test, of course!) and all of its dependencies to mock objects created by Borachio, and bound with Guice's `toInstance()` method. Placing this in the setUp() method means you're starting with a clean canvas at every test, though with a large module you might find the test taking longer than you'd normally expect.


Template test using Borachio and Guice
--------------------------------------

Here's that test template in Scala code, with comment annotations:

    class MyUnitTest extends TestCase with MockFactory {

      // You may want an injector in the unit tests, or alternatively
      // use the injector in the set up method to create the SUT and
      // hold that in a member instead.
      var injector : Injector = _

      // All dependent components should be mocked. Hold them in members
      // so that you can set expectations in test cases.
      var mockedDependency : DependencyInterface with Mock = _


      // The setUp method is where we will create mocked dependencies and
      // initialize the injector with a set of test bindings to be used in
      // each test.
      override def setUp {
        super.setUp

        // Initialize a mock object for each dependency
        mockedDependency = mock[DependencyInterface]

        // Now set up a Guice Injector, with a module we'll specifiy now
        injector = Guice.createInjector(new AbstractModule {
          override def configure() {
            // System Under Test is bound to the implementation we want to test
            bind(classOf[SUT]).to(classOf[SUTImpl])

            // Dependent objects are bound to the mocks we just created
            bind(classOf[DependencyInterface]).toInstance(mockedDependency)
          }
        })
      }

      // Usual JUnit tearDown() is available if needed
      override def tearDown {
        super.tearDown
      }

      
      // Now we can write as many JUnit tests as we like, as per normal

      @MediumTest
      def testMyMockTest {

        // Use the withExpectations function to get Borachio to check expectations!
        withExpectations {

          // Set some expectations on your mock objects
          inSequence {
            mockedDependency expects 'someFunction withArgs (1, 2) returning true once;
          }

          // Exercise the SUT with expectations set
          SUT sut = injector.getInstance(classOf[SUT])
          assertTrue(sut.doSomething())
        }
      }

    }

