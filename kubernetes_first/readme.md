# Kubectl - Kubernetes (minikube) - installazione

	sudo snap install kubectl --classic
	
Check version installata con:

	kubectl version

Check su corretta configurazione kubectl verificando lo stato cluster:

	kubectl cluster-info
	
Se il terminale risponde con le seguenti info e url, la configurazione dell'installazione è corretta e completa:

	Kubernetes master is running at https://192.168.99.100:8443
	KubeDNS is running at https://192.168.99.100:8443/api/v1/namespaces/kube-system/services/kube-dns:dns/proxy

	To further debug and diagnose cluster problems, use 'kubectl cluster-info dump'.

Se invece appare un messaggio simile al seguente, kubectl non è correttamente installato oppure non è possibile connettersi al Kubernetes cluster:

	The connection to the server <server-name:port> was refused - did you specify the right host or port?

In tal caso verificare i passi successivi e poi rilanciare il comando 'kubectl cluster-info' per la verifica stato cluster.
Per abilitare l'autocompletamento kubectl, e per ricaricare tale setting al proprio profilo, e all'apertura del terminale, lanciare il seguente comando:

	echo "source <(kubectl completion bash)" >> ~/.bashrc

Installare VirtualBox, ad esempio da deb package:

	https://www.virtualbox.org/wiki/Linux_Downloads
	
E installare Minikube, con il seguente comando: 

	curl -Lo minikube https://storage.googleapis.com/minikube/releases/v0.27.0/minikube-linux-amd64 && chmod +x minikube && sudo mv minikube /usr/local/bin/

Oppure da deb package: 

	https://github.com/kubernetes/minikube/releases

Lanciare minikube con:

	minikube start

Dashboard minikube disponibile con il comando seguente, che apre la console alla url "http://192.168.99.100:30000/#!/overview?namespace=default" :

	minikube dashboard

Verificare l'installazione di minikube con il deployment di 'hello-minikube', lanciamo un echoserver pod

	kubectl run hello-minikube --image=k8s.gcr.io/echoserver:1.10 --port=8080

Esporre il servce deployato con il comando:

	kubectl expose deployment hello-minikube --type=NodePort

Attendere che il pod sia 'up' verificando lo stato e il valore dei pod 'ready', con il seguente comando:

	kubectl get pod

Quando il pod è in stato 'running', sarà raggiungibile tramite comando curl:

	curl $(minikube service hello-minikube --url)

Darà un output del tipo:

	Hostname: hello-minikube-7c77b68cff-kgd8j

	Pod Information:
		-no pod information available-

	Server values:
		server_version=nginx: 1.13.3 - lua: 10008

	Request Information:
		client_address=xxx.xx.xx.xx
		method=GET
		real path=/
		query=
		request_version=1.1
		request_scheme=http
		request_uri=http://192.168.99.100:8080/

	Request Headers:
		accept=*/*
		host=192.168.99.100:31587
		user-agent=curl/7.58.0

	Request Body:
		-no body in request-

Con service e pod up, possiamo printare la lista dei pod e dei service attivi:

	>$ kubectl get service
	NAME             TYPE        CLUSTER-IP     EXTERNAL-IP   PORT(S)          AGE
	hello-minikube   NodePort    xx.xx.xx.xxx   <none>        8080:31587/TCP   2h
	kubernetes       ClusterIP   xx.xx.x.x      <none>        443/TCP          3h
	
	>$ kubectl get service
	NAME             TYPE        CLUSTER-IP     EXTERNAL-IP   PORT(S)          AGE
	hello-minikube   NodePort    xx.xx.xx.xxx   <none>        8080:31587/TCP   2h
	kubernetes       ClusterIP   xx.xx.x.x      <none>        443/TCP          3h

Testare il service 'some-service-name' $ curl $(minikube service hello-minikube --url)/some-service-name

	Hostname: hello-minikube-7c77b68cff-54nwz

	Pod Information:
		-no pod information available-

	Server values:
		server_version=nginx: 1.13.3 - lua: 10008

	Request Information:
		client_address=xxx.xx.x.x
		method=GET
		real path=/some-service-name
		query=
		request_version=1.1
		request_scheme=http
		request_uri=http://192.168.99.100:8080/some-service-name

	Request Headers:
		accept=*/*
		host=192.168.99.100:31587
		user-agent=curl/7.58.0

	Request Body:
	-no body in request-

Print della lista dei ReplicaSets

	>$ kubectl get rs
	NAME                        DESIRED   CURRENT   READY     AGE
	hello-minikube-7c77b68cff   3         3         3         3h


Effettuare lo scale dei ReplicaSet

	>$ kubectl scale deployments/hello-minikube --replicas=3
	deployment.extensions "hello-minikube" scaled

Verifica dello scale: print della lista dei  pods

	>$ kubectl get pod
	NAME                              READY     STATUS    RESTARTS   AGE
	hello-minikube-7c77b68cff-54nwz   1/1       Running   0          3h
	hello-minikube-7c77b68cff-fbd6k   1/1       Running   0          3h
	hello-minikube-7c77b68cff-kgd8j   1/1       Running   0          3h

Verifica dello scale: rilancio the service

	curl $(minikube service hello-minikube --url)/some-service-name

Print della url per raggiungere in GET hello-minikube:
	
	>$ minikube service hello-minikube --url
	http://192.168.99.100:31587

