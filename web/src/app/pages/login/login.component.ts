import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router'
import { finalize } from 'rxjs';
import { LucideEye, LucideEyeOff } from '@lucide/angular';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, LucideEye, LucideEyeOff],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  loading = false;
  showPassword = false;
  errorMessage = '';

  loginForm = new FormGroup({
    email: new FormControl('', {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.email
      ]
    }),

    password: new FormControl('', {
      nonNullable: true,
      validators: [
        Validators.required
      ]
    })
  });

  constructor(
    private readonly authService: AuthService,
    private readonly router: Router
  ) {}

  togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }

  login(): void {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    const request = this.loginForm.getRawValue();

    this.authService.login(request).pipe(finalize(() => {
      this.loading = false;
    })
    )
    .subscribe({
      next: () => {
        this.router.navigate(['/']);
      },
      error: (error) => {
          if (error.status === 401) {
            this.errorMessage =
              'Adresse email ou mot de passe incorrect.';
            return;
          }

          if (error.status === 403) {
            this.errorMessage =
              'Vous êtes déjà connecté.';
            return;
          }

          this.errorMessage =
            'Une erreur est survenue lors de la connexion.';
        }
    });
  }
}