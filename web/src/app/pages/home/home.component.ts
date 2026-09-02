import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AnnouncementModel } from '../../core/models/announcement.model';
import { AuthService } from '../../core/services/auth.service';
import { HomeService } from '../../core/services/home.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
  presentation = '';
  editedPresentation = '';
  announcements: AnnouncementModel[] = [];

  isEditing = false;
  isSaving = false;
  loading = true;

  readonly defaultAnnouncementImage = '/images/logo-aec.png';

  constructor(
    private readonly homeService: HomeService,
    readonly authService: AuthService
  ) {}

  ngOnInit(): void {
    this.homeService.getPresentation().subscribe({
      next: (presentation) => {
        this.presentation = presentation.content;
        this.editedPresentation = presentation.content;
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

  startEditing(): void {
    this.editedPresentation = this.presentation;
    this.isEditing = true;
  }

  cancelEditing(): void {
    this.editedPresentation = this.presentation;
    this.isEditing = false;
  }

  savePresentation(): void {
    if (!this.editedPresentation.trim()) {
      return;
    }

    this.isSaving = true;

    this.homeService.updatePresentation(this.editedPresentation.trim()).subscribe({
      next: (presentation) => {
        this.presentation = presentation.content;
        this.editedPresentation = presentation.content;
        this.isEditing = false;
        this.isSaving = false;
      },
      error: () => {
        this.isSaving = false;
      }
    });
  }

  announcementImage(announcement: AnnouncementModel): string {
    return announcement.imageUrl || this.defaultAnnouncementImage;
  }
}
