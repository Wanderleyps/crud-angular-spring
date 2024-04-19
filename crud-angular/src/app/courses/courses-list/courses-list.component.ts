import { Component, Input, OnInit, input } from '@angular/core';
import { Course } from '../model/course';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table'; // Importe o MatTableModule
import { SharedModule } from '../../shared/shared.module';
import { AppMaterialModule } from '../../shared/app-material/app-material.module';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-courses-list',
  standalone: true,
  templateUrl: './courses-list.component.html',
  styleUrl: './courses-list.component.scss',
  imports: [
    CommonModule,
    MatTableModule,
    SharedModule,
    AppMaterialModule
  ]
})
export class CoursesListComponent implements OnInit {

  @Input() courses: Course[] = [];
  readonly displayedColumns = ['name', 'category', 'actions']

  constructor (
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {}

  onAdd() {
    this.router.navigate(['new'], { relativeTo: this.route });
  }

}
