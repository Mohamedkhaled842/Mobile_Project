import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../models/vehicle.dart';
import '../providers/garage_provider.dart';

class AddVehicleScreen extends StatefulWidget {
  const AddVehicleScreen({super.key});

  @override
  State<AddVehicleScreen> createState() => _AddVehicleScreenState();
}

class _AddVehicleScreenState extends State<AddVehicleScreen> {
  final _formKey = GlobalKey<FormState>();

  final _licensePlateController = TextEditingController();
  final _ownerNameController = TextEditingController();
  final _phoneController = TextEditingController();
  final _modelController = TextEditingController();
  final _colorController = TextEditingController();
  final _issueController = TextEditingController();

  VehicleType _selectedType = VehicleType.car;

  @override
  Widget build(BuildContext context) {
    final provider = context.read<GarageProvider>();

    return Scaffold(
      appBar: AppBar(
        title: const Text('Add New Vehicle'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Form(
          key: _formKey,
          child: ListView(
            children: [
              TextFormField(
                controller: _licensePlateController,
                decoration: const InputDecoration(
                  labelText: 'License Plate',
                  prefixIcon: Icon(Icons.confirmation_number),
                ),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Please enter license plate';
                  }
                  return null;
                },
              ),
              TextFormField(
                controller: _ownerNameController,
                decoration: const InputDecoration(
                  labelText: 'Owner Name',
                  prefixIcon: Icon(Icons.person),
                ),
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Please enter owner name';
                  }
                  return null;
                },
              ),
              TextFormField(
                controller: _phoneController,
                decoration: const InputDecoration(
                  labelText: 'Phone Number',
                  prefixIcon: Icon(Icons.phone),
                ),
                keyboardType: TextInputType.phone,
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Please enter phone number';
                  }
                  return null;
                },
              ),
              DropdownButtonFormField<VehicleType>(
                value: _selectedType,
                decoration: const InputDecoration(
                  labelText: 'Vehicle Type',
                  prefixIcon: Icon(Icons.directions_car),
                ),
                items: VehicleType.values.map((type) {
                  return DropdownMenuItem<VehicleType>(
                    value: type,
                    child: Text(type.toString().split('.').last.toUpperCase()),
                  );
                }).toList(),
                onChanged: (value) {
                  setState(() {
                    _selectedType = value!;
                  });
                },
              ),
              TextFormField(
                controller: _modelController,
                decoration: const InputDecoration(
                  labelText: 'Model (Optional)',
                  prefixIcon: Icon(Icons.model_training),
                ),
              ),
              TextFormField(
                controller: _colorController,
                decoration: const InputDecoration(
                  labelText: 'Color (Optional)',
                  prefixIcon: Icon(Icons.color_lens),
                ),
              ),
              TextFormField(
                controller: _issueController,
                decoration: const InputDecoration(
                  labelText: 'Issue Description',
                  prefixIcon: Icon(Icons.description),
                ),
                maxLines: 3,
              ),
              const SizedBox(height: 20),
              ElevatedButton(
                onPressed: () {
                  if (_formKey.currentState!.validate()) {
                    if (provider.availableSpaces <= 0) {
                      ScaffoldMessenger.of(context).showSnackBar(
                        const SnackBar(
                          content: Text('Garage is full! No available spaces.'),
                          backgroundColor: Colors.red,
                        ),
                      );
                      return;
                    }

                    final newVehicle = Vehicle(
                      licensePlate: _licensePlateController.text,
                      ownerName: _ownerNameController.text,
                      phoneNumber: _phoneController.text,
                      type: _selectedType,
                      model: _modelController.text.isNotEmpty ? _modelController.text : null,
                      color: _colorController.text.isNotEmpty ? _colorController.text : null,
                      issueDescription: _issueController.text,
                      checkInTime: DateTime.now(),
                      status: VehicleStatus.checkedIn,
                      lastUpdated: DateTime.now(),
                    );

                    provider.addVehicle(newVehicle);

                    ScaffoldMessenger.of(context).showSnackBar(
                      const SnackBar(
                        content: Text('Vehicle added successfully!'),
                        backgroundColor: Colors.green,
                      ),
                    );

                    Navigator.pop(context);
                  }
                },
                style: ElevatedButton.styleFrom(
                  minimumSize: const Size(double.infinity, 50),
                ),
                child: const Text('Add Vehicle to Garage'),
              ),
            ],
          ),
        ),
      ),
    );
  }

  @override
  void dispose() {
    _licensePlateController.dispose();
    _ownerNameController.dispose();
    _phoneController.dispose();
    _modelController.dispose();
    _colorController.dispose();
    _issueController.dispose();
    super.dispose();
  }
}