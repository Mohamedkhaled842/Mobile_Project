import 'package:flutter/foundation.dart';
import '../models/vehicle.dart';

class GarageProvider extends ChangeNotifier {
  List<Vehicle> _vehicles = [];
  int _totalSpaces = 20;
  int _occupiedSpaces = 0;

  List<Vehicle> get vehicles => _vehicles;
  int get totalSpaces => _totalSpaces;
  int get occupiedSpaces => _occupiedSpaces;
  int get availableSpaces => _totalSpaces - _occupiedSpaces;

  void addVehicle(Vehicle vehicle) {
    _vehicles.add(vehicle);
    _occupiedSpaces++;
    notifyListeners();
  }

  void removeVehicle(String licensePlate) {
    _vehicles.removeWhere((v) => v.licensePlate == licensePlate);
    _occupiedSpaces--;
    notifyListeners();
  }

  void updateVehicleStatus(String licensePlate, VehicleStatus status) {
    int index = _vehicles.indexWhere((v) => v.licensePlate == licensePlate);
    if (index != -1) {
      _vehicles[index].status = status;
      _vehicles[index].lastUpdated = DateTime.now();
      notifyListeners();
    }
  }

  List<Vehicle> getActiveVehicles() {
    return _vehicles.where((v) => v.status != VehicleStatus.completed).toList();
  }

  List<Vehicle> getCompletedVehicles() {
    return _vehicles.where((v) => v.status == VehicleStatus.completed).toList();
  }
}