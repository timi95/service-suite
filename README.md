# service-suite
A multi module gradle project built with Spring Boot.

## Gradle 
- version 8.0.2


## Ingredients
- Api Gateway (single point of entry)
- SSO (with Okta, because it was easy to setup)
- Test Containers (containerised tests)
- Containerised services with Docker
- Eureka service discovery
- Resilience4J circuit breaker for fault tolerance in the system.
- Kafka message broker for asynchronous messaging
- Distributed tracing with Zipkin and Spring sleuth
- 
