package cn.tuyucheng.taketoday.pubsubmq.client;

public class Consumer {
	public void receiveOrder(String message) {
		System.out.printf("Order received: %s%n", message);
	}
}