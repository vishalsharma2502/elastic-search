How to use Elasticsearch with Spring Data

This application code demonstrates use of indexing and search capabilities of Spring Data Elasticsearch in a simple search application used for searching products in a product inventory. The main steps for running the application are:

Start an Elasticsearch Instance by running the Docker run command:
docker run -p 9200:9200 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.10.0
Clone the application and change the current directory to application root.
Build the application with Maven
mvn clean package
Start the application
java -jar target/<application>.jar
The productindex will be built during application start up.
Access the application with URL: http://localhost:8080/search
Start to input some characters in the search box(examples: toy, white shirt, jacket, etc), which will open an auto-complete box of maximum 5 suggestions.
Complete the search text and click search button to see the search results.