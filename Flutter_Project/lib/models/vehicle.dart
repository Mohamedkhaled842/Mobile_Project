import 'package:flutter/material.dart';

enum VehicleType { car, motorcycle, truck, suv }
enum VehicleStatus { checkedIn, inService, ready, completed }

class Vehicle {
  String licensePlate;
  String ownerName;
  String phoneNumber;
  VehicleType type;
  String? model;
  String? color;
  String? issueDescription;
  DateTime checkInTime;
  DateTime? checkOutTime;
  VehicleStatus status;
  DateTime lastUpdated;

  Vehicle({
    required this.licensePlate,
    required this.ownerName,
    required this.phoneNumber,
    required this.type,
    this.model,
    this.color,
    this.issueDescription,
    required this.checkInTime,
    this.checkOutTime,
    required this.status,
    required this.lastUpdated,
  });

  String get typeString {
    switch (type) {
      case VehicleType.car:
        return 'Car';
      case VehicleType.motorcycle:
        return 'Motorcycle';
      case VehicleType.truck:
        return 'Truck';
      case VehicleType.suv:
        return 'SUV';
    }
  }

  String get statusString {
    switch (status) {
      case VehicleStatus.checkedIn:
        return 'Checked In';
      case VehicleStatus.inService:
        return 'In Service';
      case VehicleStatus.ready:
        return 'Ready for Pickup';
      case VehicleStatus.completed:
        return 'Completed';
    }
  }

  Color get statusColor {
    switch (status) {
      case VehicleStatus.checkedIn:
        return Colors.blue;
      case VehicleStatus.inService:
        return Colors.orange;
      case VehicleStatus.ready:
        return Colors.green;
      case VehicleStatus.completed:
        return Colors.grey;
    }
  }
}