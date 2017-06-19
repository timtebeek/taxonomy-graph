# Taxonomy Graph
Load the NCBI Taxonomy into Neo4J and expose with Spring Data.

## Run

### Clone
`git clone git@github.com:timtebeek/taxonomy-graph.git && cd taxonomy-graph;`

### Download & process data
`sh src/main/resources/neo4j-import.sh`

### Run Neo4J
`docker run --publish=7474:7474 --publish=7687:7687 --volume=$HOME/neo4j/data:/data --volume=$HOME/neo4j/logs:/logs --env NEO4J_AUTH=none neo4j:3.2`

### Import data
`docker exec <container> sh data/import.sh`

### Run API
`mvn spring-boot:run`
