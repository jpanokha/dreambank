# dreambank
# Setting Up Mongodb with Docker 
Step 1 : docker pull mongo

Step 2 : docker run -d -p 27018:27017 --name dreambank-mongodb mongo;

Step 3 : Cheking if Mongo Service is running and port forwarding working in docker 
         docker ps

Communication Diagram

         ![](/Users/jpanokha/XData/JP/Programs/Workspace/dreambank-playarea/dreambank/Communication_Diagram.paint)