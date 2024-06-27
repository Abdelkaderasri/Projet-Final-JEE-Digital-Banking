import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CustomersComponent } from './customers/customers.component';
import { AccountsComponent } from './accounts/accounts.component';
import { LoginComponent } from './login/login.component';
import { AdminComponent } from './admin/admin.component';
import { AuthGuard } from './guards/auth.guard';

const routes: Routes = [
  {
    path: 'customers',
    component: CustomersComponent,
    canActivate: [AuthGuard],
  },
  { path: 'accounts', component: AccountsComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  {
    path: 'admin',
    component: AdminComponent,
    canActivate: [AuthGuard],
    data: { expectedRole: 'ROLE_ADMIN' },
  },
  { path: '', redirectTo: '/customers', pathMatch: 'full' }, // Redirection par défaut vers customers
  { path: '**', redirectTo: '/customers', pathMatch: 'full' }, // Redirection pour les routes inconnues
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
