package com.kodtodya.training.fuse.routes;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

//import junit.framework.Assert;

public class UtilTest {

	@Test
	public void testConvertToBoolean() {
		Assert.assertEquals(true, Util.convertToBoolean(1));
	}

}
