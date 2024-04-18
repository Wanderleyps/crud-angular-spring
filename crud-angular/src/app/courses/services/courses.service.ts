import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Course } from '../model/course';
import { delay, first } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {

  private readonly API = 'http://localhost:8080/api/courses'

  constructor(private http: HttpClient) { }

  list() {
    return this.http.get<Course[]>(this.API)
    .pipe(
      first()
    );
  }

}
