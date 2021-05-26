# Rest Client
Call REST API data using Spring RestTemplate.

When run the application, config `Active profile` like this:

![](https://user-images.githubusercontent.com/40904010/119593471-fc830380-be14-11eb-8b38-c7135a651cdc.png)

## Test
REST Client test Using `MockitoJUnitRunner.class` with `Junit4`.

When import `@Value` annotation data not using spring properties, Use `ReflectionTestUtils` like this: 
```$java
    RestClient restClient = new RestClient(template.restClientTemplate());
    ReflectionTestUtils.setField(restClient, "restserverAddress", "http://localhost:8765");
```


## Knowledge Base
It is confronted problem list when implement rest-client.

### `@Value("${rest.svr}")` data is `null`
When print @Value data in RestClient Constructor is always null .
It is not project constructor problem. It is Java Bean Initialization procedure.

![initial](https://user-images.githubusercontent.com/40904010/119590871-8da3ab80-be10-11eb-8767-6e90dd0034b0.png)

### Build failed : Cause: zip END header not found
It occur when gradle file had damaged.
Resolve this problem, `re-download gradle file` or `File > Invalid Caches / Restart`  
