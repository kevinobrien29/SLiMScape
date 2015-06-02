package vis.exec;

import java.util.LinkedList;

import vis.data.run.Run;
import vis.slimfinder.exec.SLiMThread;

public class WorkQueue {
	private int nThreads;
	private final PoolWorker[] threads;
	private final LinkedList<Run> queue;

	public WorkQueue(int nThreadsPAram) {
		if (nThreadsPAram <= 0) {
			this.nThreads = Runtime.getRuntime().availableProcessors();
		} else {
			this.nThreads = nThreadsPAram;
		}

		queue = new LinkedList<Run>();
		threads = new PoolWorker[nThreads];

		for (int i = 0; i < nThreads; i++) {
			threads[i] = new PoolWorker();
			threads[i].start();
		}
	}

	public void abort(Run r) {
		synchronized (queue) {
			if (queue.contains(r)) {
				System.out.println("remove from queue");
				queue.remove(r);
			} else {
				((SLiMThread)r.getThread()).cancel();
				System.out.println("abort");
			}
			r.failed("cancelled");
		}
	}

	public void execute(Run r) {
		synchronized (queue) {
			queue.addLast(r);
			queue.notify();
		}
	}

	public void executePriority(Run r) {
		synchronized (queue) {
			queue.addFirst(r);
			queue.notify();
		}
	}

	private class PoolWorker extends Thread {
		boolean die = false;

		public PoolWorker() {
		}

		public void run() {
			Run r;

			while (true) {
				synchronized (queue) {
					while (queue.isEmpty()) {
						try {
							queue.wait();
						} catch (InterruptedException ignored) {
							ignored.printStackTrace();
						}
					}

					r = (Run) queue.removeFirst();
				}

				// If we don't catch RuntimeException,
				// the pool could leak threads
				try {
					r.getThread().run();
					if (die) {
						break;
					}
				} catch (RuntimeException e) {
					// You might want to log something here
					e.printStackTrace();
				}
			}
		}
	}
}