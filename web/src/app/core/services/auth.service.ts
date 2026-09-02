import { Injectable, signal } from '@angular/core';

export type UserRole = 'ADMIN' | 'RESPONSABLE' | 'USER';

@Injectable({ providedIn: 'root' })
export class AuthService {
  // À remplacer plus tard par GET /api-aec/auth/me.
  private readonly currentRole = signal<UserRole | null>(null);

  canManageHomeContent(): boolean {
    const role = this.currentRole();
    return role === 'ADMIN' || role === 'RESPONSABLE';
  }

  // Utile uniquement pendant le développement de l'interface.
  setDevelopmentRole(role: UserRole | null): void {
    this.currentRole.set(role);
  }
}
