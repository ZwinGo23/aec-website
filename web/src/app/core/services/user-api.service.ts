import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CreateUserRequest } from '../../features/users/models/create-user-request';

@Injectable({
  providedIn: 'root',
})
export class UserApiService {
  private readonly http = inject(HttpClient);

  createUser(request: CreateUserRequest): Observable<void> {
    return this.http.post<void>('http://localhost:8080/api-aec/users', request);
  }
}
