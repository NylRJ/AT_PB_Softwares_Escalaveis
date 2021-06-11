variable "machine_type" {
  type = string
  default = "n1-standard-1"
}

variable "zone" {
  type = string
  default = "us-central1-c"
}

variable "region" {
  type = string
  default = "us-central1"
}


variable "project" {
  type = string
  default = "static-balm-268911"
}

variable "debian_image_family" {
  type = string
  default = "debian-10"
}

variable "debian_image_project" {
  type = string
  default = "debian-cloud"
}