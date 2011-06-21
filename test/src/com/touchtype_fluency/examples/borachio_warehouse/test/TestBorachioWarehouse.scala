package com.touchtype_fluency.examples.borachio_warehouse.test;

import junit.framework.TestCase
import junit.framework.Assert._
import android.test.suitebuilder.annotation._

import com.borachio._
import com.borachio.junit3.MockFactory

import com.google.inject._
import roboguice.test.RoboUnitTestCase

import com.touchtype_fluency.examples.borachio_warehouse._

class TestBorachioWarehouse extends TestCase with MockFactory {
  
  var injector : Injector = _
  var mockWarehouse : Warehouse with Mock = _


  override def setUp {
    super.setUp
    mockWarehouse = mock[Warehouse]

    injector = Guice.createInjector(new AbstractModule {
      override def configure() {
        // System Under Test is Order, so we use the real implementation
        bind(classOf[Order]).to(classOf[OrderImpl])

        // Order has a dependency on Warehouse, which we are mocking
        bind(classOf[Warehouse]).toInstance(mockWarehouse)
      }
    })
  }

  @MediumTest
  def testFill {
    withExpectations {
      inSequence {
        mockWarehouse expects 'hasInventory withArgs ("Talisker", 10) returning true once;
        mockWarehouse expects 'remove withArgs ("Talisker", 10) once;
      }

      val o = injector.getInstance(classOf[Order])
      o.update("Talisker", 10)

      assertTrue(o.fill)
    }
  }

  override def tearDown {
    super.tearDown
  }
 
}
