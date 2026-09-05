import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { AnnouncementModel } from '../models/announcement.model';
import { PresentationModel } from '../models/presentation.model';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class HomeService {
  private readonly apiUrl = environment.apiBaseUrl;

  constructor(private readonly http: HttpClient) {}

  getPresentation(): Observable<PresentationModel> {
    return this.http.get<PresentationModel>(`${this.apiUrl}/informations/presentation`);
  }

  getAnnouncement(id: number): Observable<AnnouncementModel> {
    return this.http.get<AnnouncementModel>(`${this.apiUrl}/informations/announcements/${id}`);
  }

  getLatestAnnouncements(limit = 5): Observable<AnnouncementModel[]> {
    return this.http.get<AnnouncementModel[]>(`${this.apiUrl}/informations/announcements`, { params: { limit } });
  }
}
