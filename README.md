# Cortica

## Java API Assignment


### General Overview 
Write a small Java web application that will do the following:

- Receive a GET request with a search term parameter. 
- Send an API request to 2 external source (Google News and YouTube) with the search term. (See Detailed Specs).
  *NOTE: The request should be sent in parallel
- Parse the response from the API and extract specific parameters. (See Detailed Specs)
- Filter out un-relevant results (That doesn’t include the search Term).
- Order the remaining results according to text similarity (Relevant results first).
- Return a JSON response with the aggregated results. (See Detailed Specs) 

## Detailed Specifications

### GOOGLE NEW API URL:
https://news.google.com/news/feeds?hl=en&gl=us&um=1&ie=UTF-8&output=rss&q=[search term]

### YOUTUBE API URL:
http://gdata.youtube.com/feeds/api/videos?alt=json&q=[search term]

### PARAMETERS TO EXTRACT FROM GOOGLE API:

- title
- link
- pubDate

### PARAMETERS TO EXTRACT FROM YOUTUBE API:

- title
- link
- author

### JSON RESPONSE STRUCTURE:

```
{
   "NewsResponse":{
      "searchTerm":"iphone 5s",
      "results":[
         {
               "title":"ipad 5s",
               "link":"http://news.google.com/news?gl=us&pz=1&ned=us&hl=en&q=ipad+5s"
			   "pubDate":"Sun, 06 Jul 2014 13:03:52 GMT"
         },
    ]
"YTResponse":{
      "searchTerm":"iphone 5s",
      "results":[
         {
               "title":"ipad 5s",
               "link":"http://news.google.com/news?gl=us&pz=1&ned=us&hl=en&q=ipad+5s"
			   "pubDate":"Sun, 06 Jul 2014 13:03:52 GMT"
	   “author”: “Bill Gates”
         },
    ]
}

```