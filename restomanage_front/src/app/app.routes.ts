import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { MenuComponent } from './pages/menu/menu.component';
import { CartComponent } from './pages/cart/cart.component';
import { CheckoutComponent } from './pages/checkout/checkout.component';
import { PaymentComponent } from './pages/payment/payment.component';
import { OrderProgressComponent } from './pages/order-progress/order-progress.component';
import { ReviewComponent } from './pages/review/review.component';
import { ReservationComponent } from './pages/reservation/reservation.component';
import { ReservationHistoryComponent } from './pages/reservation-history/reservation-history.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'menu', component: MenuComponent },
  { path: 'cart', component: CartComponent },
  { path: 'checkout', component: CheckoutComponent },
  { path: 'payment', component: PaymentComponent },
  { path: 'order-progress', component: OrderProgressComponent },
  { path: 'review', component: ReviewComponent },
  { path: 'reservation', component: ReservationComponent },
  { path: 'reservation-history', component: ReservationHistoryComponent },
];
