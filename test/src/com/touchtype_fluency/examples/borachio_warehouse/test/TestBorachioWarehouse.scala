package com.touchtype_fluency.examples.borachio_warehouse.test;

import junit.framework.TestCase
import junit.framework.Assert._
import android.test.suitebuilder.annotation._

import com.borachio._
import com.borachio.junit3.MockFactory

import com.touchtype_fluency.examples.borachio_warehouse.{Order, Warehouse}

class TestBorachioWarehouse extends TestCase with MockFactory {
  
  var mockWarehouse : Warehouse with Mock = _


  override def setUp {
    super.setUp
    mockWarehouse = mock[Warehouse]
  }

  @MediumTest
  def testFill {
    withExpectations {
      inSequence {
        mockWarehouse expects 'hasInventory withArgs ("Talisker", 10) returning true once;
        mockWarehouse expects 'remove withArgs ("Talisker", 10) once;
      }

      val o = new Order(mockWarehouse, "Talisker", 10)
      assertTrue(o.fill)
    }
  }

  override def tearDown {
    super.tearDown
  }
 
}
