package com.higherli.library;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.higherli.library.business.demo.CalAveraServiceTest;
import com.higherli.library.event.SynEventDispatcherTest;

@RunWith(Suite.class)
@SuiteClasses({ CalAveraServiceTest.class, SynEventDispatcherTest.class })
public class AllTests {

}
