import { Component, OnInit } from '@angular/core';
import { StorageService } from './auth/services/storage/storage.service';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { filter, Subscription } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  title = 'frontend';
  isCustomerLoggedIn: boolean = false;
  isAdminLoggedIn: boolean = false

  private routeDataSubscription: Subscription | undefined;
  private routerEventsSubscription: Subscription | undefined;

  constructor(private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.routerEventsSubscription = this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      this.isAdminLoggedIn = StorageService.isAdminLoggedIn();
      this.isCustomerLoggedIn = StorageService.isCustomerLoggedIn();
    });

  }

  ngOnDestroy(): void {
    if (this.routeDataSubscription) {
      this.routeDataSubscription.unsubscribe();
    }
    if (this.routerEventsSubscription) {
      this.routerEventsSubscription.unsubscribe();
    }
  }

  logout(): void {
    StorageService.logout();
    this.router.navigate(['/login']);
  }
}
