# si-demo
Spring Webflux + Spring Integration
1.1. Post call for get Global Id
1.2. goes in to requester queue with identifier
	2.a : uses the identifier to listen for response

2.1 listener/consumer on the request queue (concurrent)
2.2 read a message and fetch the global id from local mirrored queue
2.3 open multicast
  2.3.a: prepare message with global id and add the jms correlation and push it to response queue
  2.3.b: prepare a message to the replenish queue

3.1 single consumer on replenish queue
   3.1.1 check the mirrored queue size and if it is below threshold
   	3.1.2.a call the database method to fetch new set of sequences
   	3.1.2.b read all global id from original queue and append them to the mirrored queue
   	***
   	3.1.2.c call the database to fetch new set of sequences
   	

startup script
 -- if mirrored queue is empty or below threshold take the messages from original queue and populate mirrored queue
 -- call db sequence generator api to reload the original queue
