### Projeto Responsável por criação de VM's na Google Cloud usando Terraform

No arquivo de variáveis é possível ver todos os parâmetros para a aplicação do projeto.


[Variáveis](variables.tf)

Exemplo de aplicação passando parâmetros e nesse caso o tipo da máquina.

terraform apply -var machine_type=n1-standard-1


