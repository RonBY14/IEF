# IEF or ICE's Events Framework

# Table Of Contents
  
  * [What Is IEF](#what-is-ief)
  * [Quickstart](#quickstart)
  * [Main Components](#main-components)

# What Is IEF

Is events framework with the goal to help you with building event-driven software in a clean and quick manner.
The framework was originally created for a real-time chat project, but it was written with the intention to be reusable and to fit almost every software.

# Quickstart

The framework is built around the publish-subscribe design pattern and the concept of topics.
This document assumes you are already familiar with these concepts. If not, you can go read about them online, because I will not elaborate on them

## Main Components

  * **Subscriber**
    
    * Is an event handling interface each class must implement in order for its instances to be able to subscribe to topics. 

  * **EventBus**

    * Is the most important object, which from you make most of the interactions with the framework. It provides a very simple interface from which you can subscribe to topics,
      unsubscribe from topics, create new topics, publish events to topics, and more. Use example:
      
      ```
      public class Player extends Subscriber {
          
          public static final String PLAYER_TOPIC = "Player";

          private String name;
          private int age;

          public Player(EventBus eventbus) {
            /*
            Creates new topic for the player so he will be able to receive event.
            */
            eventBus.createTopic(PLAYER_TOPIC);
            eventbus.subscribe(this, Player_TOPIC);
            
            /*
            We want to retrieve the player's data from the database 
            by publishing our custom event to the topic channel the 
            database is subscribed to (Or listenning to).
            */
            eventbus.publish(new PlayerDataRetrieveEvent(), Database.DATABASE_TOPIC)
          }
          
          @Override
          public void handle(Event event) {
            if (event instanceof PlayerDataEvent) {
              PlayerDataEvent e = (PlayerDataEvent) event;
              
              name = e.getName();
              age = e.getAge();
            }
          }
      }
      ```

  * **Event**

    * In order to create custom events you must extend the `Event` class for each custom event class. Use example:
      
      ```
      public class PlayerDataEvent extends Event {
          
          private String name;
          private String age;
          
          public PlayerDataEvent(String playerName) {
            this.playerName = playerName;
          }
          
          public String getPlayerName() {
            return playerName;
          }
          
          public int getAge() {
            return age;
          }
      }
      ```
