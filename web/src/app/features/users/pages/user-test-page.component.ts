import { Component, inject } from '@angular/core';
import { UserApiService } from '../../../core/services/user-api.service';

@Component({
  selector: 'app-user-test-page',
  standalone: true,
  template: `
    <button (click)="createUser()">Créer un utilisateur</button>

    <p>{{ message }}</p>
  `,
})
export class UserTestPageComponent {
  private readonly userApi = inject(UserApiService);

  message = '';

  createUser(): void {
    this.userApi
      .createUser({
        email: 'test@test.com',
        phoneNumber: '123456789',
        firstName: 'Gabriel',
        lastName: 'Paquet',
        gender: 'M',
        birthDate: '2026-06-12',
      })
      .subscribe({
        next: () => {
          this.message = 'Utilisateur créé';
        },
        error: (error) => {
          this.message = `Erreur HTTP ${error.status}`;
        },
      });
  }
}
