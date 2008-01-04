package org.jnode.apps.jpartition.model;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

abstract public class AbstractTestDevice extends AbstractTest {
	protected static final int DEVICE_SIZE = 5000;

	protected Device device;

	abstract protected long getStartFreeSpace();
	abstract protected long getEndFreeSpace();
	abstract protected int getIndexFreeSpacePartition();

	final protected long getFreeSpace()
	{
		return getEndFreeSpace() - getStartFreeSpace() + 1;
	}

	@Before
	public void setUp() throws Exception {
		device = new Device("dev1", DEVICE_SIZE);
	}

	@Test
	public void testAddPartitionWithIllegalStart()
	{
		try {
			device.addPartition(getStartFreeSpace()-1, getFreeSpace() - 1);
			Assert.fail("failed case 1");
		} catch (DeviceException e) {
			// success
		}

		try {
			device.addPartition(getStartFreeSpace()-1, getFreeSpace());
			Assert.fail("failed case 2");
		} catch (DeviceException e) {
			// success
		}
	}

	@Test
	public void testAddPartitionWithIllegalSize()
	{
		try {
			device.addPartition(getStartFreeSpace(), -1);
			Assert.fail("failed case 1");
		} catch (DeviceException e) {
			// success
		}

		try {
			device.addPartition(getStartFreeSpace(), 0);
			Assert.fail("failed case 2");
		} catch (DeviceException e) {
			// success
		}

		try {
			device.addPartition(getStartFreeSpace(), getFreeSpace() + 1);
			Assert.fail("failed case 3");
		} catch (DeviceException e) {
			// success
		}

		try {
			device.addPartition(getStartFreeSpace(), getFreeSpace() + 2);
			Assert.fail("failed case 4");
		} catch (DeviceException e) {
			// success
		}
	}

	@Test
	public void testAddPartitionAllFreeSpace()
	{
		final int nbPartitions = device.getPartitions().size();
		device.addPartition(getStartFreeSpace(), getFreeSpace());

		List<Partition> partitions = device.getPartitions();
		Assert.assertEquals("must have only "+nbPartitions+" partition(s)", nbPartitions, partitions.size());

		Partition part = partitions.get(getIndexFreeSpacePartition());
		assertEquals(getStartFreeSpace(), getFreeSpace(), true, part);
	}

	@Test
	public void testAddPartitionBeginFreeSpace()
	{
		final int nbPartitions = device.getPartitions().size();
		final long begin = getStartFreeSpace();
		final long size = getFreeSpace() - 1500;
		device.addPartition(begin, size);

		List<Partition> partitions = device.getPartitions();
		final int expectedNbPartitions = nbPartitions + 1;
		Assert.assertEquals("must have only "+expectedNbPartitions+" partition(s)", expectedNbPartitions, partitions.size());

		Partition part1 = partitions.get(getIndexFreeSpacePartition());
		assertEquals(begin, size, true, part1);

		Partition part2 = partitions.get(getIndexFreeSpacePartition()+1);
		long part2Size = getFreeSpace() - part1.getSize();
		assertEquals(begin + size, part2Size, false, part2);
	}

	@Test
	public void testAddPartitionMiddleFreeSpace()
	{
		final int nbPartitions = device.getPartitions().size();
		final long shift = 500;
		final long begin = getStartFreeSpace() + shift;
		final long size = getFreeSpace() - 1500;
		device.addPartition(begin, size);

		List<Partition> partitions = device.getPartitions();
		final int expectedNbPartitions = nbPartitions + 2;
		Assert.assertEquals("must have only "+expectedNbPartitions+" partition(s)", expectedNbPartitions, partitions.size());

		Partition part1 = partitions.get(getIndexFreeSpacePartition());
		assertEquals(getStartFreeSpace(), shift, false, part1);

		Partition part2 = partitions.get(getIndexFreeSpacePartition()+1);
		assertEquals(begin, size, true, part2);

		Partition part3 = partitions.get(getIndexFreeSpacePartition()+2);
		long part3Size = getFreeSpace() - part1.getSize() - part2.getSize();
		assertEquals(begin + size, part3Size, false, part3);
	}

	@Test
	public void testAddPartitionEndFreeSpace()
	{
		final int nbPartitions = device.getPartitions().size();
		final long shift = 1500;
		final long begin = getStartFreeSpace() + shift;
		final long size = getFreeSpace() - shift;
		device.addPartition(begin, size);

		List<Partition> partitions = device.getPartitions();
		final int expectedNbPartitions = nbPartitions + 1;
		Assert.assertEquals("must have only "+expectedNbPartitions+" partition(s)", expectedNbPartitions, partitions.size());

		Partition part1 = partitions.get(getIndexFreeSpacePartition());
		assertEquals(getStartFreeSpace(), shift, false, part1);

		Partition part2 = partitions.get(getIndexFreeSpacePartition()+1);
		assertEquals(begin, size, true, part2);
	}

