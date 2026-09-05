import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AnnouncementModel } from '../../core/models/announcement.model';
import { AuthService } from '../../core/services/auth.service';
import { HomeService } from '../../core/services/home.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
  presentation = '';
  announcements: AnnouncementModel[] = [];
  loading = true;

  readonly defaultAnnouncementImage = '/images/logo-aec.png';

  constructor(
    private readonly homeService: HomeService,
    readonly authService: AuthService
  ) {}

  ngOnInit(): void {
    this.homeService.getPresentation().subscribe({
      next: (presentation) => {
        console.log('Présentation reçue :', presentation);
        this.presentation = presentation.content;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });

    this.homeService.getLatestAnnouncements().subscribe({
      next: (announcements) => {
        this.announcements = announcements;
      }
    });
  }

  announcementImage(announcement: AnnouncementModel): string {
    return announcement.imageUrl || this.defaultAnnouncementImage;
  }
}
