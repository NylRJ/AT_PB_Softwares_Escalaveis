# Projeto responsável por criar uma instância de Jenkins com Ansible.

### Update Instance

ansible -i inventory.cfg web -u love --private-key id_rsa -a "sudo apt-get update"

### Install Jenkins

ansible-playbook -i inventory.cfg jenkins_install.yml -b --private-key id_rsa

### Uninstall Jenkins

ansible-playbook -i inventory.cfg web jenkins_uninstall.yml -b --private-key id_rsa
