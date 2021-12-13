package Test;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.junit.Assert.*;
// Just to launch every test classes
@RunWith(Suite.class)
@Suite.SuiteClasses({
        LoadingMap.class
        // write other class tests separated by ,
        })
public class JUnit {}

