package locktest;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ILock;
import com.hazelcast.core.ITopic;

public class Destroyer {
	public static void main(String[] args) {
		HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
		ILock lock = hazelcastInstance.getLock("testLock");
		lock.destroy();
		ITopic<Boolean> topic = hazelcastInstance.getTopic("topic");
		topic.publish(true);
		hazelcastInstance.shutdown();
	}
}
