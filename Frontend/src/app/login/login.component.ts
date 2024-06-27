import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { catchError, of } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  username!: string;
  password!: string;
  errorMessage!: string;

  constructor(private authService: AuthService, private router: Router) {}

  authenticate() {
    this.authService
      .authenticate(this.username, this.password)
      .pipe(
        catchError((error) => {
          this.errorMessage = 'Invalid credentials';
          return of(null);
        })
      )
      .subscribe((response) => {
        if (response) {
          this.router.navigate(['/']);
        }
      });
  }
}