Quando non più utili, si possono cancellare deployment e service con il seguente comando (le risorse vengono liberate):

	kubectl delete service,deployment hello-minikube

	>$ kubectl get pod
	No resources found.
	>$ kubectl get service
	NAME         TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE
	kubernetes   ClusterIP   10.96.0.1    <none>  

# Docker CE 

Rimozione precedenti versioni:

	sudo apt-get remove docker docker-engine docker.io

Installazione docker ce e GPG key:
	
	sudo apt-get update
	
	sudo apt-get install \
		apt-transport-https \
		ca-certificates \
		curl \
		software-properties-common
	
	curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
	
	sudo add-apt-repository \
		"deb [arch=amd64] https://download.docker.com/linux/ubuntu \
		$(lsb_release -cs) \
		stable"
	
	sudo apt-get install docker-ce
	
Print della lista di versioni docker disponibili:

	apt-cache madison docker-ce
		
Installazione da deb package:

	https://download.docker.com/linux/ubuntu/dists/bionic/pool/edge/amd64/

	sudo dpkg -i ~/Scaricati/package.deb

Check configurazione e installazione docker:

	>$ sudo docker run hello-world
	
	Unable to find image 'hello-world:latest' locally
	latest: Pulling from library/hello-world
	9bb5a5d4561a: Pull complete 
	Digest: sha256:f5233545e43561214ca4891fd1157e1c3c563316ed8e237750d59bde73361e77
	Status: Downloaded newer image for hello-world:latest

	Hello from Docker!
	This message shows that your installation appears to be working correctly.

# Uso di docker con Kubernetes

Creazione di un server node per docker image di test 

	>$ mkdir my-test-node && cd my-test-node && touch Dockerfile server.js && tree

	>$ vim server.js

	var http = require('http');
	var handleRequest = function(request, response) {
	  response.writeHead(200);
	  response.end('Test Kubernetes - Docker');
	};
	var myTestServer = http.createServer(handleRequest);
	myTestServer.listen(8080);

	>$ vim Dockerfile

	FROM node:4.4
	EXPOSE 8080
	COPY server.js .
	CMD node server.js

Per potere lavorare con daemon docker sull'host in uso, lanciare il comando docker-env, in questo modo è possibile usare i comandi docker sulla macchina in uso per parlare con il docker daemon interno alla VM minikube:

	eval $(minikube docker-env)

Build del docker file e risultato:

	>$ docker build -t my-test-node:v1 .
	
	Sending build context to Docker daemon  3.072kB
	Step 1/4 : FROM node:4.4
	4.4: Pulling from library/node
	357ea8c3d80b: Pull complete 
	52befadefd24: Pull complete 
	3c0732d5313c: Pull complete 
	ceb711c7e301: Pull complete 
	868b1d0e2aad: Pull complete 
	3a438db159a5: Pull complete 
	Digest: sha256:e720e944ce6994a461cd2a9e0ae34c4bc45c0f9ee7b3f48052933182fc5f0bf1
	Status: Downloaded newer image for node:4.4
	 ---> 93b396996a16
	Step 2/4 : EXPOSE 8080
	 ---> Running in 451d71ee0af8
	Removing intermediate container 451d71ee0af8
	 ---> 408623c43c68
	Step 3/4 : COPY server.js .
	 ---> 5f2b1aa0eddb
	Step 4/4 : CMD node server.js
	 ---> Running in 299b66256535
	Removing intermediate container 299b66256535
	 ---> e1eba3cd0940
	Successfully built e1eba3cd0940
	Successfully tagged my-test-node:v1

Image disponibile in docker: 

	>$ docker images
	REPOSITORY                                 TAG                 IMAGE ID            CREATED              SIZE
	my-test-node                               v1                  e1eba3cd0940        About a minute ago   648MB
	k8s.gcr.io/kube-proxy-amd64                v1.10.0             bfc21aadc7d3        2 months ago         97MB
	...

Deploy di my-test-node pod in Kubernetes cluster con comando kubectl:

	>$ kubectl run my-test-node --image=my-test-node:v1 --port=8080
	
	deployment.apps "my-test-node" created
	
	>$ kubectl get pods

	NAME                            READY     STATUS    RESTARTS   AGE
	my-test-node-579469d5f5-k89hp   1/1       Running   0          1m

	>$ kubectl get deployments

	NAME           DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
	my-test-node   1         1         1            1           2m

	>$ kubectl get services

	NAME         TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE
	kubernetes   ClusterIP   xx.xx.0.1    <none>        443/TCP   12h

Per esporre verso un indirizzo IP e porta esterni il deployment docker del service nodejs, effettuato con i precedenti passi, usare il comando kubectl expose:

	>$ kubectl expose deployment my-test-node --type=NodePort

	service "my-test-node" exposed

	>$ kubectl get services

	NAME           TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)          AGE
	kubernetes     ClusterIP   xx.xx.0.1       <none>        443/TCP          12h
	my-test-node   NodePort    xx.xxx.xxx.35   <none>        8080:31068/TCP   16s

Si può quindi accedere via curl al deployment esposto verso l'esterno:

	>$ echo $(minikube service my-test-node --url)

	http://192.168.99.100:31068


	>$ curl $(minikube service my-test-node --url)
	
	result string: "Test Kubernetes - Docker"

A operazioni concluse, stop service, deployment, kubernetes cluster e rilascio delle risorse:

	> $ kubectl delete service,deployment my-test-node && minikube stop

	service "my-test-node" deleted
	deployment.extensions "my-test-node" deleted
	Stopping local Kubernetes cluster...
	Machine stopped.


