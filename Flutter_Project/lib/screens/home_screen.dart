import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../models/vehicle.dart';
import '../providers/garage_provider.dart';
import 'add_vehicle_screen.dart';
import 'vehicle_details_screen.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Garage Management'),
        actions: [
          IconButton(
            icon: const Icon(Icons.refresh),
            onPressed: () => context.read<GarageProvider>().notifyListeners(),
          ),
        ],
      ),
      body: Column(
        children: [
          _buildStatsCard(context),
          Expanded(
            child: _buildVehicleList(context),
          ),
        ],
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          Navigator.push(
            context,
            MaterialPageRoute(builder: (context) => const AddVehicleScreen()),
          );
        },
        child: const Icon(Icons.add),
      ),
    );
  }

  Widget _buildStatsCard(BuildContext context) {
    final provider = context.watch<GarageProvider>();
    return Card(
      margin: const EdgeInsets.all(16),
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            const Text(
              'Garage Status',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 10),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                _buildStatItem('Total Spaces', '${provider.totalSpaces}'),
                _buildStatItem('Occupied', '${provider.occupiedSpaces}'),
                _buildStatItem('Available', '${provider.availableSpaces}'),
              ],
            ),
            LinearProgressIndicator(
              value: provider.occupiedSpaces / provider.totalSpaces,
              backgroundColor: Colors.grey[200],
              valueColor: AlwaysStoppedAnimation<Color>(
                provider.availableSpaces > 5 ? Colors.green : Colors.red,
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildStatItem(String label, String value) {
    return Column(
      children: [
        Text(
          value,
          style: const TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
        ),
        Text(
          label,
          style: const TextStyle(fontSize: 12, color: Colors.grey),
        ),
      ],
    );
  }

  Widget _buildVehicleList(BuildContext context) {
    final provider = context.watch<GarageProvider>();
    final activeVehicles = provider.getActiveVehicles();

    if (activeVehicles.isEmpty) {
      return const Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(Icons.directions_car, size: 64, color: Colors.grey),
            SizedBox(height: 16),
            Text(
              'No vehicles in garage',
              style: TextStyle(fontSize: 18, color: Colors.grey),
            ),
          ],
        ),
      );
    }

    return ListView.builder(
      itemCount: activeVehicles.length,
      itemBuilder: (context, index) {
        final vehicle = activeVehicles[index];
        return Card(
          margin: const EdgeInsets.symmetric(horizontal: 16, vertical: 4),
          child: ListTile(
            leading: CircleAvatar(
              backgroundColor: vehicle.statusColor.withOpacity(0.2),
              child: Icon(
                _getVehicleIcon(vehicle.type),
                color: vehicle.statusColor,
              ),
            ),
            title: Text(vehicle.licensePlate),
            subtitle: Text(
              '${vehicle.ownerName} • ${vehicle.typeString}\nStatus: ${vehicle.statusString}',
            ),
            trailing: const Icon(Icons.chevron_right),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => VehicleDetailsScreen(vehicle: vehicle),
                ),
              );
            },
          ),
        );
      },
    );
  }

  IconData _getVehicleIcon(VehicleType type) {
    switch (type) {
      case VehicleType.car:
        return Icons.directions_car;
      case VehicleType.motorcycle:
        return Icons.two_wheeler;
      case VehicleType.truck:
        return Icons.local_shipping;
      case VehicleType.suv:
        return Icons.airport_shuttle;
    }
  }
}