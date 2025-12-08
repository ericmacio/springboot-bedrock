data "aws_caller_identity" "current" {}

terraform {
  required_version = ">= 1.0"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 6.0"
    }
  }
}

provider "aws" {
  # Uses AWS CLI configuration (aws configure)
  region = var.region
}

locals {
  name_prefix = "${var.project_name}-${terraform.workspace}"
  common_tags = {
    Project     = var.project_name
    Environment = terraform.workspace
    ManagedBy   = "terraform"
  }
}

module "ec2" {
    source = "./modules/ec2"
    ec2_name = "${local.name_prefix}-ec2"
    vpc_name = "${local.name_prefix}-vpc"
    subnet1_name = "${local.name_prefix}-subnet1"
    igw_name = "${local.name_prefix}-igw"
    public_route_name = "${local.name_prefix}-route"
    sg_name = "${local.name_prefix}-ec2_sg"
    role_name = "${local.name_prefix}-ec2_role"
    profile_name = "${local.name_prefix}-ec2_profile"
    policy_name = "${local.name_prefix}-ec2_policy"
    region = var.region
    zone1 = var.zone1
    my_ip = var.my_ip
    vpc_cidr_block = var.vpc_cidr_block
    subnet1_cidr_block = var.subnet1_cidr_block
    ec2_ami = var.ec2_ami
    ec2_instance_type = var.ec2_instance_type
    tags = local.common_tags
    public_key_path = var.public_key_path
    
}