import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { JwtHelperService } from '@auth0/angular-jwt';
import { environment } from '../env';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private baseUrl = environment.apiUrl;
  private roles: string[] = [];

  constructor(private http: HttpClient, private jwtHelper: JwtHelperService) {}

  authenticate(username: string, password: string): Observable<any> {
    return this.http
      .post<any>(`${this.baseUrl}/authenticate`, { username, password })
      .pipe(
        tap((response: any) => {
          localStorage.setItem('token', response.jwt);
          const decodedToken = this.jwtHelper.decodeToken(response.jwt);
          localStorage.setItem('roles', JSON.stringify(decodedToken.roles));
        })
      );
  }

  isAuthenticated(): boolean {
    const token = this.getToken();
    return token ? !this.jwtHelper.isTokenExpired(token) : false;
  }

  getToken(): string | null {
    if (typeof localStorage !== 'undefined') {
      return localStorage.getItem('token');
    }
    return null;
  }

  setToken(token: string): void {
    if (typeof localStorage !== 'undefined') {
      localStorage.setItem('token', token);
    }
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('roles');
  }

  hasRole(role: string): boolean {
    return this.getRoles().includes(role);
  }

  getRoles(): string[] {
    return JSON.parse(localStorage.getItem('roles') || '[]');
  }
}
