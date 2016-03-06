/*
 * Copyright 2014 Mikael Beyene
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package org.beyene.mode.service.test;

import java.util.Calendar;

import javax.inject.Inject;

import org.beyene.mode.service.Service;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.CoreOptions;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.ProbeBuilder;
import org.ops4j.pax.exam.TestProbeBuilder;
import org.ops4j.pax.exam.junit.PaxExam;
import org.osgi.framework.Constants;

import static org.ops4j.pax.exam.CoreOptions.mavenBundle;

@RunWith(PaxExam.class)
public class ServiceTestCase {

	@Inject
    private Service service;
    
	@ProbeBuilder
	public TestProbeBuilder probeConfiguration(TestProbeBuilder probe) {
		// makes sure the generated Test-Bundle contains this import!
		probe.setHeader(Constants.BUNDLE_SYMBOLICNAME, "org.beyene.mode.service.test.service-test-case");
//		probe.setHeader(Constants.DYNAMICIMPORT_PACKAGE, "org.beyene.mode.service.*,org.apache.felix.service.*,org.ops4j.pax.exam.*;status=provisional");
		probe.setHeader(Constants.DYNAMICIMPORT_PACKAGE, "*;status=provisional");
		probe.setHeader(Constants.EXPORT_PACKAGE, "org.beyene.mode.service.test");
		return probe;
	}
	
	@Configuration
	public Option[] config() {
		return CoreOptions.options(
				/* needed for ds annotations */
				mavenBundle("org.apache.felix", "org.apache.felix.scr").versionAsInProject(),
				mavenBundle("org.ops4j.pax.exam", "pax-exam-container-native").versionAsInProject(),
				mavenBundle("org.ops4j.pax.exam", "pax-exam-junit4").versionAsInProject(),
				mavenBundle().groupId("org.beyene.mode").artifactId("service").versionAsInProject(),
				mavenBundle().groupId("org.beyene.mode").artifactId("service.impl").versionAsInProject(),
				CoreOptions.junitBundles());
	}
	
	@Test
	public void testInjections() {
		Assert.assertNotNull(service);
	}
	
	@Test
	public void testAdd() {
		Assert.assertEquals("This service implementation should add the numbers", 25, service.add(15, 10));
	}
	
	@Test
	public void testGetDate() {
		Calendar a = Calendar.getInstance();
		a.setTime(service.getDate());
		
		Calendar b = Calendar.getInstance();
		
		Assert.assertEquals(a.get(Calendar.YEAR), b.get(Calendar.YEAR));
	}
}