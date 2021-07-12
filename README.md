# Example of using Spring Cloud Gateway

## Starting the server

`./gradlew bootRun`

## Example 1: Proxy to an image

See [Application.kt](/src/main/kotlin/com/dashfwd/Application.kt) for the code related to this example.

[Example proxying an image to Wikipedia](http://localhost:8080/wikipedia/commons/e/eb/Ash_Tree_-_geograph.org.uk_-_590710.jpg)

## Example 2: Proxy to an image by rewriting the URL

See [Application.kt](/src/main/kotlin/com/dashfwd/Application.kt) for the code related to this example.

[Example proxying an image to Wikipedia](http://localhost:8080/tree.jpg)

## Example 3: Proxy to and endpoint and add a header

See [Application.kt](/src/main/kotlin/com/dashfwd/Application.kt) for the code related to this example.

```
curl 'http://localhost:8080/get?&hello=there'
```

Should proxy to httpbin.com and output the query parameters in the `args` element
as well as the headers.  Notes the addition of the `"Hello"` header:
```
{
    "args": {
        "hello": "there"
    },
    "headers": {
        "Accept": "*/*",
        "Content-Length": "0",
        "Forwarded": "proto=http;host=\"localhost:8080\";for=\"0:0:0:0:0:0:0:1:58106\"",
        "Hello": "World",
        "Host": "httpbin.org",
        "User-Agent": "curl/7.64.1",
        "X-Amzn-Trace-Id": "Root=1-60eca8f4-054b3bd44e5e313963920d64",
        "X-Forwarded-Host": "localhost:8080"
    },
    "origin": "0:0:0:0:0:0:0:1, 34.236.252.22",
    "url": "https://localhost:8080/get?hello=there"
}
```


## Example 4: Wildcard matching using AntPathMatcher.

See [Application.kt](/src/main/kotlin/com/dashfwd/Application.kt) for the code related to this example.

So far all the examples have only shown proxying to a single URL.  What if
you wanted to proxy to multiple URLs using a common prefix?  You can do that
because all paths are in [AntPathMatcher format](https://docs.spring.io/spring/docs/5.0.0.M4_to_5.0.0.M5/Spring%20Framework%205.0.0.M5/org/springframework/util/AntPathMatcher.html) 

For this example, any path starting with /base64/ is proxied to HTTPBin. 

The `/base64/{valuee}` endpoint is [documented at HTTPBin here](http://httpbin.org/#/Dynamic_data/get_base64__value_).

For example [try this](http://localhost:8080/base64/SFRUUEJJTiBpcyBhd2Vzb21l).

## Example 5: Redirects

See [RedirectFilter.kt](/src/main/kotlin/com/dashfwd/filters/RedirectFilter.kt)

Both [the home page](http://localhost:8080/) and [/tree](http://localhost:8080/tree) will
redirect to the /tree.jpg image.  


