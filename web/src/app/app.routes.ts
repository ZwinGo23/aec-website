import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { TrainingComponent } from './pages/training/training.component';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';
import { AnnouncementDetailComponent } from './pages/announcement-detail/announcement-detail.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'entrainements', component: TrainingComponent },
  { path: 'inscription', component: RegisterComponent },
  { path: 'connexion', component: LoginComponent },
  { path: 'annonces/:id', component: AnnouncementDetailComponent },
  { path: '**', redirectTo: '' }
];
