import { Component, inject, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { UserApiService } from './core/services/user-api.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.scss',
})
export class App {
  protected readonly title = signal('web');

  private readonly userApiService = inject(UserApiService);

  message = '';

  createUser(): void {
    this.userApiService
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
        error: (err) => {
          this.message = `Erreur HTTP ${err.status}`;
          console.error(err);
        },
      });
  }
}
