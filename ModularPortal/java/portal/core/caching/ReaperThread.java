package portal.core.caching;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ReaperThread extends Thread implements Observer {

	public static final ReaperThread theReaperThread = new ReaperThread();

	private final List<ReapableCacher<Object, Object>> _observedCaches;

	private boolean _shouldFlush;

	static {
		theReaperThread.start();
	}

	private ReaperThread() {
		super("The Reaper!");
		_observedCaches = new ArrayList<ReapableCacher<Object, Object>>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object someObject) {
		if (!(ReapableCacher.class.getName().equals(o.getClass().getName()))) {
			throw new IllegalArgumentException("Observable object is not a, "
					+ ReapableCacher.class);
		}
		synchronized (_observedCaches) {
			_observedCaches.add((ReapableCacher<Object, Object>) o);
		}

	}

	@Override
	public void run() {
		while (true) {
			List<ReapableCacher<Object, Object>> copyOfObservedCaches;
			synchronized (_observedCaches) {
				copyOfObservedCaches = new ArrayList<ReapableCacher<Object, Object>>(
						_observedCaches.size() + 5);
				for (ReapableCacher<Object, Object> reapable : _observedCaches) {
					copyOfObservedCaches.add(reapable);
				}
			}
			for (ReapableCacher<Object, Object> reapable : copyOfObservedCaches) {
				reapable.reap();
			}

			if (shouldFlush()) {
				for (ReapableCacher<Object, Object> reapable : copyOfObservedCaches) {
					reapable.flush();
				}
				setShouldFlush(false);
			}
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

		}
	}

	public synchronized boolean shouldFlush() {
		return _shouldFlush;
	}

	public synchronized void setShouldFlush(boolean shouldFlush) {
		_shouldFlush = shouldFlush;
	}

}
