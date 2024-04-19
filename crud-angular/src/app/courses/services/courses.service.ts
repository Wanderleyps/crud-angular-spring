import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { first } from 'rxjs';

import { Course } from '../model/course';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {

  private readonly API = 'api/courses'

  constructor(private http: HttpClient) { }

  list() {
    return this.http.get<Course[]>(this.API)
    .pipe(
      first()
    );
  }

  save(record: Partial<Course>) {
    return this.http.post<Course>(this.API, record);
  }

}
