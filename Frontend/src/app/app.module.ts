import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {
  HttpClientModule,
  HTTP_INTERCEPTORS,
  provideHttpClient,
  withFetch,
} from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { JwtModule } from '@auth0/angular-jwt';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppComponent } from './app.component';
import { CustomersComponent } from './customers/customers.component';
import { AccountsComponent } from './accounts/accounts.component';
import { NavbarComponent } from './navbar/navbar.component';
import { AddCustomerComponent } from './add-customer/add-customer.component';
import { UpdateCustomerComponent } from './update-customer/update-customer.component';
import { AddAccountComponent } from './add-account/add-account.component';
import { UpdateAccountComponent } from './update-account/update-account.component';
import { DeleteCustomerModalComponent } from './delete-customer-modal/delete-customer-modal.component';
import { DeleteAccountModalComponent } from './delete-account-modal/delete-account-modal.component';
import { AccountOperationsComponent } from './account-operations/account-operations.component';
import { AddOperationModalComponent } from './add-operation-modal/add-operation-modal.component';
import { LoginComponent } from './login/login.component';
import { AdminComponent } from './admin/admin.component';
import { NgChartjsModule } from 'ng-chartjs';
import { AppRoutingModule } from './app-routing.module';
import { AuthService } from './services/auth.service';
import { ApiService } from './api.service';
import { AuthGuard } from './guards/auth.guard';
import { JwtInterceptor } from './interceptors/jwt.interceptor';

export function tokenGetter() {
  return localStorage.getItem('token');
}

@NgModule({
  declarations: [
    AppComponent,
    CustomersComponent,
    AccountsComponent,
    NavbarComponent,
    AddCustomerComponent,
    UpdateCustomerComponent,
    AddAccountComponent,
    UpdateAccountComponent,
    DeleteCustomerModalComponent,
    DeleteAccountModalComponent,
    AccountOperationsComponent,
    AddOperationModalComponent,
    AdminComponent,
    LoginComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgChartjsModule,
    NgbModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: tokenGetter,
        allowedDomains: ['localhost:8080'],
        disallowedRoutes: ['http://localhost:8080/api/authenticate'],
      },
    }),
  ],
  providers: [
    AuthService,
    ApiService,
    AuthGuard,
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    provideHttpClient(withFetch()),
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
