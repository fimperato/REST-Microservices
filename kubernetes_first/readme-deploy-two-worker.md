# Test con deployment due cluster su kubernets

	> $ minikube start
	
Setto replicas=2 e assegno la labels "run=load-balancer-example", l'image docker è quella disponibile in Google Cloud Registry, un repository gestito e privato per il salvataggio di docker image:

	>$ kubectl run hello-world --replicas=2 --labels="run=load-balancer-example" --image=gcr.io/google-samples/node-hello:1.0  --port=8080

	deployment.apps "hello-world" created
	
	>$ kubectl get deployments hello-world
	
	NAME          DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
	hello-world   2         2         2            0           22s
	
	>$ kubectl describe deployments hello-world
	
	Name:                   hello-world
	Namespace:              default
	Labels:                 run=load-balancer-example
	Annotations:            deployment.kubernetes.io/revision=1
	Selector:               run=load-balancer-example
	Replicas:               2 desired | 2 updated | 2 total | 0 available | 2 unavailable
	StrategyType:           RollingUpdate
	MinReadySeconds:        0
	RollingUpdateStrategy:  1 max unavailable, 1 max surge
	Pod Template:
	  Labels:  run=load-balancer-example
	  Containers:
	   hello-world:
		Image:        gcr.io/google-samples/node-hello:1.0
		Port:         8080/TCP
		Host Port:    0/TCP
		Environment:  <none>
		Mounts:       <none>
	  Volumes:        <none>
	Conditions:
	  Type           Status  Reason
	  ----           ------  ------
	  Available      False   MinimumReplicasUnavailable
	  Progressing    True    ReplicaSetUpdated
	OldReplicaSets:  <none>
	NewReplicaSet:   hello-world-5b446dd74b (2/2 replicas created)
	Events:
	  Type    Reason             Age   From                   Message
	  ----    ------             ----  ----                   -------
	  Normal  ScalingReplicaSet  24s   deployment-controller  Scaled up replica set hello-world-5b446dd74b to 2

ReplicaSet informazioni:

	>$ kubectl get replicasets
	
	NAME                     DESIRED   CURRENT   READY     AGE
	hello-world-5b446dd74b   2         2         2         2m
	
	>$ kubectl describe replicasets
	
	Name:           hello-world-5b446dd74b
	Namespace:      default
	Selector:       pod-template-hash=1600288306,run=load-balancer-example
	Labels:         pod-template-hash=1600288306
					run=load-balancer-example
	Annotations:    deployment.kubernetes.io/desired-replicas=2
					deployment.kubernetes.io/max-replicas=3
					deployment.kubernetes.io/revision=1
	Controlled By:  Deployment/hello-world
	Replicas:       2 current / 2 desired
	Pods Status:    2 Running / 0 Waiting / 0 Succeeded / 0 Failed
	Pod Template:
	  Labels:  pod-template-hash=1600288306
			   run=load-balancer-example
	  Containers:
	   hello-world:
		Image:        gcr.io/google-samples/node-hello:1.0
		Port:         8080/TCP
		Host Port:    0/TCP
		Environment:  <none>
		Mounts:       <none>
	  Volumes:        <none>
	Events:
	  Type    Reason            Age   From                   Message
	  ----    ------            ----  ----                   -------
	  Normal  SuccessfulCreate  2m    replicaset-controller  Created pod: hello-world-5b446dd74b-sbv4w
	  Normal  SuccessfulCreate  2m    replicaset-controller  Created pod: hello-world-5b446dd74b-dbzv9

	>$ kubectl expose deployment hello-world --type=NodePort --name=some-service-name
	
	service "some-service-name" exposed

	>$ kubectl describe services some-service-name
	
	Name:                     some-service-name
	Namespace:                default
	Labels:                   run=load-balancer-example
	Annotations:              <none>
	Selector:                 run=load-balancer-example
	Type:                     NodePort
	IP:                       xx.xxx.xxx.52
	Port:                     <unset>  8080/TCP
	TargetPort:               8080/TCP
	NodePort:                 <unset>  32223/TCP
	Endpoints:                xxx.xx.0.4:8080,xxx.xx.0.5:8080
	Session Affinity:         None
	External Traffic Policy:  Cluster
	Events:                   <none>

I node in kubernetes sono i worker su cui parte l'applicazione. In minikube vi è un singolo nodo quindi l'output sarà come il seguente (altrimenti avremmo avuto due worker differenti per le due righe alla colonna node):

Rif. "Minikube is a lightweight Kubernetes implementation that creates a VM on your local machine and deploys a simple cluster containing only one node [...]"; https://kubernetes.io/docs/tutorials/kubernetes-basics/create-cluster/cluster-intro/

	>$ kubectl get pods --selector="run=load-balancer-example" --output=wide
	
	NAME                           READY     STATUS    RESTARTS   AGE       IP           NODE
	hello-world-5b446dd74b-dbzv9   1/1       Running   0          16m       xxx.xx.0.5   minikube
	hello-world-5b446dd74b-sbv4w   1/1       Running   0          16m       xxx.xx.0.4   minikube

Check presenza di 2 pod in running con name "hello-..":

	>$ kubectl get pods | grep hello- | wc -l

Test di chiamata al 'some-service-name' tramite IP e porta del nodo

	>$ curl $(minikube service some-service-name --url)

	result: "Hello Kubernetes!"

	> $ kubectl delete service,deployment my-test-node && minikube stop

A operazioni concluse, stop service, deployment, kubernetes cluster e rilascio delle risorse:

	> $ kubectl delete service,deployment my-test-node && minikube stop

	service "my-test-node" deleted
	deployment.extensions "my-test-node" deleted
	Stopping local Kubernetes cluster...
	Machine stopped.


