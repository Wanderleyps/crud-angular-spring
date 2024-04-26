import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { first } from 'rxjs';

import { Course } from '../model/course';
import { CoursePage } from '../model/course-page';

@Injectable({
  providedIn: 'root'
})
export class CoursesService {

  private readonly API = 'api/courses'

  constructor(private http: HttpClient) { }

  list(page = 0, pageSize = 5) {
    return this.http.get<CoursePage>(this.API, {params: { page, pageSize }})
    .pipe(
      first()
    );
  }

  loadById(id: string) {
    return this.http.get<Course>(`${this.API}/${id}`);
  }

  save(record: Partial<Course>) {
    if (record._id) {
      return this.update(record);
    }
    return this.create(record);
  }

  private update(record: Partial<Course>) {
    return this.http.put<Course>(`${this.API}/${record._id}`, record).pipe(first());
  }

  private create(record: Partial<Course>) {
    return this.http.post<Course>(this.API, record).pipe(first());
  }

  remove(id: string) {
    return this.http.delete(`${this.API}/${id}`).pipe(first());
  }

}
