package com.eventsystem.components;

import com.eventsystem.events.Event;
import com.eventsystem.exceptions.NoSuchTopicException;
import com.eventsystem.exceptions.TopicNameUnavailableException;
import com.eventsystem.exceptions.UnableToQueueEventException;
import com.eventsystem.subscriber.Subscriber;
import com.eventsystem.topics.Topic;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * Is an interface follows the publish-subscribe
 * design pattern combined with the concept of "topics"
 * for dealing with events.
 *
 * @author Ron
 * @since 1.0
 */
public class EventBus {

    private final Hashtable<String, Topic> topics;

    private final EventDispatcher eventDispatcher;

    public EventBus() {
        topics = new Hashtable<>();

        eventDispatcher = new EventDispatcher();
    }

    /**
     * Returns a collection of strings in which each
     * string is a represents a name of an available topic.
     *
     * @return names of all available topics.
     */
    public Set<String> getAvailableTopics() {
        return topics.keySet();
    }

    /**
     * Checks if a subscriber is registered to the specified channel.
     *
     * @param subscriber                the subscriber to check on the topic channel.
     * @param topicChannel              the topic channel to check on.
     * @return                          true if the subscriber is subscribed to the specified topic.
     * @throws NullPointerException     in case where one of the parameters is null.
     * @throws NoSuchTopicException     in case that the specified topic does not exist.
     */
    public boolean subscribed(Subscriber subscriber, String topicChannel) throws NullPointerException, NoSuchTopicException {
        Topic topic = topics.get(topicChannel);

        if (topic == null)
            throw new NoSuchTopicException(topicChannel);
        return topic.getSubscribers().contains(subscriber);
    }

    /**
     * Returns a collection of strings in which each
     * string represents a name of a topic that the
     * specified subscriber is subscribed to.
     *
     * @param subscriber                the subscriber whose subscriptions will be returned.
     * @return                          names of all topics the subscriber is subscribed to.
     * @throws NullPointerException     in case the parameter is null.
     */
    public ArrayList<String> getSubscriptions(Subscriber subscriber) throws NullPointerException {
        ArrayList<String> subscriptions = new ArrayList<>();

        for (Map.Entry<String, Topic> entry : topics.entrySet()) {
            Topic topic = entry.getValue();

            if (topic.getSubscribers().contains(subscriber))
                subscriptions.add(entry.getKey());
        }
        return subscriptions;
    }

    /**
     * Subscribes the specified subscriber to the specified topic.
     *
     * @param subscriber                the subscriber to subscribe.
     * @param topicChannel              the specified channel to subscribe the subscriber.
     * @return                          true if the subscriber successfully subscriber to the topic channel.
     * @throws NullPointerException     in case one of the parameters is null.
     * @throws NoSuchTopicException     in case that the specified topic does not exist.
     */
    public synchronized boolean subscribe(Subscriber subscriber, String topicChannel) throws NullPointerException, NoSuchTopicException {
        Topic topic = topics.get(topicChannel);

        if (topic == null)
            throw new NoSuchTopicException(topicChannel);
        return topic.getSubscribers().add(subscriber);
    }

    /**
     * Unsubscribes the specified subscriber from the specified topic.
     *
     * @param subscriber                the subscriber to unsubscribe.
     * @param topicChannel              the specified channel to unsubscribe the subscriber.
     * @return                          true if the subscriber successfully unsubscribed from the topic channel.
     * @throws NullPointerException     in case one of the parameters is null.
     * @throws NoSuchTopicException     in case that the specified topic does not exist.
     */
    public synchronized boolean unsubscribe(Subscriber subscriber, String topicChannel) throws NullPointerException, NoSuchTopicException {
        Topic topic = topics.get(topicChannel);

        if (topic == null)
            throw new NoSuchTopicException(topicChannel);
        return topic.getSubscribers().remove(subscriber);
    }

    /**
     * Publishes the specified event to the specified topic.
     * The event will be cloned for each subscriber and will
     * be pushed into the dispatcher's queue with the specified
     * recipient.
     *
     * @param event                     the event to publish.
     * @param topicChannel              the topic channel to publish on.
     * @throws NullPointerException     in case one of the parameters is null.
     * @throws NoSuchTopicException     in case that the specified topic does not exist.
     */
    public synchronized void publish(Event event, String topicChannel) throws NullPointerException, NoSuchTopicException {
        Topic topic = topics.get(topicChannel);

        if (topic == null)
            throw new NoSuchTopicException(topicChannel);
        event = event.clone();
        event.setTopicChannel(topicChannel);

        for (Subscriber subscriber : topic.getSubscribers()) {
            event.setRecipient(subscriber);

            if (!eventDispatcher.push(event))
                throw new UnableToQueueEventException();
            event = event.clone();
        }
    }

    /**
     * Creates a new topic with the specified name.
     *
     * @param name                      the name of the new topic.
     * @throws NullPointerException     in case the parameter is null.
     */
    public synchronized void createTopic(String name) throws NullPointerException {
        if (topics.containsKey(name))
            throw new TopicNameUnavailableException(name);
        topics.put(name, new Topic(name));
    }

    /**
     * Deletes a topic with the specified name.
     *
     * @param topicChannel              the name of the new topic.
     * @throws NullPointerException     in case the parameter is null.
     */
    public synchronized void deleteTopic(String topicChannel) {
        Topic topic = topics.get(topicChannel);

        if (topic == null)
            throw new NoSuchTopicException(topicChannel);
        topics.remove(topicChannel);
    }

    /**
     * Deletes all the existing topics.
     */
    public synchronized void deleteAllTopics() {
        topics.clear();
    }

}
