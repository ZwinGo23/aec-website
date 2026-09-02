import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { AnnouncementModel } from '../../core/models/announcement.model';
import { HomeService } from '../../core/services/home.service';

@Component({
  selector: 'app-announcement-detail',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './announcement-detail.component.html',
  styleUrl: './announcement-detail.component.scss'
})
export class AnnouncementDetailComponent implements OnInit {
  announcement: AnnouncementModel | null = null;
  loading = true;
  notFound = false;

  readonly defaultAnnouncementImage = '/images/logo-aec.png';

  constructor(
    private readonly route: ActivatedRoute,
    private readonly homeService: HomeService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    if (!Number.isInteger(id) || id <= 0) {
      this.loading = false;
      this.notFound = true;
      return;
    }

    this.homeService.getAnnouncement(id).subscribe({
      next: (announcement) => {
        this.announcement = announcement;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
        this.notFound = true;
      }
    });
  }

  announcementImage(announcement: AnnouncementModel): string {
    return announcement.imageUrl || this.defaultAnnouncementImage;
  }
}
