# Card Application Portal - Dream Bank 

# Component
1. Bank Config Server
2. Card Application Microservice
3. Decision Microservice
4. Equifax Credit Service
5. Experian Credit Service
6. Transunion Credit Service
7. Mongo DB (Card Application Document Store)

# Setting Up Mongodb with Docker 
Step 1 : docker pull mongo

Step 2 : docker run -d -p 27018:27017 --name dreambank-mongodb mongo;

Step 3 : Cheking if Mongo Service is running and port forwarding working in docker 
         docker ps

# Communication Diagram
<img height="350" src="./img_1.png" width="750"/>



