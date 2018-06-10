var http = require('http');
var handleRequest = function(request, response) {
  response.writeHead(200);
  response.end('Test Kubernetes - Docker');
};
var myTestServer = http.createServer(handleRequest);
myTestServer.listen(8080);

