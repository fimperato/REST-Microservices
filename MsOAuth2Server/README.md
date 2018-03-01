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
  
###### OAuth2 Facebook - Microservice start:
Autenticarsi all'endpoint: 
  ```
   http://localhost:[server.port]/login/facebook
  ```
  
Se le impostazioni sono corrette al seguente endpoint: http://localhost:8088/fb/getResourceController, verrà indicato lo start dell'OAuth Microservice: 

  ```
   getResource (FB) controller ready.
  ```
  
###### Impostazioni Provider Google:
Developers setting in Facebook, aggiungere alle origini javascript autorizzate:
  
  ```
   http://localhost:[server.port]
  ```
  
Developers setting in Facebook, aggiungere agli URI di reindirizzamento autorizzati:
    
  ```
   http://localhost:[server.port]/login/google
   http://localhost:[server.port]/oauth2/callback
  ```
    
###### OAuth2 Google - Microservice start:
Autenticarsi all'endpoint: 
  ```
   http://localhost:[server.port]/login/google
  ```
  
Se si è ottenuto correttamente l'access token, l'accesso alla risorsa sarà permesso tramite chiamata in GET:
  ```
   curl -i -H "Authorization: Bearer ya29.[...]AHES(60 Characters Long)" "https://www.googleapis.com/userinfo/v2/me"
  ```
  
  