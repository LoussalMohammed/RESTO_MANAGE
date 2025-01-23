import { Component } from '@angular/core';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss']
})
export class LayoutComponent {
  menuItems = [
    { path: '/', icon: 'dashboard', label: 'Dashboard' },
    { path: '/staff', icon: 'people', label: 'Staff' },
    { path: '/menu', icon: 'restaurant_menu', label: 'Menu' },
    { path: '/orders', icon: 'receipt', label: 'Orders' },
    { path: '/tables', icon: 'table_restaurant', label: 'Tables' },
    { path: '/reservations', icon: 'event', label: 'Reservations' },
    { path: '/clients', icon: 'group', label: 'Clients' },
    { path: '/inventory', icon: 'inventory', label: 'Inventory' },
    { path: '/payments', icon: 'payments', label: 'Payments' },
    { path: '/reports', icon: 'analytics', label: 'Reports' },
    { path: '/settings', icon: 'settings', label: 'Settings' }
  ];
}
