## Microservices REST

#### Microservice per operazioni di OAuth2 - Authorization

* Spring Boot 2
* Spring Data
* Spring Social
* OAuth2 
  * Facebook provider
  * Google provider
* Maven
  
###### Impostazioni Provider Facebook:
Developers setting in Facebook, aggiungere alle URI di reindirizzamento OAuth validi il parametro relativo a:
  ```
  facebook.client.userAuthorizationUri
  ```
  
###### Test OAuth2 - Microservice start:
Autenticarsi all'endpoint: 
  ```
   http://localhost:8088/login/facebook
  ```
  
Se le impostazioni sono corrette al seguente endpoint: http://localhost:8088/fb/getResourceController, verrà indicato lo start dell'OAuth Microservice: 

  ```
   getResource (FB) controller ready.
  ```