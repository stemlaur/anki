# Domain

This layer contains information about the domain. This is the heart of the business software. 
The state of business objects is held here. 

Persistence of the business objects and possibly their state is delegated 
to the infrastructure layer using dependency inversion.

The service-poller is divided into two responsabilities, a service registrator and a service monitor.

We postpone infrastructure decisions like, until we are ready to tackle them:

- should we introduce a frameword for the web part, if yes which one (Vertx, Spring MVC, ...)
- what kind of database we would like to use to store our service statuses
- what are the business rules regarding health checks (should we consider 2XX ok, 3XX ?)
- what frontend technology would fit the best, should we use an SPA or not, should we use a JS framework ?   
