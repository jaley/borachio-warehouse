package com.touchtype_fluency.examples.borachio_warehouse;
import java.util.List;

import roboguice.application.RoboApplication;

import com.google.inject.Module;


public class BorachioWarehouseApplication extends RoboApplication {
    protected void addApplicationModules(List<Module> modules) {
        modules.add(new BorachioWarehouseModule());
    }
}
