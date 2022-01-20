# Architecture Decision Record: Domain layer

## Context

In order to separate domain specific business rules from infrastructure details, we need to:

- separate this domain concern
- remove dependencies from the domain layer to the infrastructure details (database, http, messaging)
- stop introducing dependencies between the domain and any infrastructure frameworks (spring, hibernate, ...)

## Decision

We will create a specific domain module containing all domain specific classes and business logic.

This layer will contain information about the domain. This is the heart of the business software. The state of business objects is held here.

Persistence of the business objects and possibly their state are delegated to the infrastructure layer using dependency inversion.

We will postpone infrastructure decisions like, until we are ready to tackle them:

* should we introduce a framework for the web part, if yes which one (Vertx, Spring MVC, ...)
* what kind of database we would like to use to store our service statuses
* what are the business rules regarding health checks (should we consider 2XX ok, 3XX ?)
* what frontend technology would fit the best, should we use an SPA or not, should we use a JS framework ?



## Status

Accepted

## Consequences

1. Developers must write their domain business classes in the domain module.
2. Developers should not add any framework dependencies to the domain module.
3. Developers should name theirs classes depending on the domain experts terms.
