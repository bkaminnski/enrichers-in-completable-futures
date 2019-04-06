# Enrichers in Completable Futures

## Problem

The topic of the project is to concurrently enrich a single object using data distributed among multiple microservices and external systems. 

We start with an object in some initial state, and in order to move forward with business logic, we need to build the object up. Overall the object is a complex structure, and the data required for further processing is distributed among multiple microservices and external systems.

## Case Study Introduction

For example, let's consider an insurance company that would like to classify its customers to present them personalized offers. The company segments their customers into risky, regular and premium. There is a rule engine that performs the classification, and our goal is to provide aggregated data aka 360 degrees view of the customer. The data happens to be distributed among different departments of the company, which are reflected in microservices.

Each microservice allows getting a distinct aspect of the information related to the customer. In real life project the list could grow significantly. Here, let's focus only on few of the possible areas.

- `Contact data` - keeps track of addresses, phone numbers and e-mails.
- `Payment history` - keeps track of each insurance premium paid by the customer. It also knows which payments were late or delinquent. 
- `Claims` - keeps track of the claims made by the customers.
- `Feedback` - keeps track of the feedback provided by the customers. In this case however we need to dig deeper. Each feedback relates to a particular communication occurrence (when insurence company reached out to the customer), and for each feedback we need to get more details about the communication that feedback relates to. This means that once we get feedback, we need to make another call to `Communication` service which holds complete history of communication with the customer.

We need to create the `Classification` service that builds up the customer object which can be next processed by the rule engine.

## Solution

From the architecture point view, this problem could be solved using events. Each service could publish an event. `Classification` service could listen to these events and build local representation of the data required for customer classification, effectively replicating relevant data. Techniques discussed in the following project could be helpful in this approach: [kafka-in-spring](https://github.com/bkaminnski/kafka-in-spring), [kafka-streams-in-spring](https://github.com/bkaminnski/kafka-streams-in-spring).

In our case however, the `Classification` service actively calls all other services synchronously. And this makes us think of all the consequence such architectural decision brings.

- Performance: each network call means latency + processing time on the other side. We have to call services concurrently.
- Reliability: network calls may time out, services might be unavailable. We have to take this into consideration.
- Data consistency: we must not allow any latecomers to break consistency of the object that we have already built up. It means that once we move forward with the business logic of the `Classification` service, no long-running-communication-thread that finally gets response from the other service may mutate the customer object (we will get back to this topic later).

