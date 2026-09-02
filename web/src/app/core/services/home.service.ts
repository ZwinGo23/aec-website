import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { AnnouncementModel } from '../models/announcement.model';
import { PresentationModel } from '../models/presentation.model';
import { environment } from '../../../environments/environment';

const DEFAULT_PRESENTATION = `Notre club de tennis de table, fondé il y a plus de 50 ans, accueille tous les publics : dames, messieurs et jeunes dès l’âge de 6 ans.

Situé à Molenbeek-Saint-Jean (Bruxelles), notre local est entièrement accessible aux personnes à mobilité réduite et dispose de 12 tables de jeu.

Le club est ouvert du lundi au vendredi, sauf pendant les vacances scolaires où les séances se tiennent uniquement les mardis et vendredis. Consultez la page Horaire pour plus de détails.

Durant les congés scolaires, nous organisons des stages encadrés par un entraîneur qualifié et un partenaire de sparring.

Le RAECTT est affilié à la Fédération Royale Belge de Tennis de Table sous le matricule BBW134.

En compétition, plus de 20 équipes sont engagées dans les championnats, couvrant toutes les catégories : dames, vétérans, seniors, jeunes et découverte. Nos deux équipes phares évoluent en division 2 et 3 nationale.`;

const MOCK_ANNOUNCEMENTS: AnnouncementModel[] = [
  {
    id: 1,
    date: '2026-09-02',
    description: 'Exemple d’annonce : les informations importantes du club apparaîtront ici.',
    imageUrl: null
  },
  {
    id: 2,
    date: '2026-08-28',
    description: 'Exemple d’annonce avec une description courte adaptée à la page d’accueil.',
    imageUrl: null
  }
];

@Injectable({ providedIn: 'root' })
export class HomeService {
  private readonly apiUrl = environment.apiBaseUrl;

  constructor(private readonly http: HttpClient) {}

  getPresentation(): Observable<PresentationModel> {
    if (environment.useMockHomeData) {
      return of({ content: DEFAULT_PRESENTATION });
    }

    return this.http.get<PresentationModel>(`${this.apiUrl}/informations`);
  }

  updatePresentation(content: string): Observable<PresentationModel> {
    if (environment.useMockHomeData) {
      return of({ content });
    }

    return this.http.put<PresentationModel>(
      `${this.apiUrl}/informations`,
      { content }
    );
  }

  getAnnouncement(id: number): Observable<AnnouncementModel> {
    if (environment.useMockHomeData) {
      const announcement = MOCK_ANNOUNCEMENTS.find(item => item.id === id);

      if (!announcement) {
        throw new Error('Annonce introuvable');
      }

      return of(announcement);
    }

    return this.http.get<AnnouncementModel>(
      `${this.apiUrl}/announcements/${id}`
    );
  }

  getLatestAnnouncements(limit = 5): Observable<AnnouncementModel[]> {
    if (environment.useMockHomeData) {
      return of(MOCK_ANNOUNCEMENTS.slice(0, limit));
    }

    return this.http.get<AnnouncementModel[]>(
      `${this.apiUrl}/announcements`,
      { params: { limit } }
    );
  }
}
