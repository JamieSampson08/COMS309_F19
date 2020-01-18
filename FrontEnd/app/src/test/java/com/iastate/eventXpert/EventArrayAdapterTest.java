package com.iastate.eventXpert;

import com.iastate.eventXpert.adapters.EventArrayAdapter;
import com.iastate.eventXpert.objects.Event;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class EventArrayAdapterTest {
	private EventArrayAdapter eventArrayAdapter;
	private int expectedId;
	private ArrayList<Event> expectedItemList;
	private Event sampleEventOne;
	private Event sampleEventTwo;
	private EventArrayAdapter.OnEventListener sampleOnEventListener;

	@Before
	public void setUp() {
		expectedId = 4;
		sampleEventOne = mock(Event.class);
		sampleEventTwo = mock(Event.class);
		sampleOnEventListener = mock(EventArrayAdapter.OnEventListener.class);
		expectedItemList = new ArrayList<>();
		expectedItemList.add(sampleEventOne);
		expectedItemList.add(sampleEventTwo);
		eventArrayAdapter = new EventArrayAdapter(expectedId, expectedItemList, sampleOnEventListener);
	}

	@Test
	public void testGetItemCount() {
		int size = eventArrayAdapter.getItemCount();

		assertEquals(2, size);
	}
}
