terraform {
  backend "s3" {
    bucket = "tf-agent-api-springboot-481160876852"
    key = "terraform.tfstate"
    workspace_key_prefix = "workspaces"
    region = "eu-west-3"
  }
}