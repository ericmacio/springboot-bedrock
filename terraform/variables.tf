variable "project_name" {
  description = "Name prefix for all resources"
  type        = string
  validation {
    condition     = can(regex("^[a-z0-9-]+$", var.project_name))
    error_message = "Project name must contain only lowercase letters, numbers, and hyphens."
  }
}

variable "region" {
    type = string
}

variable "ec2_ami" {
    type = string
}

variable "ec2_instance_type" {
    type = string
}

variable "public_key_path" {
    type = string
}

variable "zone1" {
    type = string
}

variable "my_ip" {
    type = string
}

variable "vpc_cidr_block" {
    type = string
}

variable "subnet1_cidr_block" {
    type = string
}

