import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../models/vehicle.dart';
import '../providers/garage_provider.dart';

class VehicleDetailsScreen extends StatelessWidget {
  final Vehicle vehicle;

  const VehicleDetailsScreen({super.key, required this.vehicle});

  @override
  Widget build(BuildContext context) {
    final provider = context.read<GarageProvider>();

    return Scaffold(
      appBar: AppBar(
        title: Text(vehicle.licensePlate),
        actions: [
          if (vehicle.status != VehicleStatus.completed)
            IconButton(
              icon: const Icon(Icons.check_circle),
              onPressed: () {
                _showStatusDialog(context, provider);
              },
            ),
        ],
      ),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            _buildInfoCard(),
            const SizedBox(height: 20),
            _buildStatusCard(),
            const SizedBox(height: 20),
            if (vehicle.status == VehicleStatus.ready)
              _buildCheckoutButton(context, provider),
          ],
        ),
      ),
    );
  }

  Widget _buildInfoCard() {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'Vehicle Information',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 10),
            _buildInfoRow('Owner', vehicle.ownerName),
            _buildInfoRow('Phone', vehicle.phoneNumber),
            _buildInfoRow('Type', vehicle.typeString),
            if (vehicle.model != null) _buildInfoRow('Model', vehicle.model!),
            if (vehicle.color != null) _buildInfoRow('Color', vehicle.color!),
            _buildInfoRow(
              'Check-in Time',
              '${vehicle.checkInTime.hour}:${vehicle.checkInTime.minute.toString().padLeft(2, '0')}',
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildInfoRow(String label, String value) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 4),
      child: Row(
        children: [
          SizedBox(
            width: 100,
            child: Text(
              label,
              style: const TextStyle(fontWeight: FontWeight.w500),
            ),
          ),
          Text(value),
        ],
      ),
    );
  }

  Widget _buildStatusCard() {
    return Card(
      color: vehicle.statusColor.withOpacity(0.1),
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'Service Status',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 10),
            Chip(
              label: Text(
                vehicle.statusString,
                style: TextStyle(color: Colors.white),
              ),
              backgroundColor: vehicle.statusColor,
            ),
            if (vehicle.issueDescription != null)
              Padding(
                padding: const EdgeInsets.only(top: 10),
                child: Text(
                  'Issue: ${vehicle.issueDescription}',
                  style: const TextStyle(fontStyle: FontStyle.italic),
                ),
              ),
          ],
        ),
      ),
    );
  }

  Widget _buildCheckoutButton(BuildContext context, GarageProvider provider) {
    return SizedBox(
      width: double.infinity,
      child: ElevatedButton.icon(
        onPressed: () {
          showDialog(
            context: context,
            builder: (context) => AlertDialog(
              title: const Text('Checkout Vehicle'),
              content: const Text('Are you sure you want to checkout this vehicle?'),
              actions: [
                TextButton(
                  onPressed: () => Navigator.pop(context),
                  child: const Text('Cancel'),
                ),
                TextButton(
                  onPressed: () {
                    provider.removeVehicle(vehicle.licensePlate);
                    Navigator.pop(context);
                    Navigator.pop(context);
                    ScaffoldMessenger.of(context).showSnackBar(
                      const SnackBar(
                        content: Text('Vehicle checked out successfully!'),
                        backgroundColor: Colors.green,
                      ),
                    );
                  },
                  child: const Text('Checkout'),
                ),
              ],
            ),
          );
        },
        icon: const Icon(Icons.exit_to_app),
        label: const Text('Checkout Vehicle'),
        style: ElevatedButton.styleFrom(
          backgroundColor: Colors.green,
          minimumSize: const Size(double.infinity, 50),
        ),
      ),
    );
  }

  void _showStatusDialog(BuildContext context, GarageProvider provider) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: const Text('Update Status'),
        content: Column(
          mainAxisSize: MainAxisSize.min,
          children: VehicleStatus.values.map((status) {
            if (status == VehicleStatus.completed) return const SizedBox.shrink();

            return ListTile(
              leading: Icon(
                Icons.circle,
                color: _getStatusColor(status),
              ),
              title: Text(_getStatusString(status)),
              onTap: () {
                provider.updateVehicleStatus(vehicle.licensePlate, status);
                Navigator.pop(context);
                ScaffoldMessenger.of(context).showSnackBar(
                  SnackBar(
                    content: Text('Status updated to ${_getStatusString(status)}'),
                    backgroundColor: Colors.green,
                  ),
                );
              },
            );
          }).toList(),
        ),
      ),
    );
  }

  String _getStatusString(VehicleStatus status) {
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

  Color _getStatusColor(VehicleStatus status) {
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