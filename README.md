### Monitoring Demo
#### Prerequisites
1. Ansible 
2. Openshift cluster (3.10 recommended) with cluster admin

#### Deploy
1. `ansible-galaxy install -r requirements.yml -p roles`
2. `oc login -u system:admin`
3. `ansible-playbook -i .applier/inventory roles/openshift-applier/playbooks/openshift-cluster-seed.yml`

#### Artifacts
The above will create two projects
- `monitoring-demo-cicd` for Jenkins build + deploy of the application
- `monitoring-demo-prod` where the application is being deployed

It will also deploy the `prometheus-operator` in the `default` project.
Finally it will install prometheus in `monitoring-demo-prod` (route needs to be manually exposed).

#### TODO
* Create buildconfig for the Jenkins Pipeline (create pipeline manually for now, use `Jenkinsfile-pa`)
* Add prometheus route
* Add prometheus alerting
