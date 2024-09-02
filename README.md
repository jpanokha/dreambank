# Card Application Portal - Dream Bank 

## Component
1. Bank Config Server
2. Card Application Microservice
3. Decision Microservice
4. Equifax Credit Service
5. Experian Credit Service
6. Transunion Credit Service
7. Mongo DB (Card Application Document Store)


### Communication Diagram
<img height="500" src="./img_1.png" width="850"/>

### Creating a Docker Network
Command : 
>docker network create -d bridge dreambank-network

This network will be used while running all bank services. Same network will ease communication among services and mongodb.

### Setting Up Mongodb with Docker
__Step 1 :__ 
>docker pull mongo

__Step 2 :__ 
>docker run -d -p 27018:27017 --network dreambank-network --name dreambank-mongodb mongo;

__Step 3 :__
Cheking if Mongo Service is running and port forwarding working in docker
>docker ps

--Step 4:__
Cleaning up
> docker ps
> 
> docker rm -f CONTAINER ID
> 
> docker ps



### Bank Config Server  & Mongodb
This is a spring cloud based config server to hold configuration (properties) related to dreambank services.
Tech Stack Used : Spring Cloud Config Server, docker, gradle,git.

This service connects with another git repository(https://github.com/jpanokha/dreambank-config) to read configuration and keep it updated based on branch. any service can connect to this service and starts with latest configuration and can refresh all properties in refresh scope.

Docker Compose to this service includes spinning up mongodb just to minimize number of docker-compose and project setup. But this can be spun up as a separate container as described up and can be removed from compose file. 
As this service is pre-requisite for other services just like mongodb, starting will bring up mongodb database and config server.

__Command to run this service__

>docker compose -f ./bank-config-server/docker-compose.yml up --build -d

![img.png](img.png)
__Command to bring down both containers__

>docker compose -f ./bank-config-server/docker-compose.yml down

__Checking Config Server__
Config Server and Mongodb can be noticed in docker for mac/desktop as below

>curl --location 'http://localhost:7071/dreambank-config/decision-service/docker'







