variable "region" {
    type = string
    default = "eu-west-3"
}

variable "ec2_ami" {
    type = string
}

variable "vpc_name" {
    type = string
}

variable "subnet1_name" {
    type = string
}

variable "igw_name" {
    type = string
}

variable "public_route_name" {
    type = string
}

variable "sg_name" {
    type = string
}

variable "ec2_name" {
    type = string
}

variable "role_name" {
    type = string
}

variable "profile_name" {
    type = string
}

variable "policy_name" {
    type = string
}


variable "public_key_path" {
    type = string
}

variable "ec2_instance_type" {
    type = string
    default = "t2.micro"
}

variable "zone1" {
    type = string
    default = "eu-west-3a"
}

variable "my_ip" {
    type = string
}

variable "vpc_cidr_block" {
    type = string
    default = "10.0.0.0/16"
}

variable "subnet1_cidr_block" {
    type = string
    default = "10.0.1.0/24"
}

variable "tags" {
  type    = map(string)
  default = {}
}
