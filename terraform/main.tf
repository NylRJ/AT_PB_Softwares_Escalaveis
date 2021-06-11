provider "google" {
  credentials = file("~/gcp/i9development.json")
  project = var.project
  region = var.region
  zone = var.zone
}

resource "random_id" "instance_id" {
  byte_length = 8
}

resource "google_compute_address" "default" {
  name = "ipv4-address"
}

data "google_compute_image" "debian_image" {
  family = var.debian_image_family
  project = var.debian_image_project
}

resource "google_compute_firewall" "default" {
  name = "firewall-8080"
  network = google_compute_network.default.name

  allow {
    protocol = "tcp"
    ports = [
      "80",
      "8080",
      "443",
      "22"]
  }

  source_ranges = [
    "0.0.0.0/0"]
  target_tags = [
    "firewall-8080",
    "http-server",
    "https-server"]
}

resource "google_compute_network" "default" {
  name = "private-network"
}

resource "google_compute_instance" "default" {
  name = "coffeeandit-vm-${random_id.instance_id.hex}"
  machine_type = var.machine_type
  zone = var.zone

  tags = [
    "http-server",
    "https-server",
    "firewall-8080"
  ]



  boot_disk {
    initialize_params {
      image = data.google_compute_image.debian_image.self_link
    }
  }
  metadata_startup_script = "sudo apt-get update;sudo apt-key adv --keyserver keyserver.ubuntu.com --recv-keys 93C4A3FD7BB9C367;sudo apt update;sudo apt install ansible -yq"

  network_interface {
    network = "default"
    access_config {
      nat_ip = google_compute_address.default.address

    }
  }
}




