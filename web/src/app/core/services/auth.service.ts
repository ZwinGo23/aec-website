import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, catchError, Observable, of, switchMap, tap } from 'rxjs';
import { environment } from '../../../environments/environment';
import { AuthenticatedUserModel } from '../models/authenticated-user.model';
import { LoginRequest } from '../models/login-request.model';
import { RegisterRequest } from '../models/register-request.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly apiUrl = environment.apiBaseUrl;

  private readonly currentUserSubject =
    new BehaviorSubject<AuthenticatedUserModel | null>(null);

  readonly currentUser$ = this.currentUserSubject.asObservable();

  constructor(private readonly http: HttpClient) {}

  login(request: LoginRequest): Observable<AuthenticatedUserModel> {
    return this.http.post<void>(
      `${this.apiUrl}/auth/login`,
      request,
      { withCredentials: true }
    ).pipe(
      switchMap(() => this.getCurrentUser())
    );
  }

  register(request: RegisterRequest): Observable<void> {
    return this.http.post<void>(
      `${this.apiUrl}/users`,
      request,
      { withCredentials: true }
    );
  }

  getCurrentUser(): Observable<AuthenticatedUserModel> {
    return this.http.get<AuthenticatedUserModel>(
      `${this.apiUrl}/auth/me`,
      { withCredentials: true }
    ).pipe(
      tap(user => this.currentUserSubject.next(user))
    );
  }

  loadCurrentUser(): Observable<AuthenticatedUserModel | null> {
    return this.getCurrentUser().pipe(
      catchError(() => {
        this.currentUserSubject.next(null);
        return of(null);
      })
    );
  }

  logout(): Observable<void> {
    return this.http.post<void>(
      `${this.apiUrl}/auth/logout`,
      {},
      { withCredentials: true }
    ).pipe(
      tap(() => this.currentUserSubject.next(null))
    );
  }

  isAuthenticated(): boolean {
    return this.currentUserSubject.value !== null;
  }

  isAdmin(): boolean {
    return this.currentUserSubject.value?.role === 'ADMIN';
  }

  isResponsable(): boolean {
    return this.currentUserSubject.value?.role === 'RESPONSABLE';
  }

  canManageClub(): boolean {
    const role = this.currentUserSubject.value?.role;

    return role === 'ADMIN' || role === 'RESPONSABLE';
  }
}