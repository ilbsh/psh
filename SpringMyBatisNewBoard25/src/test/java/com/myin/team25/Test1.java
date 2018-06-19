package com.myin.team25;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class Test1 {
	@RunWith(SpringJUnit4ClassRunner.class)
	@ContextConfiguration(locations = { "classpath:Config/root-context.xml" })
	public class BoardDao_FatherTest {

		@Autowired
		private BoardDao_FatherTest bdf;

		@Before
		public void setup() throws Exception {
		}

		@Test
		public void testGetSelectBoardOne() {
			BoardVo bv = 
			

		}

	}

}