	@Test
	public void testRemovePartitionAllFreeSpace()
	{
		final int nbPartitions = device.getPartitions().size();
		device.addPartition(getStartFreeSpace(), getFreeSpace());

		List<Partition> partitions = device.getPartitions();
		Assert.assertEquals("must have only "+nbPartitions+" partition(s)", nbPartitions, partitions.size());

		Partition part = partitions.get(getIndexFreeSpacePartition());
		assertEquals(getStartFreeSpace(), getFreeSpace(), true, part);
	}

	@Test
	public void testRemovePartitionBeginFreeSpace()
	{
		final int nbPartitions = device.getPartitions().size();
		final long begin = getStartFreeSpace();
		final long size = getFreeSpace() - 1500;
		device.addPartition(begin, size);

		List<Partition> partitions = device.getPartitions();
		final int expectedNbPartitions = nbPartitions + 1;
		Assert.assertEquals("must have only "+expectedNbPartitions+" partition(s)", expectedNbPartitions, partitions.size());

		Partition part1 = partitions.get(getIndexFreeSpacePartition());
		assertEquals(begin, size, true, part1);

		Partition part2 = partitions.get(getIndexFreeSpacePartition()+1);
		long part2Size = getFreeSpace() - part1.getSize();
		assertEquals(begin + size, part2Size, false, part2);
	}

	@Test
	public void testRemovePartitionMiddleFreeSpace()
	{
		final int nbPartitions = device.getPartitions().size();
		final long shift = 500;
		final long begin = getStartFreeSpace() + shift;
		final long size = getFreeSpace() - 1500;
		device.addPartition(begin, size);

		List<Partition> partitions = device.getPartitions();
		final int expectedNbPartitions = nbPartitions + 2;
		Assert.assertEquals("must have only "+expectedNbPartitions+" partition(s)", expectedNbPartitions, partitions.size());

		Partition part1 = partitions.get(getIndexFreeSpacePartition());
		assertEquals(getStartFreeSpace(), shift, false, part1);

		Partition part2 = partitions.get(getIndexFreeSpacePartition()+1);
		assertEquals(begin, size, true, part2);

		Partition part3 = partitions.get(getIndexFreeSpacePartition()+2);
		long part3Size = getFreeSpace() - part1.getSize() - part2.getSize();
		assertEquals(begin + size, part3Size, false, part3);
	}

	@Test
	public void testRemovePartitionEndFreeSpace()
	{
		final int nbPartitions = device.getPartitions().size();
		final long shift = 1500;
		final long begin = getStartFreeSpace() + shift;
		final long size = getFreeSpace() - shift;
		device.addPartition(begin, size);

		List<Partition> partitions = device.getPartitions();
		final int expectedNbPartitions = nbPartitions + 1;
		Assert.assertEquals("must have only "+expectedNbPartitions+" partition(s)", expectedNbPartitions, partitions.size());

		Partition part1 = partitions.get(getIndexFreeSpacePartition());
		assertEquals(getStartFreeSpace(), shift, false, part1);

		Partition part2 = partitions.get(getIndexFreeSpacePartition()+1);
		assertEquals(begin, size, true, part2);
	}

	@Test
	public void testAddPartitionInNonFreeSpace()
	{
		testAddRemoveInBadPartitionKind(true);
	}

	@Test
	public void testRemovePartitionInFreeSpace()
	{
		testAddRemoveInBadPartitionKind(false);
	}

	private void testAddRemoveInBadPartitionKind(boolean usedPartition)
	{
		final long nbPartitions = device.getPartitions().size();
		for(Partition part : device.getPartitions())
		{
			if(part.isUsed() == usedPartition)
			{
				final long start = part.getStart();
				final long end = part.getEnd();
				final long size = part.getSize();
				final boolean used = part.isUsed();

				try {
					if(usedPartition)
					{
						device.addPartition(start + 1, size - 2);
					}
					else
					{
						device.removePartition(start + 1);
					}
					Assert.fail("must throw an exception");
				} catch (DeviceException e) {
					// success
				}

				Assert.assertEquals(nbPartitions, device.getPartitions().size());
				assertEquals(start, size, used, part);
			}
		}
	}
}
