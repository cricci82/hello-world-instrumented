### Monitoring Demo
#### Prerequisites
1. Ansible 
2. Openshift cluster (3.10 recommended) with cluster admin

#### Deploy
1. `ansible-galaxy install -r requirements.yml -p roles`
2. `oc login -u system:admin` (or user with `cluster-admin` role)
3. `ansible-playbook -i .applier/inventory roles/openshift-applier/playbooks/openshift-cluster-seed.yml`
4. Wait for the Jenkins pod to start
5. Go to `Builds -> Pipelines -> hello-world-pipeline` in `monitoring-demo-cicd` project
6. Press `Start Pipeline`

#### Artifacts
The above will create two projects
- `monitoring-demo-cicd` for Jenkins build + deploy of the application
- `monitoring-demo-prod` where the application is being deployed

It will also deploy the `prometheus-operator` in the `default` project.
Finally it will install prometheus in `monitoring-demo-prod`

#### TODO
* Create buildconfig for the Jenkins Pipeline (create pipeline manually for now, use `Jenkinsfile-pa`)
* Add prometheus alerting
