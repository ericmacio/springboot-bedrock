resource "aws_vpc" "my_vpc" {
  cidr_block           = var.vpc_cidr_block
  instance_tenancy     = "default"
  enable_dns_support   = "true"
  enable_dns_hostnames = "true"
  tags                 = merge(var.tags, { Name = var.vpc_name })
}

resource "aws_subnet" "public_subnet_1" {
  vpc_id                  = aws_vpc.my_vpc.id
  cidr_block              = var.subnet1_cidr_block
  map_public_ip_on_launch = "true"
  availability_zone       = var.zone1
  tags                    = merge(var.tags, { Name = var.subnet1_name })
}

resource "aws_internet_gateway" "igw" {
  vpc_id = aws_vpc.my_vpc.id
  tags   = merge(var.tags, { Name = var.igw_name })
}

resource "aws_route_table" "public_route" {
  vpc_id = aws_vpc.my_vpc.id
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.igw.id
  }
  tags = merge(var.tags, { Name = var.public_route_name })
}

resource "aws_route_table_association" "public_subnet_1a" {
  subnet_id      = aws_subnet.public_subnet_1.id
  route_table_id = aws_route_table.public_route.id
}

resource "aws_security_group" "ec2_sg" {
  name        = "allow_ssh_http"
  description = "sg for to allow ssh and http"
  vpc_id      = aws_vpc.my_vpc.id
  tags        = merge(var.tags, { Name = var.sg_name })
}

resource "aws_vpc_security_group_ingress_rule" "allow_tls_ipv4" {
  security_group_id = aws_security_group.ec2_sg.id
  cidr_ipv4         = var.my_ip
  from_port         = 22
  ip_protocol       = "tcp"
  to_port           = 22
  tags              = merge(var.tags, { Name = "allow ssh" })
}

resource "aws_vpc_security_group_ingress_rule" "allow_http_ipv4" {
  security_group_id = aws_security_group.ec2_sg.id
  cidr_ipv4         = var.my_ip
  from_port         = 8080
  ip_protocol       = "tcp"
  to_port           = 8080
  tags              = merge(var.tags, { Name = "allow http" })
}

resource "aws_vpc_security_group_egress_rule" "allow_all_traffic_ipv4" {
  security_group_id = aws_security_group.ec2_sg.id
  cidr_ipv4         = "0.0.0.0/0"
  ip_protocol       = "-1" # semantically equivalent to all ports
  tags              = merge(var.tags, { Name = "allow all ipv4" })
}

resource "aws_key_pair" "ec2_key_pair" {
  key_name   = "ec2_ssh"
  public_key = file(var.public_key_path)
}

resource "aws_iam_role" "ec2_role" {
  name = var.role_name
  assume_role_policy = jsonencode(
    {
      "Version": "2012-10-17",
      "Statement": [
        {
          "Action": "sts:AssumeRole",
          "Principal": {
            "Service": "ec2.amazonaws.com"
          },
          "Effect": "Allow",
          "Sid": ""
        }
      ]
    }
  )
  tags = var.tags
}

resource "aws_iam_instance_profile" "ec2_profile" {
  name = var.profile_name
  role = aws_iam_role.ec2_role.name
}

resource "aws_iam_role_policy" "ec2_policy" {
  name = var.policy_name
  role = aws_iam_role.ec2_role.id
  policy = jsonencode(
    {
      "Version": "2012-10-17",
      "Statement": [
        {
          "Action": [
            "events:*",
            "s3:*",
            "dynamodb:*"
          ],
          "Effect": "Allow",
          "Resource": "*"
        }
      ]
    }
  )
}

resource "aws_instance" "ec2_instance" {
  ami                    = var.ec2_ami
  instance_type          = var.ec2_instance_type
  subnet_id              = aws_subnet.public_subnet_1.id
  vpc_security_group_ids = [aws_security_group.ec2_sg.id]
  key_name               = aws_key_pair.ec2_key_pair.key_name
  iam_instance_profile =  aws_iam_instance_profile.ec2_profile.name
  tags                   = merge(var.tags, { Name = var.ec2_name })
}