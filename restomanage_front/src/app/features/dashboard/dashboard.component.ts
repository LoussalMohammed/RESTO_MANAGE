import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  // Sample dashboard data
  statistics = {
    totalOrders: 0,
    totalRevenue: 0,
    activeReservations: 0,
    availableTables: 0
  };

  ngOnInit(): void {
    // Here we'll fetch real data once we implement the services
    this.statistics = {
      totalOrders: 150,
      totalRevenue: 5250,
      activeReservations: 12,
      availableTables: 8
    };
  }
}
