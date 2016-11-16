package locktest;

import com.hazelcast.core.*;

public class Locker implements MessageListener<Boolean> {

	private static HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
	private static ILock lock = hazelcastInstance.getLock("testLock");

	public static void main(String[] args) {
		ITopic<Boolean> topic = hazelcastInstance.getTopic("topic");
		topic.addMessageListener(new Locker());

		ILock lock = hazelcastInstance.getLock("testLock");
		lock.lock();
		while (lock.isLocked()) {
			System.out.println("Locked");
		}
		System.out.println("Unlocked");
	}

	@Override
	public void onMessage(Message<Boolean> message) {
		System.out.println("Try to unlock");
		if (lock.isLocked()) {
			System.out.println("Locked");
			lock.unlock();
		} else {
			System.out.println("Failed");
		}

	}
}
