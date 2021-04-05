package tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import privblock.gerald.ryan.entity.Transaction;
import privblock.gerald.ryan.entity.TransactionPool;
import privblock.gerald.ryan.entity.Wallet;

public class TestTransactionPool {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSetTransaction()
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
		TransactionPool tp = new TransactionPool();
		Transaction t = new Transaction(new Wallet(), "here", 165);
		tp.setTransaction(t);
		assertEquals(t, tp.getTransactionMap().get(t.getUuid()));
		Transaction t2 = new Transaction(new Wallet(), "here", 165);
		assertNotEquals(t, tp.getTransactionMap().get(t2.getUuid()));

	}

}
