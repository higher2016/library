package com.higherli.library.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.higherli.library.event.syn.ListenSynEvent;
import com.higherli.library.event.syn.SynEvent;
import com.higherli.library.event.syn.SynEventDispatcher;
import com.higherli.library.event.syn.SynEventType;

public class SynEventDispatcherTest {
	@Test
	public void testAddEventListenersObjectOnce() {
		SynListener synListener = new SynListener();
		boolean addListenerRes = SynEventDispatcher.INSTANCE.addEventListeners(synListener);
		assertTrue(addListenerRes);
	}

	@Test
	public void testAddEventListenersObjectTwice() {
		SynListener synListener = new SynListener();
		SynEventDispatcher.INSTANCE.addEventListeners(synListener);
		boolean addListenerRes = SynEventDispatcher.INSTANCE.addEventListeners(synListener);
		assertFalse(addListenerRes);
	}

	@Test
	public void testDispatchEventSynEvent() {
		SynListener synListener = new SynListener();
		SynEventDispatcher.INSTANCE.addEventListeners(synListener);
		SynEventDispatcher.INSTANCE.dispatchEvent(new UnknownEvent(1, 2));
		assertEquals(synListener.testInt1, 1);
		assertEquals(synListener.testInt2, 2);
	}

	
	private static class UnknownEvent implements SynEvent {
		public final int testInt1;
		public final int testInt2;

		private UnknownEvent(int testInt1, int testInt2) {
			this.testInt1 = testInt1;
			this.testInt2 = testInt2;
		}

		public SynEventType getEventType() {
			return SynEventType.UNKNOWN;
		}
	}

	private static class SynListener {
		protected int testInt1 = 0;
		protected int testInt2 = 0;

		@ListenSynEvent(eventType = SynEventType.UNKNOWN)
		public void onUnknownEventHappen1(final SynEvent event) {
			UnknownEvent ue = (UnknownEvent) event;
			this.testInt1 = ue.testInt1;
		}

		@ListenSynEvent(eventType = SynEventType.UNKNOWN)
		public void onUnknownEventHappen2(final SynEvent event) {
			UnknownEvent ue = (UnknownEvent) event;
			this.testInt2 = ue.testInt2;
		}
	}
}
