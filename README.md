# Index Socialcast with Elasticsearch

This will help you create Elasticsearch indexes for your Socialcast installation. 

## 1. Fetch users and messages from Socialcast

1. Start a shell, and go to the `scripts` - directory.
2. Copy `setenv.sh.orig` to `setenv.sh`, and set `sc_url`, `sc_username` and `sc_password` for your Socialcast account.
3. Start fetching data by running `./getdata.sh`.

## 2. Create indexes

Wait for the data to be fetched from Socialcast, and you can start the indexing app:

1. Install Java
2. Install Maven
3. Start the app: run `mvn spring-boot:run`

The indexing will now start, and will create 3 indexes:

1. `socialcast/messages`
2. `socialcast/comments`
3. `socialcast/likes`

## 3. Search

Wait for the indexing to be complete, and you can start searching.

Example: search the message-index for the string `test`:

`curl http://localhost:9200/socialcast/messages/_search?q=test`

Se `searches` - directory for more examples, and look at the Elasticsearch documentation for how to search:
https://www.elastic.co/guide/en/elasticsearch/reference/current/index.html
