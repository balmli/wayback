#!/bin/bash

echo " "
echo "Most number of messages in 2017 ?"
echo " "

curl -X POST 'http://localhost:9200/socialcast/messages/_search?size=0' -d '{
    "size": 1,
    "query" : {
     "bool" : {
       "must" : [ {
         "range" : {
           "created" : {
             "from" : "2016-12-31T23:00:00Z",
             "to" : "2017-12-31T22:59:59.999Z",
             "include_lower" : true,
             "include_upper" : true
           }
         }
       } ]
     }
    },
    "aggregations" : {
        "messages" : { "terms": { "field" : "userId", "size": 150 } }
    }
}
' | python -c 'import json,sys;
obj=json.load(sys.stdin);
for x in obj["aggregations"]["messages"]["buckets"]:
  print "{0} wrote {1} messages.".format(
    x["key"].replace("_", " ").title().encode("utf-8"),
    x["doc_count"]
    );
'
