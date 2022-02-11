
## URL Shortener API

Create a simple URL shortener service (UI is optional but will be considered a plus). Plan the implementation
to be suitable for very high volume of traffic and large amounts of data.
The service should be able to:
● Shorten a given URL and return shortened link;
● Expand a shortened URL into the original one and redirect to the original location.
Shortened URLs should expire after a configured retention period.

## SYSTEM DESIGN
Expectations
-------------------------------------
● Satisfactory implementation should consider aspects of the high volume and/or high load
● Security precautions should be taken for all shortened URLs (it is up to you to make a decisions what
those security precautions are)
● Think how you can optimize the storage
● Consider how to scale your solution horizontally (it's not necessary to implement it, but prepare to
explain this when you present your solution)

Solutions
-----------------------------------------
Generate Shorten URL
Not collision with Base 62 unique data -> 62^8
DB generated sequence – ID

Caching
Spring boot @cacheable annotation
Store result of method call

Data Storage
MYSQL
Optimization of storage:
Partitioning By Key
schedule event - delete expired dates records
Load Balancer
Overload request -> request distribution
Multiple instance of ShortenAPI

Docker-compose
Running multi container docker app with single config file

Security
Input parameter validation – Spring boot @Valid
Custom https certification
Spring boot with okta  - Spring Security 

## Owner contact
Email : [asli.bhr.apaydin@gmail.com](mailto:asli.bhr.apaydin@gmail.com)
LinkedIn: [aslibaharcay](https://www.linkedin.com/in/asl%C4%B1-bahar-%C3%A7ay-7b0b7779/)